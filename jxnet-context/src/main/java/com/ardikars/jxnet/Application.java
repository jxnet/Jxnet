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

import java.util.logging.Logger;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.5
 */
public final class Application {

    private static final Logger LOGGER = Logger.getLogger(Application.class.getName());

    private boolean loaded;
    private boolean developmentMode;

    private static final Application instance = new Application();

    private Context context;

    public boolean isLoaded() {
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
     * Used for bootstraping Jxnet.
     * @param applicationName application name.
     * @param applicationVersion application version.
     * @param initializerClass initializer class.
     * @param pcapBuilder pcap builder.
     * @param argements additional information.
     * @throws UnsatisfiedLinkError UnsatisfiedLinkError.
     */
    @SuppressWarnings("PMD.AvoidUsingNativeCode")
    public static void run(final String applicationName, final String applicationVersion, Class initializerClass,
                           final Pcap.Builder pcapBuilder,
                           final Object argements) {
        run(applicationName, applicationVersion, initializerClass, pcapBuilder, null, argements);
    }

    /**
     * Used for bootstraping Jxnet.
     * @param applicationName application name.
     * @param applicationVersion application version.
     * @param initializerClass initializer class.
     * @param pcapBuilder pcap builder.
     * @param bpfBuilder bpf builder.
     * @param argements additional information.
     * @throws UnsatisfiedLinkError UnsatisfiedLinkError.
     */
    @SuppressWarnings("PMD.AvoidUsingNativeCode")
    public static void run(final String applicationName, final String applicationVersion, Class initializerClass,
                               final Pcap.Builder pcapBuilder, final BpfProgram.Builder bpfBuilder,
                               final Object argements) {

        ApplicationInitializer initializer;
        Loader<Void> libraryLoaders;
        try {
            initializer = (ApplicationInitializer) initializerClass.newInstance();
            libraryLoaders = initializer.initialize(argements);
        } catch (InstantiationException e) {
            LOGGER.warning(e.getMessage());
            return;
        } catch (IllegalAccessException e) {
            LOGGER.warning(e.getMessage());
            return;
        }

        if (instance.developmentMode && !instance.loaded) {
            try {
                System.loadLibrary("jxnet");
                instance.loaded = true;
            } catch (Exception e) {
                instance.loaded = false;
            }
        } else {
            if (!instance.loaded && libraryLoaders != null) {
                libraryLoaders.load(new Callback() {
                    @Override
                    public void onSuccess(Object value) {
                        instance.loaded = true;
                        Pcap pcap = pcapBuilder.build();
                        if (bpfBuilder != null) {
                            BpfProgram bpfProgram = bpfBuilder.pcap(pcap).build();
                            instance.context = ApplicationContext
                                    .newApplicationContext(applicationName, applicationVersion, argements, pcap, bpfProgram);
                        } else {
                            instance.context = ApplicationContext
                                    .newApplicationContext(applicationName, applicationVersion, argements, pcap, null);
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        LOGGER.warning(throwable.getMessage());
                    }
                });
            }
        }
    }

    /**
     * Get application context.
     * @return application context.
     */
    public static Context getApplicationContext() {
        return instance.context;
    }

}
