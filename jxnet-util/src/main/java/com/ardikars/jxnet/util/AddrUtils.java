/**
 * Copyright (C) 2017  Ardika Rommy Sanjaya
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

package com.ardikars.jxnet.util;

import com.ardikars.jxnet.exception.NotSupportedPlatformException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.2
 */
public class AddrUtils {

    public static String GetGatewayAddress() throws IOException {
        Process process = null;
        BufferedReader stdIn = null;
        String str = null;
        if (Platforms.isWindows()) {
            process = Runtime.getRuntime().exec("route PRINT -4");
            stdIn = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            str = stdIn.lines().filter(s -> s.contains("0.0.0.0"))
                    .findFirst().orElse(null);
        } else if (Platforms.isLinux()) {
            process = Runtime.getRuntime().exec("route -n");
            stdIn = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            str = stdIn.lines().filter(s -> s.contains("0.0.0.0"))
                    .filter(s -> s.contains("UG"))
                    .findFirst().orElse(null);
        } else {
            throw new NotSupportedPlatformException();
        }
        if (str == null) return null;
        String[] strings = str.replaceFirst("0.0.0.0", "").split(" ");
        for (String s : strings) {
            if (!s.equals("")) {
                str = s;
                break;
            }
        }
        return str;
    }

    public static void main(String[] args) {
        try {
            String str = GetGatewayAddress();
            System.out.println(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
