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
 * @since 1.1.0
 */
public abstract class NamedTwoKeyMap<T, U, V extends NamedTwoKeyMap<T, U, ?>> {

    private final TwoKeyMap<T, U> key;
    private final String name;

    public NamedTwoKeyMap(T firstKey, U secondKey, String name) {
        this.key = TwoKeyMap.newInstance(firstKey, secondKey);
        this.name = name;
    }

    public TwoKeyMap<T, U> getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        if (!(obj instanceof NamedTwoKeyMap)) {
            return false;
        }
        return this.key.equals(this.getClass().cast(obj).getKey());
    }

    @Override
    public int hashCode() {
        return 17 * 37 + this.getKey().hashCode();
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("[Key: ").append(getKey().toString())
                .append(", Name: ").append(this.getName().toString())
                .append("]").toString();
    }

}
