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
import java.io.PrintStream;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.Executor;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.5
 */
public class DefaultPrinter implements Printer {

    @Override
    public void print(String name, Object message, Logger.Level level, PrintStream printStream, Writer writer, Executor executor) {
        if (Logger.Level.getLevels().contains(level)) {
            String value = " [ "
                    + level + " ] [ "
                    + name + " ] : "
                    + message;
            if (executor == null) {
                printStream.println(level.getColor().getAnsiColorCode() + value);
            } else {
                executor.execute(() -> printStream.println(level.getColor().getAnsiColorCode() + value));
            }
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

}
