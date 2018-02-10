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

import com.ardikars.jxnet.annotation.Component;
import com.ardikars.jxnet.annotation.Configuration;
import com.ardikars.jxnet.annotation.Inject;
import com.ardikars.jxnet.annotation.Property;
import com.ardikars.jxnet.exception.DuplicatePropertyException;
import com.ardikars.jxnet.exception.PropertyNotFoundException;
import com.ardikars.jxnet.util.Platforms;
import com.ardikars.jxnet.util.PropertyUtils;

import javax.xml.bind.PropertyException;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.logging.Logger;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.5
 */
public class Application {

    private static final Logger LOGGER = Logger.getLogger(Application.class.getName());

    private boolean loaded;
    private boolean developmentMode;

    private static final Application instance = new Application();

    private String applicationName;
    private String applicationVersion;
    private Context context;

    private final List<Library.Loader> libraryLoaders = new ArrayList<>();
    private final Map<String, Object> properties = Collections.synchronizedMap(new WeakHashMap<String, Object>());

    protected boolean isLoaded() {
        return this.loaded;
    }

    public void enableDevelopmentMode() {
        this.developmentMode = true;
    }

    private Application() {

    }

    protected static Application getInstance() {
        synchronized (Application.class) {
            return instance;
        }
    }

    protected void addLibrary(final Library.Loader libraryLoader) {
        this.libraryLoaders.add(libraryLoader);
    }

    protected Object getProperty(final String key) throws PropertyNotFoundException{
        if (this.properties.get(key) == null) {
            throw new PropertyNotFoundException("Property with name " + key + " not found.");
        }
        return this.properties.get(key);
    }

    /**
     * Used for bootstraping Jxnet.
     * @param applicationName application name.
     * @param applicationVersion application version.
     * @param initializer initializer.
     * @throws UnsatisfiedLinkError UnsatisfiedLinkError.
     */
    public static void run(final String applicationName, final String applicationVersion,
                           final ApplicationInitializer initializer) {

        getInstance().applicationName = applicationName;
        getInstance().applicationVersion = applicationVersion;
        getInstance().context = new ApplicationContext();

        initializer.initialize(getInstance().getContext());

        if (Platforms.isWindows()) {
            String paths = System.getProperty("java.library.path");
            final String path = "C:\\Windows\\System32\\Npcap";
            final String pathSparator = File.pathSeparator;
            final String[] libraryPaths = paths.split(pathSparator);
            boolean isAdded = false;
            for (final String str : libraryPaths) {
                if (str.equals(path)) {
                    isAdded = true;
                    break;
                }
            }
            if (!isAdded) {
                paths = paths.concat(pathSparator + path);
                System.setProperty("java.library.path", paths);
                Field sysPathsField;
                try {
                    sysPathsField = ClassLoader.class.getDeclaredField("sys_paths");
                } catch (NoSuchFieldException e) {
                    LOGGER.warning(e.getMessage());
                    throw new UnsatisfiedLinkError(e.getMessage());
                }
                sysPathsField.setAccessible(true);
                try {
                    sysPathsField.set(null, null);
                } catch (IllegalAccessException e) {
                    LOGGER.warning(e.getMessage());
                    throw new UnsatisfiedLinkError(e.getMessage());
                }
            }
        }

        if (getInstance().developmentMode && !getInstance().loaded) {
            try {
                System.loadLibrary("jxnet");
                getInstance().loaded = true;
            } catch (Exception e) {
                getInstance().loaded = false;
            }
        } else {
            if (!getInstance().loaded && getInstance().libraryLoaders != null && !getInstance().libraryLoaders.isEmpty()) {
                for (final Library.Loader loader : getInstance().libraryLoaders) {
                    try {
                        loader.load();
                        getInstance().loaded = true;
                        break;
                    } catch (UnsatisfiedLinkError e) {
                        continue;
                    }
                }
            }
        }
    }

    protected static void configure(String basePackage) throws PropertyException, InstantiationException, IllegalAccessException {

    	Set<Class> classSet = PropertyUtils.getClasses(basePackage);
        Set<Class> classes = PropertyUtils.processConfigurationOrder(classSet);
        Set<Class> components = PropertyUtils.processComponentOrder(classSet);
        Set<Method> methods = Collections.synchronizedSet(new LinkedHashSet<Method>());
        Set<Field> fields = Collections.synchronizedSet(new LinkedHashSet<Field>());

        for (Class clazz : classes) {
            if (!clazz.isInterface() && !clazz.isAnnotation() && !clazz.isAnonymousClass() && !clazz.isEnum()) {
                for (Method method : clazz.getMethods()) {
                    methods.add(method);
                }
                for (Field field : clazz.getFields()) {
                    fields.add(field);
                }
            }
        }
        methods = PropertyUtils.processMethodOrder(methods);
        fields = PropertyUtils.processFieldOrder(fields);
        for (Field field : fields) {
            System.out.println(field);
        }
        for (Class clazz : classes) {
            Annotation[] typeAnnotations = clazz.getAnnotations();
            for (Annotation annotation : typeAnnotations) {
                if (annotation instanceof Configuration) {
                    Configuration configuration = (Configuration) annotation;
                    String key = configuration.value().trim();
                    if (key == null || key.equals("")) {
                        throw new PropertyException("Property name in " + clazz.getName() + " should be not null or empty string.");
                    }
                    if (getInstance().properties.containsKey(key)) {
                        throw new DuplicatePropertyException("Property in " + clazz.getName() + " with name " + key + " already exist.");
                    }
                    Object object;
                    try {
                        object = clazz.newInstance();
                    } catch (InstantiationException e) {
                        throw new InstantiationException("Is " + clazz.getName() + " abstract class? " + e.getMessage());
                    } catch (IllegalAccessException e) {
                        throw new IllegalAccessException("Is the constructor in " + clazz.getName() + " accessible? " + e.getMessage());
                    }
                    getInstance().properties.put(key, object);
                }
            }
        }

        for (Method method : methods) {
            Annotation[] methodAnnotations = method.getDeclaredAnnotations();
            for (Annotation annotation : methodAnnotations) {
                if (annotation instanceof Property) {
                    Property property = (Property) annotation;
                    String key = property.value();
                    boolean replaced = property.replaced();
                    if (key == null || key.equals("")) {
                        throw new PropertyException("Property name in " + method.getDeclaringClass() + ":" + method.getName() + "() should be not null or empty string.");
                    }
                    if (getInstance().properties.containsKey(key)) {
                        if (!replaced) {
                            throw new DuplicatePropertyException("Property in " + method.getDeclaringClass() + ":" + method.getName() + "() with name " + key + " already exist.");
                        }
                    }
                    if (method.getParameterTypes().length > 0) {
                        throw new PropertyException("Method should be not have any parameters.");
                    }
                    Object object;
                    try {
                        object = method.getDeclaringClass().newInstance();
                    } catch (InstantiationException e) {
                        throw new InstantiationException("Is " + method.getDeclaringClass() + ":" + method.getName() + "() abstract class? " + e.getMessage());
                    } catch (IllegalAccessException e) {
                        throw new IllegalAccessException("Is the constructor in " + method.getDeclaringClass() + " accessible? " + e.getMessage());
                    }
                    Object[] parameters = new Object[0];
                    try {
                        method.setAccessible(true);
                        object = method.invoke(object, parameters);
                    } catch (IllegalAccessException e) {
                        throw new InstantiationException("Is " + method.getDeclaringClass() + ":" + method.getName() + "() abstract class? " + e.getMessage());
                    } catch (InvocationTargetException e) {
                        throw new IllegalAccessException("Is the method " + method.getDeclaringClass() + ":" +method.getName() + "() accessible or throwing exception? " + e.getMessage());
                    }
                    if (object == null) {
                        throw new PropertyException("Method return value should be not null.");
                    }
                    getInstance().properties.put(key, object);
                }
            }
        }

        for (Field field : fields) {
            Annotation[] fieldAnnotations = field.getDeclaredAnnotations();
            for (Annotation annotation : fieldAnnotations) {
                if (annotation instanceof Property) {
                    Property property = (Property) annotation;
                    String key = property.value();
                    boolean replaced = property.replaced();
                    if (key == null || key.equals("")) {
                        throw new PropertyException("Property name should be not null or empty string.");
                    }
                    if (getInstance().properties.containsKey(key)) {
                        if (!replaced) {
                            throw new DuplicatePropertyException("Property with name " + key + " already exist.");
                        }
                    }
                    Object object;
                    try {
                        object = field.getDeclaringClass().newInstance();
                    } catch (InstantiationException e) {
                        throw new InstantiationException("Is it an abstract class? " + e.getMessage());
                    } catch (IllegalAccessException e) {
                        throw new IllegalAccessException("Is the field accessible? " + e.getMessage());
                    }
                    try {
                        field.setAccessible(true);
                        object = field.get(object);
                    } catch (IllegalAccessException e) {
                        throw new IllegalAccessException("Is the field accessible? " + e.getMessage());
                    }
                    if (object == null) {
                        throw new PropertyException("Field value should be not null");
                    }
                    getInstance().properties.put(key, object);
                }
            }
        }

        injectProperties();

	    for (Class clazz : components) {
		    Annotation[] typeAnnotations = clazz.getAnnotations();
		    for (Annotation annotation : typeAnnotations) {
			    if (annotation instanceof Component) {
				    Component component = (Component) annotation;
				    String key = component.value().trim();
				    if (key == null || key.equals("")) {
					    throw new PropertyException("Property name in " + clazz.getName() + " should be not null or empty string.");
				    }
				    if (getInstance().properties.containsKey(key)) {
					    throw new DuplicatePropertyException("Property in " + clazz.getName() + " with name " + key + " already exist.");
				    }
				    Object object;
				    try {
					    object = clazz.newInstance();
				    } catch (InstantiationException e) {
					    throw new InstantiationException("Is " + clazz.getName() + " abstract class? " + e.getMessage());
				    } catch (IllegalAccessException e) {
					    throw new IllegalAccessException("Is the constructor in " + clazz.getName() + " accessible? " + e.getMessage());
				    }
				    getInstance().properties.put(key, object);
			    }
		    }
	    }
        injectProperties();
    }

    protected static void injectProperties() throws PropertyException, IllegalAccessException {
        for (Map.Entry<String, Object> entry : getInstance().properties.entrySet()) {
            Object value = entry.getValue();
            Class valueClass = value.getClass();
            Field[] fields = valueClass.getFields();
            for (Field field : fields) {
                Annotation[] fieldAnnotations = field.getDeclaredAnnotations();
                for (Annotation annotation : fieldAnnotations) {
                    if (annotation instanceof Inject) {
                        Inject inject = (Inject) annotation;
                        String injectKey = inject.value();
                        boolean isRequired = inject.required();
                        if (injectKey == null || injectKey.equals("")) {
                            throw new PropertyException("Property name in " + field.getDeclaringClass() + "." + field.getName() + " should be not null or empty string.");
                        }
                        if (!getInstance().properties.containsKey(injectKey)) {
                            if (isRequired) {
                                throw new PropertyException("Property in " + field.getDeclaringClass() + "." + field.getName() + " with name " + injectKey + " not found.");
                            }
                        }
                        Object injectValue = getInstance().getProperty(injectKey);
                        try {
                            field.setAccessible(true);
                            field.set(value, injectValue);
                        } catch (IllegalAccessException e) {
                            throw new IllegalAccessException("Is " + field.getDeclaringClass() + "." + field.getName() + " field accessible? " + e.getMessage());
                        }
                    }
                }
            }
        }
    }

    protected String getApplicationName() {
        return this.applicationName;
    }

    protected String getApplicationVersion() {
        return this.applicationVersion;
    }

    protected Context getContext() {
        return this.context;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    /**
     * Get application context.
     * @return application context.
     */
    public static Application.Context getApplicationContext() {
        final Application.Context context = getInstance().getContext();
        if (context == null) {
            throw new NullPointerException("No application context found.");
        }
        return context;
    }

    public interface Context {

        String getApplicationName();

        String getApplicationVersion();

        Object getProperty(String key) throws PropertyNotFoundException;

        <T> T getProperty(String name, Class<T> requiredType) throws ClassCastException, PropertyNotFoundException;

        Map<String, Object> getProperties();

        void addLibrary(Library.Loader libraryLoader);

        void configuration(String basePackage);

    }

}
