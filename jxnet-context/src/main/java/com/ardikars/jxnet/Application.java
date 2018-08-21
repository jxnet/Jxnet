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

import com.ardikars.common.util.Callback;
import com.ardikars.common.util.Loader;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
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

    private final Set<Loader> libraryLoaders = Collections.checkedSet(new HashSet<Loader>(), Loader.class);

    protected boolean isLoaded() {
        return this.loaded;
    }

    /**
     * Enable development mode will be force to use default installed library on the system.
     */
    public void enableDevelopmentMode() {
        this.developmentMode = true;
    }

    protected Application() {

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
    protected void addLibrary(final Loader libraryLoader) {
        this.libraryLoaders.add(libraryLoader);
    }

    /**
     * Used for bootstraping Jxnet.
     * @param applicationName application name.
     * @param applicationVersion application version.
     * @param initializerClass initializer class.
     * @param applicationContext application context.
     * @throws UnsatisfiedLinkError UnsatisfiedLinkError.
     */
    @SuppressWarnings("PMD.AvoidUsingNativeCode")
    public static void run(final String applicationName, final String applicationVersion,
                           Class initializerClass, Context applicationContext) {

        getInstance().applicationName = applicationName;
        getInstance().applicationVersion = applicationVersion;
        getInstance().context = applicationContext;

        ApplicationInitializer initializer;
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
                for (final Loader loader : getInstance().libraryLoaders) {
                    if (getInstance().loaded) {
                        break;
                    }
                    loader.load(new Callback() {
                        @Override
                        public void onSuccess(Object value) {
                            getInstance().loaded = true;
                        }

                        @Override
                        public void onFailure(Throwable throwable) {
                            //
                        }
                    });
                }
            }
        }
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
     * Get application context.
     * @return application context.
     */
    public static Context getApplicationContext() {
        return getInstance().getContext();
    }

}
