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

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
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

    private final Set<Library.Loader> libraryLoaders = Collections.checkedSet(new HashSet<Library.Loader>(), Library.Loader.class);
    private final Map<String, Object> registry = Collections.synchronizedMap(new WeakHashMap<String, Object>());

    protected boolean isLoaded() {
        return this.loaded;
    }

    /**
     * Enable development mode will be force to use default installed library on the system.
     */
    public void enableDevelopmentMode() {
        this.developmentMode = true;
    }

    private Application() {

    }

    /**
     * Get instance of Application.
     * @return single instance of Application.
     */
    protected static Application getInstance() {
        synchronized (Application.class) {
            return instance;
        }
    }

    /**
     * Add library will be used (static/dynamic).
     * @param libraryLoader library loader.
     */
    protected void addLibrary(final Library.Loader libraryLoader) {
        this.libraryLoaders.add(libraryLoader);
    }

    /**
     * Get property from the container.
     * @param key property key.
     * @return object.
     */
    protected Object getProperty(final String key) {
        if (this.getProperties().get(key) == null) {
            throw new PropertyNotFoundException("Property with name " + key + " not found.");
        }
        return this.getProperties().get(key);
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
            LOGGER.warning(e.getMessage());
            return;
        } catch (IllegalAccessException e) {
            LOGGER.warning(e.getMessage());
            return;
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

        getInstance().getProperties().put("applicationInitializer", initializer);
        getInstance().getProperties().put("applicationContext", Application.getInstance().getContext());
    }

    /**
     * Get application name.
     * @return application name.
     */
    protected String getApplicationName() {
        return this.applicationName;
    }

    /**
     * Get application version.
     * @return application version.
     */
    protected String getApplicationVersion() {
        return this.applicationVersion;
    }

    /**
     * Get application context.
     * @return application context.
     */
    protected Context getContext() {
        return this.context;
    }

    /**
     * Get properties.
     * @return properties.
     */
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

        void removeProperty(String key);

        Map<String, Object> getProperties();

        void addLibrary(Library.Loader libraryLoader);

    }

}
