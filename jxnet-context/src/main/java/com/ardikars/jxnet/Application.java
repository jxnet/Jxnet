/**
 * Copyright (C) 2018  Ardika Rommy Sanjaya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.ardikars.jxnet;

import com.ardikars.jxnet.util.Platforms;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.5
 */
public class Application {

    private List<Library.Loader> libraryLoaders;
    private boolean loaded;
    private boolean developmentMode = false;

    private static Application instance;

    private String applicationName;
    private String applicationVersion;
    private Map<String, Object> properties;
    private Context context;

    protected boolean isLoaded() {
        return loaded;
    }

    public void enableDevelopmentMode() {
        this.developmentMode = true;
    }

    private Application() {

    }

    protected static Application getInstance() {
        if (instance == null) {
            instance = new Application();
            if (instance.libraryLoaders == null) {
                instance.libraryLoaders = new ArrayList<Library.Loader>();
            }
            if (instance.properties == null) {
                instance.properties = new HashMap<String, Object>();
            }
        }
        return instance;
    }

    protected void addLibrary(Library.Loader libraryLoader) {
        libraryLoaders.add(libraryLoader);
    }

    protected void addProperty(String key, Object value) {
        properties.put(key, value);
    }

    protected Object getProperty(String key) {
        return properties.get(key);
    }

    public static void run(String applicationName, String applicationVersion, ApplicationInitializer initializer) throws UnsatisfiedLinkError {
        getInstance().applicationName = applicationName;
        getInstance().applicationVersion = applicationVersion;
        getInstance().context = new ApplicationContext();

        initializer.initialize(getInstance().getContext());

        if (Platforms.isWindows()) {
            String path = "C:\\Windows\\System32\\Npcap";
            String paths = System.getProperty("java.library.path");
            String pathSparator = File.pathSeparator;
            String[] libraryPaths = paths.split(pathSparator);
            boolean isAdded = false;
            for (String str : libraryPaths) {
                if (str.equals(path)) {
                    isAdded = true;
                    break;
                }
            }
            if (!isAdded) {
                paths = paths.concat(pathSparator + path);
                System.setProperty("java.library.path", paths);
                Field sysPathsField = null;
                try {
                    sysPathsField = ClassLoader.class.getDeclaredField("sys_paths");
                } catch (NoSuchFieldException e) {
                    throw new UnsatisfiedLinkError(e.getMessage());
                }
                sysPathsField.setAccessible(true);
                try {
                    sysPathsField.set(null, null);
                } catch (IllegalAccessException e) {
                    throw new UnsatisfiedLinkError(e.getMessage());
                }
            }
        }

        if (getInstance().developmentMode && !getInstance().loaded) {
            try {
                System.loadLibrary("jxnet");
                getInstance().loaded = true;
            } catch (Exception e) {
                getInstance().loaded = false;
            }
        } else {
            if (!getInstance().loaded && getInstance().libraryLoaders != null && !getInstance().libraryLoaders.isEmpty()) {
                for (Library.Loader loader : getInstance().libraryLoaders) {
                    try {
                        loader.load();
                        getInstance().loaded = true;
                        break;
                    } catch (UnsatisfiedLinkError e) {
                        continue;
                    }
                }
            }
        }
    }

    protected String getApplicationName() {
        return applicationName;
    }

    protected String getApplicationVersion() {
        return applicationVersion;
    }

    protected Context getContext() {
        return context;
    }

    public static Application.Context getApplicationContext() {
        Application.Context context = getInstance().getContext();
        if (context == null) {
            throw new NullPointerException("No application context found.");
        }
        return context;
    }

    public interface Context {

        String getApplicationName();

        String getApplicationVersion();

        void addProperty(String key, Object value);

        Object getProperty(String key);

        void addLibrary(Library.Loader libraryLoader);

    }

}
