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

import com.ardikars.jxnet.exception.PropertyNotFoundException;
import com.ardikars.jxnet.util.Platforms;
import com.ardikars.jxnet.util.PropertyUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.logging.Logger;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.5
 */
public class Application {

    private static final Logger LOGGER = Logger.getLogger(Application.class.getName());

    private boolean loaded;
    private boolean developmentMode;

    private static final Application instance = new Application();

    private String applicationName;
    private String applicationVersion;
    private Context context;

    private final List<Library.Loader> libraryLoaders = new ArrayList<>();
    private final Map<String, Object> registry = Collections.synchronizedMap(new WeakHashMap<String, Object>());
    private final List<Properties> propertyFiles = new ArrayList<>();
    private final Set<Class> classes = Collections.synchronizedSet(new HashSet<Class>());

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
            return instance;
        }
    }

    protected void addLibrary(final Library.Loader libraryLoader) {
        this.libraryLoaders.add(libraryLoader);
    }

    protected Object getProperty(final String key) throws PropertyNotFoundException{
        if (this.getProperties().get(key) == null) {
            throw new PropertyNotFoundException("Property with name " + key + " not found.");
        }
        return this.getProperties().get(key);
    }

    protected void addPropertyPackages(String basePackage) {
        Set<Class> classes = PropertyUtils.getClasses(basePackage);
        getInstance().classes.addAll(classes);
    }

    protected void addPropertyClasses(Class... classes) {
        for (Class aClass : classes) {
            getInstance().classes.add(aClass);
        }
    }

    protected void addPropertyFiles(String... propertyFiles) {
        final String classpath = "classpath:";
        for (String propertyFile : propertyFiles) {
            if (propertyFile.startsWith(classpath)) {
                propertyFile = propertyFile.substring(classpath.length(), propertyFile.length());
                InputStream stream = ClassLoader.getSystemResourceAsStream(propertyFile);
                Properties properties = new Properties();
                try {
                    properties.load(stream);
                    getInstance().propertyFiles.add(properties);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Used for bootstraping Jxnet.
     * @param applicationName application name.
     * @param applicationVersion application version.
     * @param initializerClass initializer class.
     * @throws UnsatisfiedLinkError UnsatisfiedLinkError.
     */
    public static void run(final String applicationName, final String applicationVersion,
                           Class initializerClass) {

        getInstance().applicationName = applicationName;
        getInstance().applicationVersion = applicationVersion;
        getInstance().context = new ApplicationContext();

        ApplicationInitializer initializer = null;
        try {
            initializer = (ApplicationInitializer) initializerClass.newInstance();
            initializer.initialize(getInstance().context);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

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
                Field sysPathsField;
                try {
                    sysPathsField = ClassLoader.class.getDeclaredField("sys_paths");
                } catch (NoSuchFieldException e) {
                    LOGGER.warning(e.getMessage());
                    throw new UnsatisfiedLinkError(e.getMessage());
                }
                sysPathsField.setAccessible(true);
                try {
                    sysPathsField.set(null, null);
                } catch (IllegalAccessException e) {
                    LOGGER.warning(e.getMessage());
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

        if (getInstance().classes == null || getInstance().classes.size() <= 0) {
            getInstance().addPropertyPackages(initializerClass.getPackage().toString());
        }

        getInstance().getProperties().put("applicationInitializer", initializer);
        getInstance().getProperties().put("applicationContext", Application.getInstance().getContext());

        for (Properties propertyFile : getInstance().propertyFiles) {
            for (Map.Entry<Object, Object> property : propertyFile.entrySet()) {
                getInstance().getProperties().put((String) property.getKey(), property.getValue());
            }
        }

        Set<Class> classes = PropertyUtils.processClassOrder(getInstance().classes);

        try {
            PropertyUtils.createClassProperties(getInstance().getProperties(), classes);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        for (Class aClass : classes) {
            Set<Method> methods = PropertyUtils.processMethodOrder(aClass);
            try {
                PropertyUtils.createMethodProperties(getInstance().getProperties(), methods);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            Set<Field> fields = PropertyUtils.processFieldOrder(aClass);
            try {
                PropertyUtils.createFieldProperties(getInstance().getProperties(), fields);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        try {
            PropertyUtils.injectProperties(getInstance().getProperties());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
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

    public Map<String, Object> getProperties() {
        synchronized (this) {
            return registry;
        }
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

        Object getProperty(String key) throws PropertyNotFoundException;

        <T> T getProperty(String name, Class<T> requiredType) throws ClassCastException, PropertyNotFoundException;

        void removeProperty(String key) throws PropertyNotFoundException;

        Map<String, Object> getProperties();

        void addLibrary(Library.Loader libraryLoader);

        void addPropertyFiles(String... propertyFiles);

        void addPropertyPackages(String basePackage);

        void addPropertyClassses(Class... classes);

    }

}
