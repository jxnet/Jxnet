/**
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

package com.ardikars.jxnet;

import com.ardikars.jxnet.exception.PropertyNotFoundException;

import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.5
 */
public class ApplicationContext implements Application.Context {

	private final Logger logger = Logger.getLogger(ApplicationContext.class.getName());

    @Override
    public String getApplicationName() {
        return Application.getInstance().getApplicationName();
    }

    @Override
    public String getApplicationVersion() {
        return Application.getInstance().getApplicationVersion();
    }

    @Override
    public Object getProperty(final String key) throws PropertyNotFoundException{
        return Application.getInstance().getProperty(key);
    }

	@Override
	public <T> T getProperty(String name, Class<T> requiredType) throws ClassCastException, PropertyNotFoundException {
		Object object = Application.getInstance().getProperty(name);
		if (requiredType != null) {
			if (object.getClass() != requiredType) {
				throw new ClassCastException(object.getClass().getName() + " can't cast to " + requiredType.getName() + ".");
			}
		}
    	return (T) object;
	}

	@Override
	public void removeProperty(String key) throws PropertyNotFoundException {
		Application.getInstance().getProperties().remove(key);
	}

	@Override
	public Map<String, Object> getProperties() {
		return Application.getInstance().getProperties();
	}

	@Override
    public void addLibrary(final Library.Loader libraryLoader) {
        Application.getInstance().addLibrary(libraryLoader);
    }

	@Override
	public void addPropertyFiles(String... propertyFiles) {
		Application.getInstance().addPropertyFiles(propertyFiles);
	}

	@Override
	public void addPropertyPackages(String basePackage) {
		Application.getInstance().addPropertyPackages(basePackage);
	}

	@Override
	public void addPropertyClassses(Class... classes) {
		Application.getInstance().addPropertyClasses(classes);
	}

}
