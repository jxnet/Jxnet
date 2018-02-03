/**
 * Copyright (C) 2017-2018  Ardika Rommy Sanjaya <contact@ardikars.com>
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.5
 */
public class Application {

    private List<Library.Loader> libraryLoaders;
    private boolean loaded;
    private boolean developmentMode;

    private static Application instance;

    private String applicationName;
    private String applicationVersion;
    private Map<String, Object> properties;
    private Context context;

    protected boolean isLoaded() {
        return this.loaded;
    }

    public void enableDevelopmentMode() {
        this.developmentMode = true;
    }

    private Application() {

    }

    protected static Application getInstance() {
        synchronized (Application.class) {
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
    }

    protected void addLibrary(final Library.Loader libraryLoader) {
        this.libraryLoaders.add(libraryLoader);
    }

    protected void addProperty(final String key, final Object value) {
        this.properties.put(key, value);
    }

    protected Object getProperty(final String key) {
        return this.properties.get(key);
    }

    /**
     * Used for bootstraping Jxnet.
     * @param applicationName application name.
     * @param applicationVersion application version.
     * @param initializer initializer.
     * @throws UnsatisfiedLinkError UnsatisfiedLinkError.
     */
    public static void run(final String applicationName, final String applicationVersion
            , final ApplicationInitializer initializer) {
        getInstance().applicationName = applicationName;
        getInstance().applicationVersion = applicationVersion;
        getInstance().context = new ApplicationContext();

        initializer.initialize(getInstance().getContext());

        if (Platforms.isWindows()) {
            String paths = System.getProperty("java.library.path");
            final String path = "C:\\Windows\\System32\\Npcap";
            final String pathSparator = File.pathSeparator;
            final String[] libraryPaths = paths.split(pathSparator);
            boolean isAdded = false;
            for (final String str : libraryPaths) {
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
                for (final Library.Loader loader : getInstance().libraryLoaders) {
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
        return this.applicationName;
    }

    protected String getApplicationVersion() {
        return this.applicationVersion;
    }

    protected Context getContext() {
        return this.context;
    }

    /**
     * Get application context.
      * @return application context.
     */
    public static Application.Context getApplicationContext() {
        final Application.Context context = getInstance().getContext();
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
