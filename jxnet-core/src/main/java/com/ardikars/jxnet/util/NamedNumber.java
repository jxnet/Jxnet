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

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.0.0
 */
public abstract class NamedNumber<T extends Number, U extends NamedNumber<T, ?>> {

    private final T value;
    private final String name;

    protected NamedNumber(T value, String name) {
        this.value = value;
        this.name = name;
    }

    public T getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!this.getClass().isInstance(obj)) {
            return false;
        }
        return this.value.equals(this.getClass().cast(obj).getValue());
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return new StringBuffer("[Type: ")
                .append(this.value.toString())
                .append(", Name: ")
                .append(this.name.toString())
                .append("]").toString();
    }

}
