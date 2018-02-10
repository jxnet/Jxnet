package com.ardikars.jxnet.util;


import com.ardikars.jxnet.annotation.Component;
import com.ardikars.jxnet.annotation.Configuration;
import com.ardikars.jxnet.annotation.Order;
import com.ardikars.jxnet.annotation.Property;
import com.ardikars.jxnet.exception.DuplicateOrderException;
import com.ardikars.jxnet.exception.OrderException;
import com.ardikars.jxnet.exception.PropertyException;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Logger;

public class PropertyUtils {

	private static final Logger LOGGER = Logger.getLogger(PropertyUtils.class.getName());

	public static Set<Class> getClasses(String packageName) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		String path = packageName.replace('.', '/');
		Enumeration resources = null;
		try {
			resources = classLoader.getResources(path);
		} catch (IOException e) {
			LOGGER.warning(e.getMessage());
		}
		Set<File> dirs = new LinkedHashSet<>();
		while (resources.hasMoreElements()) {
			URL resource = (URL) resources.nextElement();
			dirs.add(new File(resource.getFile()));
		}
		Set<Class> classes = new LinkedHashSet<>();
		for (File directory : dirs) {
			classes.addAll(findClasses(directory, packageName));
		}
		return classes;
	}

	private static Set<Class> findClasses(File directory, String packageName) {
		Set<Class> classes = new LinkedHashSet<>();
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				classes.addAll(findClasses(file, packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".class")) {
				try {
					classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
				} catch (ClassNotFoundException e) {
					LOGGER.warning(e.getMessage());
				}
			}
		}
		return classes;
	}

	private static Order validateOrder(Annotation annotation) {
		Order order = order = (Order) annotation;
		if (order.value() <= 0) {
			throw new OrderException("Order value should be greater then 0.");
		}
		return order;
	}

	public static Set<Class> processConfigurationOrder(Set<Class> classes) throws PropertyException {
		
		Set<Class> classSet = Collections.synchronizedSet(new HashSet<Class>());
		Map<Integer, Class> classAscendingOrder = Collections.synchronizedMap(new TreeMap<Integer, Class>());
		
		Iterator<Class> classIterator = classes.iterator();
		while (classIterator.hasNext()) {
			Class clazz = classIterator.next();
			Annotation[] annotations = clazz.getDeclaredAnnotations();
			Order order = null;
			for (Annotation annotation : annotations) {
				if (annotation instanceof Order) {
					try {
						order = validateOrder(annotation);
						if (classAscendingOrder.containsKey(order.value())) {
							throw new DuplicateOrderException("Property with order number " + order.value() + " alredy exist.");
						}
					} catch (OrderException e) {
						throw e;
					}
				}
				if (annotation instanceof Configuration) {
					Configuration configuration = (Configuration) annotation;
					String key = configuration.value().trim();
					if (key == null || key.equals("")) {
						throw new PropertyException("Property name should be not null or empty string.");
					}
					if (order != null) {
						classAscendingOrder.put(order.value(), clazz);
					} else {
						classSet.add(clazz);
					}
				}
			}
		}
		Set<Class> result = Collections.synchronizedSet(Collections.synchronizedSet(new LinkedHashSet<Class>()));
		result.addAll(classAscendingOrder.values());
		result.addAll(classSet);
		return result;
	}

	public static Set<Method> processMethodOrder(Set<Method> methods) {

		Set<Method> methodSet = Collections.synchronizedSet(new HashSet<Method>());
		Map<Integer, Method> methodAscendingOrder = Collections.synchronizedMap(new TreeMap<Integer, Method>());

		Iterator<Method> methodIterator = methods.iterator();
		while (methodIterator.hasNext()) {
			Method method = methodIterator.next();
			Annotation[] annotations = method.getDeclaredAnnotations();
			Order order = null;
			for (Annotation annotation : annotations) {
				if (annotation instanceof Order) {
					try {
						order = validateOrder(annotation);
						if (methodAscendingOrder.containsKey(order.value())) {
							throw new DuplicateOrderException("Property with order number " + order.value() + " alredy exist.");
						}
					} catch (OrderException e) {
						throw e;
					}
				}
				if (annotation instanceof Property) {
					Property property = (Property) annotation;
					String key = property.value().trim();
					if (key == null || key.equals("")) {
						throw new PropertyException("Property name should be not null or empty string.");
					}
					if (order != null) {
						methodAscendingOrder.put(order.value(), method);
					} else {
						methodSet.add(method);
					}
				}
			}
		}

		Set<Method> result = Collections.synchronizedSet(new LinkedHashSet<Method>());
		result.addAll(methodAscendingOrder.values());
		result.addAll(methodSet);
		return result;
	}

	public static Set<Field> processFieldOrder(Set<Field> fields) {

		Set<Field> fieldSet = Collections.synchronizedSet(new HashSet<Field>());
		Map<Integer, Field> fieldAscendingOrder = Collections.synchronizedMap(new TreeMap<Integer, Field>());

		Iterator<Field> fieldIterator = fields.iterator();
		while (fieldIterator.hasNext()) {
			Field field = fieldIterator.next();
			Annotation[] annotations = field.getDeclaredAnnotations();
			Order order = null;
			for (Annotation annotation : annotations) {
				if (annotation instanceof Order) {
					try {
						order = validateOrder(annotation);
						if (fieldAscendingOrder.containsKey(order.value())) {
							throw new DuplicateOrderException("Property with order number " + order.value() + " alredy exist.");
						}
					} catch (OrderException e) {
						throw e;
					}
				}
				if (annotation instanceof Property) {
					Property property = (Property) annotation;
					String key = property.value().trim();
					if (key == null || key.equals("")) {
						throw new PropertyException("Property name should be not null or empty string.");
					}
					if (order != null) {
						fieldAscendingOrder.put(order.value(), field);
					} else {
						fieldSet.add(field);
					}
				}
			}
		}
		Set<Field> result = Collections.synchronizedSet(new LinkedHashSet<Field>());
		result.addAll(fieldAscendingOrder.values());
		result.addAll(fieldSet);
		return result;
	}

	public static Set<Class> processComponentOrder(Set<Class> classes) throws PropertyException {

		Set<Class> classSet = Collections.synchronizedSet(new HashSet<Class>());
		Map<Integer, Class> classAscendingOrder = Collections.synchronizedMap(new TreeMap<Integer, Class>());

		Iterator<Class> classIterator = classes.iterator();
		while (classIterator.hasNext()) {
			Class clazz = classIterator.next();
			Annotation[] annotations = clazz.getDeclaredAnnotations();
			Order order = null;
			for (Annotation annotation : annotations) {
				if (annotation instanceof Order) {
					try {
						order = validateOrder(annotation);
						if (classAscendingOrder.containsKey(order.value())) {
							throw new DuplicateOrderException("Property with order number " + order.value() + " alredy exist.");
						}
					} catch (OrderException e) {
						throw e;
					}
				}
				if (annotation instanceof Component) {
					Component component = (Component) annotation;
					String key = component.value().trim();
					if (key == null || key.equals("")) {
						throw new PropertyException("Property name should be not null or empty string.");
					}
					if (order != null) {
						classAscendingOrder.put(order.value(), clazz);
					} else {
						classSet.add(clazz);
					}
				}
			}
		}
		Set<Class> result = Collections.synchronizedSet(Collections.synchronizedSet(new LinkedHashSet<Class>()));
		result.addAll(classAscendingOrder.values());
		result.addAll(classSet);
		return result;
	}

}
