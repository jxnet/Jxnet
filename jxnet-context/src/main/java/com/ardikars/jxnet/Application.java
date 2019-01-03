/**
 * Copyright (C) 2015-2018 Jxnet
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
 * Helper class for bootstraping jxnet application.
 *
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.1.5
 * @deprecated please use {@link com.ardikars.jxnet.context.Application}.
 */
@Deprecated
public class Application {

    /**
     * Bootstraping application.
     * @param aplicationName application name.
     * @param applicationDisplayName application display name.
     * @param applicationVersion application version.
     * @param builder pcap builder.
     */
    public static void run(String aplicationName, String applicationDisplayName, String applicationVersion, Builder<Pcap, Void> builder) {
        Validate.notIllegalArgument(builder != null,
                new IllegalArgumentException("Pcap builder should be not null."));
        com.ardikars.jxnet.context.Application.run(aplicationName, applicationDisplayName, applicationVersion, builder);
    }

    /**
     * Get application context.
     * @return application context.
     */
    public static Context getApplicationContext() {
        com.ardikars.jxnet.context.Context context = com.ardikars.jxnet.context.Application.getApplicationContext();
        return new ApplicationContext(context);
    }

}
