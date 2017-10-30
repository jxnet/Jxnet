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

import java.io.PrintStream;
import java.io.Writer;
import java.util.concurrent.Executor;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.5
 */
@FunctionalInterface
public interface Printer {

    enum Color {

        RESET("\u001B[0m"),
        BLACK("\u001B[30m"),
        RED("\u001B[31m"),
        GREEN("\u001B[32m"),
        YELLOW("\u001B[33m"),
        BLUE("\u001B[34m"),
        PURPLE("\u001B[35m"),
        CYAN("\u001B[36m"),
        WHITE("\u001B[37m");

        private final String ansiColorCode;

        Color(String ansiColorCode) {
            this.ansiColorCode = ansiColorCode;
        }

        public String getAnsiColorCode() {
            return ansiColorCode;
        }

    }

    void print(String name, Object message, Logger.Level level, PrintStream printStream, Writer writer, Executor executor);

}
