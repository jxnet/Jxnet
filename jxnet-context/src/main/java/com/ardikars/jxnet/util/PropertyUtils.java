package com.ardikars.jxnet.util;


import com.ardikars.jxnet.annotation.Component;
import com.ardikars.jxnet.annotation.Configuration;
import com.ardikars.jxnet.annotation.Inject;
import com.ardikars.jxnet.annotation.Order;
import com.ardikars.jxnet.annotation.Property;
import com.ardikars.jxnet.exception.DuplicatePropertyException;
import com.ardikars.jxnet.exception.DuplicatePropertyOrderException;
import com.ardikars.jxnet.exception.PropertyCreationException;
import com.ardikars.jxnet.exception.PropertyOrderException;
import com.ardikars.jxnet.exception.PropertyException;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
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
			e.printStackTrace();
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
					e.printStackTrace();
				}
			}
		}
		return classes;
	}

	private static <T> void processAnnotation(Map<Integer, T> orderedData, Set<T> sets, Annotation[] annotations, T data) {
		Order order = null;
		for (Annotation annotation : annotations) {
			if (annotation instanceof Order) {
				order = (Order) annotation;
				int orderValue = order.value();
				if (orderValue <= 0) {
					throw new PropertyOrderException("Order value should be greater then 0.");
				}
				if (orderedData.containsKey(orderValue)) {
					throw new DuplicatePropertyOrderException("Property with order number " + order.value() + " alredy exist.");
				}
			}
		}
		for (Annotation annotation : annotations) {
			if (annotation instanceof Configuration) {
				Configuration configuration = (Configuration) annotation;
				String key = configuration.value().trim();
				if (key == null || key.equals("")) {
					throw new PropertyException("Property name should be not null or empty string.");
				}
				if (order != null) {
					orderedData.put(order.value(), data);
				} else {
					sets.add(data);
				}
			} else if (annotation instanceof Property) {
				Property property = (Property) annotation;
				String key = property.value().trim();
				if (key == null || key.equals("")) {
					throw new PropertyException("Property name should be not null or empty string.");
				}
				if (order != null) {
					orderedData.put(order.value(), data);
				} else {
					sets.add(data);
				}
			} else if (annotation instanceof Component) {
				Component component = (Component) annotation;
				String key = component.value().trim();
				if (key == null || key.equals("")) {
					throw new PropertyException("Property name should be not null or empty string.");
				}
				if (order != null) {
					orderedData.put(order.value(), data);
				} else {
					sets.add(data);
				}
			}
		}
	}


	public static Set<Method> processMethodOrder(Class aClass) {

		Set<Method> configurationMethods = Collections.synchronizedSet(new HashSet<Method>());
		Map<Integer, Method> configurationMethodsAscendingOrder = Collections.synchronizedMap(new TreeMap<Integer, Method>());

		for (Method method : aClass.getMethods()) {
			processAnnotation(configurationMethodsAscendingOrder, configurationMethods, method.getAnnotations(), method);
		}
		Set<Method> methods = Collections.synchronizedSet(new LinkedHashSet<Method>());
		methods.addAll(configurationMethodsAscendingOrder.values());
		methods.addAll(configurationMethods);
		return methods;
	}

	public static Set<Field> processFieldOrder(Class aClass) {

		Set<Field> configurationFields = Collections.synchronizedSet(new HashSet<Field>());
		Map<Integer, Field> configurationFieldsAscendingOrder = Collections.synchronizedMap(new TreeMap<Integer, Field>());

		for (Field field : aClass.getFields()) {
			processAnnotation(configurationFieldsAscendingOrder, configurationFields, field.getAnnotations(), field);
		}
		Set<Field> fields = Collections.synchronizedSet(new LinkedHashSet<Field>());
		fields.addAll(configurationFieldsAscendingOrder.values());
		fields.addAll(configurationFields);
		return fields;
	}

	public static Set<Class> processClassOrder(Set<Class> classes) throws PropertyException {
		
		Set<Class> configuratioClass = Collections.synchronizedSet(new HashSet<Class>());
		Map<Integer, Class> configurationClassAscendingOrder = Collections.synchronizedMap(new TreeMap<Integer, Class>());

		for (Class aClass : classes) {
			processAnnotation(configurationClassAscendingOrder, configuratioClass, aClass.getAnnotations(), aClass);
		}
		classes = Collections.synchronizedSet(new LinkedHashSet<Class>());
		classes.addAll(configurationClassAscendingOrder.values());
		classes.addAll(configuratioClass);
		return classes;
	}

	public static void createClassProperties(Map<String, Object> registry, Set<Class> classes) throws IllegalAccessException {
		for (Class clazz : classes) {
			Annotation[] typeAnnotations = clazz.getAnnotations();
			for (Annotation annotation : typeAnnotations) {
				if (annotation instanceof Configuration) {
					Configuration configuration = (Configuration) annotation;
					String key = configuration.value().trim();
					if (key == null || key.equals("")) {
						throw new PropertyCreationException("Property name in " + clazz.getName() + " should be not null or empty string.");
					}
					if (registry.containsKey(key)) {
						throw new DuplicatePropertyException("Property in " + clazz.getName() + " with name " + key + " already exist.");
					}
					Object object;
					try {
						object = clazz.newInstance();
					} catch (InstantiationException e) {
						throw new PropertyCreationException("Error creating property with name " + key + " in class " + clazz.getName() + ".");
					} catch (IllegalAccessException e) {
						throw new PropertyCreationException("Error creating property with name " + key + " in class " + clazz.getName() + ".");
					}
					registry.put(key, object);
				} else if (annotation instanceof Component) {
					Component component = (Component) annotation;
					String key = component.value().trim();
					if (key == null || key.equals("")) {
						throw new PropertyCreationException("Property name in " + clazz.getName() + " should be not null or empty string.");
					}
					if (registry.containsKey(key)) {
						throw new DuplicatePropertyException("Property in " + clazz.getName() + " with name " + key + " already exist.");
					}
					Object object;
					try {
						object = clazz.newInstance();
					} catch (InstantiationException e) {
						throw new PropertyCreationException("Error creating property with name " + key + " in class " + clazz.getName() + ".");
					} catch (IllegalAccessException e) {
						throw new PropertyCreationException("Error creating property with name " + key + " in class " + clazz.getName() + ".");
					}
					registry.put(key, object);
				}
			}
		}
	}

	public static void createMethodProperties(Map<String, Object> registry, Set<Method> methods) throws IllegalAccessException {
		for (Method method : methods) {
			Annotation[] methodAnnotations = method.getAnnotations();
			for (Annotation annotation : methodAnnotations) {
				if (annotation instanceof Property) {
					Property property = (Property) annotation;
					String key = property.value();
					boolean replaced = property.replaced();
					if (key == null || key.equals("")) {
						throw new PropertyCreationException("Property name in " + method.getAnnotations() + ":" + method.getName() + "() should be not null or empty string.");
					}
					if (registry.containsKey(key)) {
						if (!replaced) {
							throw new DuplicatePropertyException("Property in " + method.getAnnotations() + ":" + method.getName() + "() with name " + key + " already exist.");
						}
					}
					if (method.getParameterTypes().length > 0) {
						throw new PropertyCreationException("Method should be not have any parameters.");
					}
					Object object;
					try {
						object = method.getDeclaringClass().newInstance();
					} catch (InstantiationException e) {
						throw new PropertyCreationException("Error creating property with name " + key + " in method " + method.getDeclaringClass() + ":" +method.getName() + "()");
					} catch (IllegalAccessException e) {
						throw new PropertyCreationException("Error creating property with name " + key + " in method " + method.getDeclaringClass() + ":" +method.getName() + "()");
					}
					Object[] parameters = new Object[0];
					try {
						method.setAccessible(true);
						object = method.invoke(object, parameters);
					} catch (IllegalAccessException e) {
						throw new PropertyCreationException("Error creating property with name " + key + " in method " + method.getDeclaringClass() + ":" +method.getName() + "()");
					} catch (InvocationTargetException e) {
						throw new PropertyCreationException("Error creating property with name " + key + " in method " + method.getDeclaringClass() + ":" +method.getName() + "()");
					}
					if (object == null) {
						throw new PropertyCreationException("Method return value should be not null.");
					}
					registry.put(key, object);
				}
			}
		}
	}

	public static void createFieldProperties(Map<String, Object> registry, Set<Field> fields) throws IllegalAccessException {
		for (Field field : fields) {
			Annotation[] fieldAnnotations = field.getDeclaredAnnotations();
			for (Annotation annotation : fieldAnnotations) {
				if (annotation instanceof Property) {
					Property property = (Property) annotation;
					String key = property.value();
					boolean replaced = property.replaced();
					if (key == null || key.equals("")) {
						throw new PropertyCreationException("Property name in " + field.getDeclaringClass() + ":" + field.getName() + " should be not null or empty string.");
					}
					if (registry.containsKey(key)) {
						if (!replaced) {
							throw new DuplicatePropertyException("Property with name " + key + " in field " + field.getDeclaringClass() + "." + field.getName() + " already exist.");
						}
					}
					Object object;
					try {
						object = field.getDeclaringClass().newInstance();
					} catch (InstantiationException e) {
						throw new PropertyCreationException("Error creating property with name " + key + " in field " + field.getDeclaringClass() + ":" + field.getName() + "()");
					} catch (IllegalAccessException e) {
						throw new PropertyCreationException("Error creating property with name " + key + " in field " + field.getDeclaringClass() + ":" + field.getName() + "()");
					}
					try {
						field.setAccessible(true);
						object = field.get(object);
					} catch (IllegalAccessException e) {
						throw new PropertyCreationException("Error creating property with name " + key + " in field " + field.getDeclaringClass() + ":" + field.getName() + "()");
					}
					if (object == null) {
						throw new PropertyException("Field value should be not null");
					}
					registry.put(key, object);
				}
			}
		}
	}

	public static void injectProperties(Map<String, Object> registry) throws PropertyException, IllegalAccessException {
		for (Map.Entry<String, Object> entry : registry.entrySet()) {
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
						if (!registry.containsKey(injectKey)) {
							if (isRequired) {
								throw new PropertyException("Property in " + field.getDeclaringClass() + "." + field.getName() + " with name " + injectKey + " not found.");
							}
						}
						Object injectValue = registry.get(injectKey);
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


}
