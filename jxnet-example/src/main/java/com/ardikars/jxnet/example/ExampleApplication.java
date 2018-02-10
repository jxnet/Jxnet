package com.ardikars.jxnet.example; /**
 * Copyright (C) 2017-2018  Ardika Rommy Sanjaya <contact@ardikars.com>
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

import com.ardikars.jxnet.Application;
import com.ardikars.jxnet.annotation.Component;
import com.ardikars.jxnet.annotation.Configuration;
import com.ardikars.jxnet.annotation.Inject;
import com.ardikars.jxnet.annotation.Order;

//@Order(2)
@Configuration("exampleApplication")
public class ExampleApplication {

    @Inject("libraryVersion")
    public String libVersion;

    @Inject("errbuf")
    public StringBuilder errbuf;

//    @Inject("source")
//    public String source;

    public ExampleApplication() {

    }

    public static void main(final String[] args) {
        Application.run("Example Application", "0.0.1", new ExampleApplicationInitializer());
        Application.Context context = Application.getApplicationContext();
        ExampleApplication exampleApplication = context.getProperty("exampleApplication", ExampleApplication.class);
        System.out.println(exampleApplication);

    }

    @Override
    public String toString() {
        return "ExampleApplication{" +
                "libVersion='" + libVersion + '\'' +
                ", errbuf=" + errbuf +
//                ", source='" + source + '\'' +
                '}';
    }

}
