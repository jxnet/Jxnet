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

package com.ardikars.jxnet.logger;

import java.io.IOException;
import java.io.Writer;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.5
 */
public class DefaultPrinter implements Printer {

    private final Writer writer;

    public DefaultPrinter() {
        this.writer = null;
    }

    public DefaultPrinter(Writer writer) {
        this.writer = writer;
    }

    @Override
    public void print(String name, Object message, Logger.Level level) {
        String value = " [ "
                + level + " ] [ "
                + name + " ] : "
                + message;
        System.out.println(level.getColor().getAnsiColorCode() + value);
        if (writer != null) {
            try {
                writer.write(value);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
