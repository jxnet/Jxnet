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
import com.ardikars.jxnet.annotation.Inject;
import com.ardikars.jxnet.annotation.Order;
import com.ardikars.jxnet.example.configuration.ExampleApplicationInitializer;

@Order(2)
@Component("exampleApplication")
public class ExampleApplication {

    @Inject("applicationContext") public Application.Context context;
    @Inject("errbuf")             public StringBuilder errbuf;
    @Inject("snaplen")            public int snaplen;
    @Inject("promisc")            public int promisc;
    @Inject("timeout")            public int timeout;

    public ExampleApplication() {

    }




    public static void main(final String[] args) {
        Application.run("Example Application", "0.0.1", ExampleApplicationInitializer.class);
        ExampleApplication exampleApplication = Application.getApplicationContext().getProperty("exampleApplication", ExampleApplication.class);
    }

}
