package com.ardikars.jxnet.util;

import sun.reflect.FieldAccessor;
import sun.reflect.ReflectionFactory;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unchecked")
public class EnumHelpers {

    private static ReflectionFactory REFLECTION_FACTORY = ReflectionFactory.getReflectionFactory();

    public static <T extends Enum<?>> void add(Class<T> enumType, String enumName, Object[] additionalValues) {
        if (!Enum.class.isAssignableFrom(enumType)) {
            throw new RuntimeException("Class " + enumType + " is not an instance of Enum.");
        }
        Class<?>[] additionalType = new Class<?>[additionalValues.length];
        for (int i=0; i<additionalType.length; i++) {
            additionalType[i] = additionalValues[i].getClass();
        }
        Field fieldValues = null;
        Field[] fields = enumType.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().contains("$VALUES")) {
                fieldValues = field;
                break;
            }
        }
        AccessibleObject.setAccessible(new Field[] { fieldValues }, true);
        T[] values = null;
        try {
            values = (T[]) fieldValues.get(enumType);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Object[] parameters = new Object[additionalValues.length + 2];
        parameters[0] = enumName;
        parameters[1] = Integer.valueOf(values.length);
        System.arraycopy(additionalValues, 0, parameters, 2, additionalValues.length);

        Class<?>[] parameterTypes = new Class[parameters.length];
        parameterTypes[0] = String.class;
        parameterTypes[1] = int.class;
        System.arraycopy(additionalType, 0, parameterTypes, 2, additionalType.length);
        T newInstance = null;
        try {
            newInstance = (T) enumType.cast(REFLECTION_FACTORY.newConstructorAccessor(
                    enumType.getDeclaredConstructor(parameterTypes)).newInstance(parameters));
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        fieldValues.setAccessible(true);
        Field fieldModifiers = null;
        try {
            fieldModifiers = Field.class.getDeclaredField("modifiers");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        fieldModifiers.setAccessible(true);
        int modifiers = 0;
        try {
            modifiers = fieldModifiers.getInt(fieldValues);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        modifiers &= ~Modifier.FINAL;
        try {
            fieldModifiers.setInt(fieldValues, modifiers);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        FieldAccessor fa = REFLECTION_FACTORY.newFieldAccessor(fieldValues, false);

        List<T> newValues = new ArrayList<T>(Arrays.asList((T[]) values));
        newValues.add(newInstance);
        values = newValues.toArray((T[]) Array.newInstance(enumType, 0));
        try {
            fa.set(null, values);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static <T extends Enum<?>> void remove(Class<T> enumType, String enumName) {
        if (!Enum.class.isAssignableFrom(enumType)) {
            throw new RuntimeException("Class " + enumType + " is not an instance of Enum.");
        }
        Field fieldValues = null;
        Field[] fields = enumType.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().contains("$VALUES")) {
                fieldValues = field;
                break;
            }
        }
        AccessibleObject.setAccessible(new Field[] { fieldValues }, true);
        T[] values = null;
        try {
            values = (T[]) fieldValues.get(enumType);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        fieldValues.setAccessible(true);
        Field fieldModifiers = null;
        try {
            fieldModifiers = Field.class.getDeclaredField("modifiers");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        fieldModifiers.setAccessible(true);
        int modifiers = 0;
        try {
            modifiers = fieldModifiers.getInt(fieldValues);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        modifiers &= ~Modifier.FINAL;
        try {
            fieldModifiers.setInt(fieldValues, modifiers);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        FieldAccessor fa = REFLECTION_FACTORY.newFieldAccessor(fieldValues, false);
        List<T> newValues = new ArrayList<T>();
        for (T t : values) {
            if (!t.toString().equals(enumName)) {
                newValues.add(t);
            }
        }
        values = newValues.toArray((T[]) Array.newInstance(enumType, 0));
        try {
            fa.set(null, values);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
