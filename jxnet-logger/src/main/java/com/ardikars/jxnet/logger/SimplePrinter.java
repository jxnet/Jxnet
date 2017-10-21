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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.5
 */
public class SimplePrinter implements Printer {

    private final Writer writer;

    public SimplePrinter() {
        this.writer = null;
    }

    public SimplePrinter(Writer writer) {
        this.writer = writer;
    }

    private Locale locale = Locale.ENGLISH;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(
            "dd-MM-yyyy HH:mm:ss", locale
    );

    @Override
    public void print(String name, Object message, Logger.Level level) {
        String value = " [ "
                + level + " ] [ "
                + LocalDateTime.now().format(dateTimeFormatter) + " ] [ "
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

    public SimplePrinter setDateTimeFormatter(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
        return this;
    }

    public SimplePrinter setLocale(Locale locale) {
        this.locale = locale;
        return this;
    }

}
