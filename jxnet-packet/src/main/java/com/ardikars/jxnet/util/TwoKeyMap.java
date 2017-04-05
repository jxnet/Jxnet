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
public class TwoKeyMap<T, U> {

    private final T firstKey;
    private final U secondKey;

    private TwoKeyMap(T firstKey, U secondKey) {
        this.firstKey = firstKey;
        this.secondKey = secondKey;
    }

    public static <T, U> TwoKeyMap<T, U> newInstance(T firstKey, U secondKey) {
        return new TwoKeyMap<T, U>(firstKey, secondKey);
    }

    public T getFirstKey() {
        return this.firstKey;
    }

    public U getSecondKey() {
        return this.secondKey;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        if (!(obj instanceof TwoKeyMap)) {
            return false;
        }
        return (this.firstKey.equals(this.getClass().cast(obj).getFirstKey()) &&
                this.secondKey.equals(this.getClass().cast(obj).getSecondKey()));
    }

    @Override
    public int hashCode() {
        return 17 * 37 + this.getFirstKey().hashCode() + this.getSecondKey().hashCode();
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("[First Key: ").append(this.getFirstKey().toString())
                .append(", Second Key: ").append(this.getSecondKey().toString())
                .append("]").toString();
    }

}
