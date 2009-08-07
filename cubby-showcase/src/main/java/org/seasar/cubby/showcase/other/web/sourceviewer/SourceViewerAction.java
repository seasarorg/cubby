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
package org.seasar.cubby.showcase.other.web.sourceviewer;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Direct;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.RequestParameter;

@Path("sourceviewer")
public class SourceViewerAction extends Action {

	public List<SourcePath> paths;

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

	private List<SourcePath> collectPaths(String[] dirs) {
		List<SourcePath> pathList = new ArrayList<SourcePath>();
		for (String dir : dirs) {
			collect(dir, pathList);
		}
		return pathList;
	}

	private void collect(String path, List<SourcePath> sourcePaths) {
		if (path == null) {
			sourcePaths.add(new SourcePath(path, false));
		} else if (path.endsWith("/")) {
			@SuppressWarnings("unchecked")
			Set<String> childPaths = context.getResourcePaths(path);
			if (childPaths == null) {
				sourcePaths.add(new SourcePath(path, false));
			} else {
				for (String childPath : childPaths) {
					collect(childPath, sourcePaths);
				}
			}
		} else {
			if (context.getResourceAsStream(path) == null) {
				sourcePaths.add(new SourcePath(path, false));
			} else {
				sourcePaths.add(new SourcePath(path, true));
			}
		}
	}

	private String getSource(String relativePath) throws Exception {
		InputStream input = context.getResourceAsStream(relativePath);
		return IOUtils.toString(input, "UTF-8");
	}

	public class SourcePath {

		private String path;

		private boolean visible;

		SourcePath(String path, boolean visible) {
			this.path = path;
			this.visible = visible;
		}

		public String getPath() {
			return path;
		}

		public boolean isVisible() {
			return visible;
		}

	}
}
