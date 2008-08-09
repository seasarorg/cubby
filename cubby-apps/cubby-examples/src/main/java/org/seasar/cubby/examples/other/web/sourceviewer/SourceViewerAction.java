/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.cubby.examples.other.web.sourceviewer;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Direct;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.RequestParameter;

@Path("sourceviewer")
public class SourceViewerAction extends Action {

	private static final String[] SOURCE_EXTENSIONS = new String[] {"jsp","java"};

	public List<String> paths;
	
	public ServletContext context;

	public HttpServletResponse response;

	@RequestParameter
	public String[] dirs;
	
	@RequestParameter
	public String title;
	
	@RequestParameter
	public String path;
	
	@Form
	public ActionResult index() {
		this.paths = collectPaths(this.dirs);
		return new Forward("index.jsp");
	}
	
	@Form
	public ActionResult getSource() throws Exception {
		String source = getSource(path);
		response.setContentType("text/text");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(source);
		return new Direct();
	}

	@SuppressWarnings("unchecked")
	private List<String> collectPaths(String[] dirs) {
		List<String> pathList = new ArrayList<String>();
		for (String dir : dirs) {
			File target = new File(toAbsolutePath(dir));
			if (target.isFile()) {
				String path = toRelativePath(target.getAbsolutePath());
				pathList.add(path);
			} else if (target.isDirectory()){
				Collection<File> childFiles = FileUtils.listFiles(target, SOURCE_EXTENSIONS, true);
				for (File childFile : childFiles) {
					String path = toRelativePath(childFile.getAbsolutePath());
					pathList.add(path);
				}
			}
		}
		return pathList;
	}

	private String toAbsolutePath(String relativePath) {
		return getContextRootPath() + relativePath;
	}

	private String toRelativePath(String absolutePath) {
		return absolutePath.substring(getContextRootPath().length());
	}

	private String getContextRootPath() {
		return context.getRealPath("/");
	}

	private String getSource(String relativePath) throws Exception {
		File sourceFile = new File(toAbsolutePath(relativePath));
		return FileUtils.readFileToString(sourceFile, "UTF-8");
	}
	
}
