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

import java.io.Writer;
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

    }

    private static Level level = null;

    private final String name;
    private final Printer printer;
    private final Executor executor;

    private Logger(String name, Printer printer) {
        Validate.notNull(name, "Logger name is null.");
        Validate.notNull(printer, "Logger printer is null.");
        this.name = name;
        this.printer = printer;
        this.executor = null;
    }

    private Logger(String name, Printer printer, Executor executor) {
        Validate.notNull(name, "Logger name is null.");
        Validate.notNull(printer, "Logger printer is null.");
        Validate.notNull(executor, "Logger executor is null");
        this.name = name;
        this.printer = printer;
        this.executor = executor;
    }

    public static Logger getLogger(Class clazz, Printer printer) {
        Validate.notNull(clazz, "Logger name is null.");
        return new Logger(clazz.getName(), printer);
    }

    public static Logger getLogger(Class clazz, Printer printer, Executor executor) {
        Validate.notNull(clazz, "Logger name is null.");
        return new Logger(clazz.getName(), printer, executor);
    }

    public static Logger getLogger(String name, Printer printer) {
        return new Logger(name, printer);
    }

    public static Logger getLogger(String name, Printer printer, Executor executor) {
        return new Logger(name, printer, executor);
    }

    public void info(Object message) {
        if (executor == null) {
            printer.print(name, message, Level.INFO);
        } else {
            executor.execute(() -> printer.print(name, message, Level.INFO));
        }
    }

    public void warn(Object message) {
        if (executor == null) {
            printer.print(name, message, Level.WARN);
        } else {
            executor.execute(() -> printer.print(name, message, Level.WARN));
        }
    }

    public void debug(Object message) {
        if (executor == null) {
            printer.print(name, message, Level.DEBUG);
        } else {
            executor.execute(() -> printer.print(name, message, Level.DEBUG));
        }
    }

    public void error(Object message) {
        if (executor == null) {
            printer.print(name, message, Level.ERROR);
        } else {
            executor.execute(() -> printer.print(name, message, Level.ERROR));
        }
    }

    public void info(Class<?> clazz, Object message) {
        Validate.notNull(clazz, "Logger name is null.");
        if (executor == null) {
            printer.print(clazz.getName(), message, Level.INFO);
        } else {
            executor.execute(() -> printer.print(clazz.getName(), message, Level.INFO));
        }
    }

    public void warn(Class<?> clazz, Object message) {
        Validate.notNull(clazz, "Logger name is null.");
        if (executor == null) {
            printer.print(clazz.getName(), message, Level.WARN);
        } else {
            executor.execute(() -> printer.print(clazz.getName(), message, Level.WARN));
        }
    }

    public void debug(Class<?> clazz, Object message) {
        Validate.notNull(clazz, "Logger name is null.");
        if (executor == null) {
            printer.print(clazz.getName(), message, Level.DEBUG);
        } else {
            executor.execute(() -> printer.print(clazz.getName(), message, Level.DEBUG));
        }
    }

    public void error(Class<?> clazz, Object message) {
        Validate.notNull(clazz, "Logger name is null.");
        if (executor == null) {
            printer.print(clazz.getName(), message, Level.ERROR);
        } else {
            executor.execute(() -> printer.print(clazz.getName(), message, Level.ERROR));
        }
    }

    public void info(String name, Object message) {
        if (executor == null) {
            printer.print(name, message, Level.INFO);
        } else {
            executor.execute(() -> printer.print(name, message, Level.INFO));
        }
    }

    public void warn(String name, Object message) {
        if (executor == null) {
            printer.print(name, message, Level.WARN);
        } else {
            executor.execute(() -> printer.print(name, message, Level.WARN));
        }
    }

    public void debug(String name, Object message) {
        if (executor == null) {
            printer.print(name, message, Level.DEBUG);
        } else {
            executor.execute(() -> printer.print(name, message, Level.DEBUG));
        }
    }

    public void error(String name, Object message) {
        if (executor == null) {
            printer.print(name, message, Level.ERROR);
        } else {
            executor.execute(() -> printer.print(name, message, Level.ERROR));
        }
    }

    public static void info(Class<?> clazz, Object message, Printer printer) {
        Validate.notNull(clazz, "Logger name is null.");
        Validate.notNull(printer, "Logger printer is null.");
        printer.print(clazz.getName(), message, Level.INFO);
    }

    public static void warn(Class<?> clazz, Object message, Printer printer) {
        Validate.notNull(clazz, "Logger name is null.");
        Validate.notNull(printer, "Logger printer is null.");
        printer.print(clazz.getName(), message, Level.WARN);
    }

    public static void debug(Class<?> clazz, Object message, Printer printer) {
        Validate.notNull(clazz, "Logger name is null.");
        Validate.notNull(printer, "Logger printer is null.");
        printer.print(clazz.getName(), message, Level.DEBUG);
    }

    public static void error(Class<?> clazz, Object message, Printer printer) {
        Validate.notNull(clazz, "Logger name is null.");
        Validate.notNull(printer, "Logger printer is null.");
        printer.print(clazz.getName(), message, Level.ERROR);
    }

    public static void info(Class<?> clazz, Object message, Printer printer, Executor executor) {
        Validate.notNull(clazz, "Logger name is null.");
        Validate.notNull(printer, "Logger printer is null.");
        Validate.notNull(executor, "Logger executor is null");
        executor.execute(() -> printer.print(clazz.getName(), message, Level.INFO));
    }

    public static void warn(Class<?> clazz, Object message, Printer printer, Executor executor) {
        Validate.notNull(clazz, "Logger name is null.");
        Validate.notNull(printer, "Logger printer is null.");
        Validate.notNull(executor, "Logger executor is null");
        executor.execute(() -> printer.print(clazz.getName(), message, Level.WARN));
    }

    public static void debug(Class<?> clazz, Object message, Printer printer, Executor executor) {
        Validate.notNull(clazz, "Logger name is null.");
        Validate.notNull(printer, "Logger printer is null.");
        Validate.notNull(executor, "Logger executor is null");
        executor.execute(() -> printer.print(clazz.getName(), message, Level.DEBUG));
    }

    public static void error(Class<?> clazz, Object message, Printer printer, Executor executor) {
        Validate.notNull(clazz, "Logger name is null.");
        Validate.notNull(printer, "Logger printer is null.");
        Validate.notNull(executor, "Logger executor is null");
        executor.execute(() -> printer.print(clazz.getName(), message, Level.ERROR));
    }

    public static void info(String name, Object message, Printer printer) {
        Validate.notNull(name, "Logger name is null.");
        Validate.notNull(printer, "Logger printer is null.");
        printer.print(name, message, Level.INFO);
    }

    public static void warn(String name, Object message, Printer printer) {
        Validate.notNull(name, "Logger name is null.");
        Validate.notNull(printer, "Logger printer is null.");
        printer.print(name, message, Level.WARN);
    }

    public static void debug(String name, Object message, Printer printer) {
        Validate.notNull(name, "Logger name is null.");
        Validate.notNull(printer, "Logger printer is null.");
        printer.print(name, message, Level.DEBUG);
    }

    public static void error(String name, Object message, Printer printer) {
        Validate.notNull(name, "Logger name is null.");
        Validate.notNull(printer, "Logger printer is null.");
        printer.print(name, message, Level.ERROR);
    }

    public static void info(String name, Object message, Printer printer, Executor executor) {
        Validate.notNull(name, "Logger name is null.");
        Validate.notNull(printer, "Logger printer is null.");
        Validate.notNull(executor, "Logger executor is null");
        executor.execute(() -> printer.print(name, message, Level.INFO));
    }

    public static void warn(String name, Object message, Printer printer, Executor executor) {
        Validate.notNull(name, "Logger name is null.");
        Validate.notNull(printer, "Logger printer is null.");
        Validate.notNull(executor, "Logger executor is null");
        executor.execute(() -> printer.print(name, message, Level.WARN));
    }

    public static void debug(String name, Object message, Printer printer, Executor executor) {
        Validate.notNull(name, "Logger name is null.");
        Validate.notNull(printer, "Logger printer is null.");
        Validate.notNull(executor, "Logger executor is null");
        executor.execute(() -> printer.print(name, message, Level.DEBUG));
    }

    public static void error(String name, Object message, Printer printer, Executor executor) {
        Validate.notNull(name, "Logger name is null.");
        Validate.notNull(printer, "Logger printer is null.");
        Validate.notNull(executor, "Logger executor is null");
        executor.execute(() -> printer.print(name, message, Level.ERROR));
    }

    public static void setLevel(Level level) {
        Logger.level = level;
    }

    public static Level getLevel() {
        return Logger.level;
    }

}
