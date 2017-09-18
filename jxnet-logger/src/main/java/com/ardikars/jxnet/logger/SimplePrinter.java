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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.5
 */
public class SimplePrinter implements Printer {

    private Locale locale = Locale.ENGLISH;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(
            "dd-MM-yyyy HH:mm:ss", locale
    );

    @Override
    public void print(Object message, Class<?> clazz, Logger.Level level) {
        if (Logger.getLevel() == null || Logger.getLevel() == level) {
            System.out.println(level.getColor().getAnsiColorCode() + " [ "
                    + level + " ] [ "
                    + LocalDateTime.now().format(dateTimeFormatter) + " ] [ "
                    + clazz.getName() + " ] : "
                    + message);
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
