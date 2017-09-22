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

import java.util.Collection;
import java.util.Map;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.5
 */
public class Logger {

    public enum Level {

        INFO(Printer.Color.CYAN),
        WARN(Printer.Color.YELLOW),
        DEBUG(Printer.Color.BLUE),
        ERROR(Printer.Color.RED);

        private Printer.Color color;

        private Level(Printer.Color color) {
            this.color = color;
        }

        public Level setColor(Printer.Color ansiColorCode) {
            this.color = ansiColorCode;
            return this;
        }

        public Printer.Color getColor() {
            return color;
        }

    }

    private static Level level = null;

    private final Class clazz;
    private final Printer printer;

    private Logger(Class clazz, Printer printer) {
        this.clazz = clazz;
        this.printer = printer;
    }

    public static Logger getLogger(Class clazz, Printer printer) {
        return new Logger(clazz, printer);
    }

    public void info(Object message) {
        printer.print(message, clazz, Level.INFO);
    }

    public void warn(Object message) {
        printer.print(message, clazz, Level.WARN);
    }

    public void debug(Object message) {
        printer.print(message, clazz, Level.DEBUG);
    }

    public void error(Object message) {
        printer.print(message, clazz, Level.ERROR);
    }

    public void info(Object message, Class<?> clazz) {
        printer.print(message, clazz, Level.INFO);
    }

    public void warn(Object message, Class<?> clazz) {
        printer.print(message, clazz, Level.WARN);
    }

    public void debug(Object message, Class<?> clazz) {
        printer.print(message, clazz, Level.DEBUG);
    }

    public void error(Object message, Class<?> clazz) {
        printer.print(message, clazz, Level.ERROR);
    }

    public static void info(Object message, Class<?> clazz, Printer printer) {
        printer.print(message, clazz, Level.INFO);
    }

    public static void warn(Object message, Class<?> clazz, Printer printer) {
        printer.print(message, clazz, Level.WARN);
    }

    public static void debug(Object message, Class<?> clazz, Printer printer) {
        printer.print(message, clazz, Level.DEBUG);
    }

    public static void error(Object message, Class<?> clazz, Printer printer) {
        printer.print(message, clazz, Level.ERROR);
    }

    public static void setLevel(Level level) {
        Logger.level = level;
    }

    public static Level getLevel() {
        return Logger.level;
    }

}
