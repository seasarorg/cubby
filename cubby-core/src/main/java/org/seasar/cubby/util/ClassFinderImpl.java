/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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
package org.seasar.cubby.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.servlet.ServletContext;

//import org.seasar.framework.log.Logger;
//import org.seasar.framework.util.ClassUtil;

/**
 * @author Satoshi Kimura
 */
public class ClassFinderImpl implements ClassFinder {
//    private static final Log logger = Logger.getLogger(ClassFinderImpl.class);

    private static final String CLASS_FILE_EXTENTION = ".class";

    private static final int CLASS_FILE_EXTENTION_LENGTH = CLASS_FILE_EXTENTION.length();

    private static final char FILE_SEPARATOR = File.separatorChar;

    private static final String WEB_CLASSES_DIR = "/WEB-INF/classes";

    private static final String WEB_LIB_DIR = "/WEB-INF/lib";
    
    private static final String ALL_MATCHE_PATTERN = ".*";

    private Collection<Class> classCollection = new ArrayList<Class>();

    public ClassFinderImpl() {
    }

    public void find() {
        find(ALL_MATCHE_PATTERN);
    }

    public void find(String pattern) {
        find(true, pattern);

    }

    public void find(boolean enableJar, String pattern) {
        String cp = System.getProperty("java.class.path");
        String ps = System.getProperty("path.separator");

        for (StringTokenizer tokenizer = new StringTokenizer(cp, ps); tokenizer.hasMoreTokens();) {
            String path = tokenizer.nextToken();
            loadAllClass(path, enableJar, pattern);
        }
    }

    public void find(boolean enableJar) {
        find(enableJar, ALL_MATCHE_PATTERN);
    }

    public void find(String path, boolean enableJar, String pattern) {
        loadAllClass(path, enableJar, pattern);
    }

    public void find(String path, boolean enableJar) {
        find(path, enableJar, ALL_MATCHE_PATTERN);
    }

    public void find(File file, boolean enableJar, String pattern) {
        loadAllClass(file.getAbsolutePath(), enableJar, pattern);
    }

    public void find(File file, boolean enableJar) {
        find(file.getAbsolutePath(), enableJar, ALL_MATCHE_PATTERN);
    }

    public void find(ServletContext context, boolean enableJar) {
        find(context, enableJar, ALL_MATCHE_PATTERN);
    }

    public void find(ServletContext context, boolean enableJar, String pattern) {
        String classesDirPath = context.getRealPath(WEB_CLASSES_DIR);
        find(classesDirPath, enableJar, pattern);

        String libDirPath = context.getRealPath(WEB_LIB_DIR);
        File[] files = new File(libDirPath).listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                find(files[i], enableJar, pattern);
            }
        }
    }

    private void loadAllClass(String classpath, boolean enableJar, String pattern) {
        File path = new File(classpath);
        if (!path.exists()) {
            return;
        }

        if (path.isDirectory()) {
            loadFromDir(path, path, pattern);
        } else if (enableJar) {
            loadFromJar(path, pattern);
        }
    }

    private void loadFromJar(File path, String pattern) {
        JarFile jarFile = createJarFileInstance(path);
        if (jarFile == null) {
            return;
        }

        for (Enumeration entries = jarFile.entries(); entries.hasMoreElements();) {
            String entryName = ((JarEntry) entries.nextElement()).getName();
            if (entryName.endsWith(CLASS_FILE_EXTENTION)) {
                String classResourceName = entryName;
                Class clazz = forResourceName(classResourceName);
                addToCollection(clazz, pattern);
            }
        }
    }

    private static JarFile createJarFileInstance(File path) {
        try {
            return new JarFile(path);
        } catch (IOException e) {
//            logger.warn(e.toString());
            return null;
        }
    }

    private void loadFromDir(File rootPath, File path, String pattern) {
        File[] files = path.listFiles();
        int rootPathDirNameLength = rootPath.getAbsolutePath().length() + 1;
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                loadFromDir(rootPath, files[i], pattern);
            } else {
                if (files[i].getName().endsWith(CLASS_FILE_EXTENTION)) {
                    String classFilePath = files[i].getAbsolutePath();
                    String classResourceName = classFilePath.substring(rootPathDirNameLength);
                    Class clazz = forResourceName(classResourceName);
                    addToCollection(clazz, pattern);
                }
            }
        }
    }

    private synchronized void addToCollection(Class clazz, String pattern) {
        if (clazz == null) {
            return;
        }
        if (clazz.getName().matches(pattern)) {
            this.classCollection.add(clazz);
        }
    }

    private static final Class forResourceName(String classResourceName) {
        String className = classResourceName.substring(0, classResourceName.length() - CLASS_FILE_EXTENTION_LENGTH);
        className = className.replace(FILE_SEPARATOR, '.');
        className = className.replace('/', '.');
        try {
            Class clazz = ClassUtils.forName(className);
            return clazz;
        } catch (NoClassDefFoundError e) {
//            logger.warn(e.toString());
            return null;
        } catch (UnsatisfiedLinkError e) {
//            logger.warn(e.toString());
            return null;
        }
    }

    public synchronized Collection<Class> getClassCollection() {
        return this.classCollection;
    }

    public synchronized void destroy() {
        this.classCollection = new ArrayList<Class>();
    }
}
