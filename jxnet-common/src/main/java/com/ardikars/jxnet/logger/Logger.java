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

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.0
 */
public class Logger {

    public enum Level {

        INFO(Printer.Color.WHITE),
        WARN(Printer.Color.YELLOW),
        DEBUG(Printer.Color.GREEN),
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

    public void info(String message) {
        printer.print(message, clazz, Level.INFO);
    }

    public void warn(String message) {
        printer.print(message, clazz, Level.WARN);
    }

    public void debug(String message) {
        printer.print(message, clazz, Level.DEBUG);
    }

    public void error(String message) {
        printer.print(message, clazz, Level.ERROR);
    }

    public void info(String message, Class<?> clazz) {
        printer.print(message, clazz, Level.INFO);
    }

    public void warn(String message, Class<?> clazz ) {
        printer.print(message, clazz, Level.WARN);
    }

    public void debug(String message, Class<?> clazz) {
        printer.print(message, clazz, Level.DEBUG);
    }

    public void error(String message, Class<?> clazz) {
        printer.print(message, clazz, Level.ERROR);
    }

    public static void setLevel(Level level) {
        Logger.level = level;
    }

    public static Level getLevel() {
        return Logger.level;
    }

}
