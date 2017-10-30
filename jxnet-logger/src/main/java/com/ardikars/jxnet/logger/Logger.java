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

import com.ardikars.jxnet.util.Validate;

import java.io.PrintStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

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

        Level(Printer.Color color) {
            this.color = color;
        }

        public Level setColor(Printer.Color ansiColorCode) {
            this.color = ansiColorCode;
            return this;
        }

        public Printer.Color getColor() {
            return color;
        }

        public static List<Level> getLevels() {
            return Logger.levels;
        }

    }

    private static List<Level> levels = new ArrayList<>();
    private static PrintStream printStream = null;
    private static Writer writer = null;
    private static Printer printer = null;
    private static Executor executor = null;

    private final String name;

    private Logger(String name) {
        Validate.notNull(name, "Logger name is null.");
        this.name = name;
        if (Logger.printStream == null) {
            Logger.printStream = System.out;
        }
        if (Logger.printer == null) {
            Logger.printer = new DefaultPrinter();
        }
    }

    public void info(Object message) {
        printer.print(name, message, Level.INFO, printStream, writer, executor);
    }

    public void warn(Object message) {
        printer.print(name, message, Level.WARN, printStream, writer, executor);
    }

    public void debug(Object message) {
        printer.print(name, message, Level.DEBUG, printStream, writer, executor);
    }

    public void error(Object message) {
        printer.print(name, message, Level.ERROR, printStream, writer, executor);
    }

    public void info(String name, Object message) {
        printer.print(name, message, Level.INFO, printStream, writer, executor);
    }

    public void warn(String name, Object message) {
        printer.print(name, message, Level.WARN, printStream, writer, executor);
    }

    public void debug(String name, Object message) {
        printer.print(name, message, Level.DEBUG, printStream, writer, executor);
    }

    public void error(String name, Object message) {
        printer.print(name, message, Level.ERROR, printStream, writer, executor);
    }

    public void info(Class<?> clazz, Object message) {
        Validate.notNull(clazz, "Logger name is null.");
        printer.print(clazz.getName(), message, Level.INFO, printStream, writer, executor);
    }

    public void warn(Class<?> clazz, Object message) {
        Validate.notNull(clazz, "Logger name is null.");
        printer.print(clazz.getName(), message, Level.WARN, printStream, writer, executor);
    }

    public void debug(Class<?> clazz, Object message) {
        Validate.notNull(clazz, "Logger name is null.");
        printer.print(clazz.getName(), message, Level.DEBUG, printStream, writer, executor);
    }

    public void error(Class<?> clazz, Object message) {
        Validate.notNull(clazz, "Logger name is null.");
        printer.print(clazz.getName(), message, Level.ERROR, printStream, writer, executor);
    }

    public static void setLevel(List<Level> levels) {
        Logger.levels = levels;
    }

    public static Printer getPrinter() {
        return Logger.printer;
    }

    public static void setPrinter(Printer printer) {
        Logger.printer = printer;
    }

    public static PrintStream getPrintStream() {
        return Logger.printStream;
    }

    public static void setPrintStream(PrintStream printStream) {
        Logger.printStream = printStream;
    }

    public static Writer getWriter() {
        return Logger.writer;
    }

    public static void setWriter(Writer writer) {
        Logger.writer = writer;
    }

    public static Executor getExecutor() {
        return Logger.executor;
    }

    public static void setExecutor(Executor executor) {
        Logger.executor = executor;
    }

    public static boolean isParralel() {
        return (Logger.executor != null);
    }

    public static boolean isWritable() {
        return (Logger.writer != null);
    }

    public static class Factory {

        private static Logger LOGGER = null;

        public static Logger getLogger() {
            if (Logger.Factory.LOGGER == null) {
                return new Logger("");
            } else if (!Factory.LOGGER.name.equals("")) {
                return new Logger("");
            }
            return LOGGER;
        }

        public static Logger getLogger(String name) {
            if (Logger.Factory.LOGGER == null) {
                return new Logger(name);
            } else if (!Factory.LOGGER.name.equals(name)) {
                return new Logger(name);
            }
            return LOGGER;
        }

        public static Logger getLogger(Class clazz) {
            Validate.notNull(clazz, "Logger name is null.");
            if (Logger.Factory.LOGGER == null) {
                return new Logger(clazz.getName());
            } else if (!Factory.LOGGER.name.equals(clazz.getName())) {
                return new Logger(clazz.getName());
            }
            return LOGGER;
        }

    }

}
