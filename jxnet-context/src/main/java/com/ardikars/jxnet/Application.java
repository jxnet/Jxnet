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

import com.ardikars.common.util.Builder;
import com.ardikars.common.util.Validate;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.5
 */
public final class Application {

    private static final Application instance = new Application();

    private Context context;

    private Application() {

    }

    /**
     * Bootstraping application.
     * @param builder pcap builder.
     */
    public static void run(Builder<Pcap, Void> builder) {
        Validate.notIllegalArgument(builder != null,
                new IllegalArgumentException("Pcap builder should be not null."));
        instance.context = new ApplicationContext(builder);
    }

    /**
     * Get application context.
     * @return application context.
     */
    public static Context getApplicationContext() {
        return instance.context;
    }

}
