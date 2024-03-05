package com.gino;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

public class ReflectionUtils {

    public static <T> T createInstanceWithFields(Class<T> clazz, List<String> fieldNames, List<Object> fieldValues) {
        try {
            // Find the appropriate constructor based on field types
            Constructor<T> constructor = findMatchingConstructor(clazz, fieldValues);

            // Create the object instance
            T object = constructor.newInstance(fieldValues.toArray());

            // Set additional fields if constructor didn't cover all
            setAdditionalFields(object, fieldNames, fieldValues);

            return object;

        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to create object using reflection: " + e.getMessage(), e);
        }
    }

    private static <T> Constructor<T> findMatchingConstructor(Class<T> clazz, List<Object> fieldValues) throws NoSuchMethodException {
        // Prioritize constructors with matching parameter types
        for (Constructor<?> constructor : clazz.getConstructors()) {
            if (Arrays.equals(constructor.getParameterTypes(), convertToClasses(fieldValues))) {
                return (Constructor<T>) constructor;
            }
        }

        // If no exact match found, use a constructor with compatible types
        Constructor<T> compatibleConstructor = null;
        int maxMatchingParameters = 0;
        for (Constructor<?> constructor : clazz.getConstructors()) {
            int matchingParameters = countMatchingParameters(constructor, fieldValues);
            if (matchingParameters > maxMatchingParameters) {
                compatibleConstructor = (Constructor<T>) constructor;
                maxMatchingParameters = matchingParameters;
            }
        }

        if (compatibleConstructor == null) {
            throw new NoSuchMethodException("No matching constructor found for provided field values");
        }

        return compatibleConstructor;
    }

    private static Class<?>[] convertToClasses(List<Object> values) {
        return values.stream().map(Object::getClass).toArray(Class<?>[]::new);
    }

    private static int countMatchingParameters(Constructor<?> constructor, List<Object> values) {
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        int count = 0;
        for (int i = 0; i < parameterTypes.length && i < values.size(); i++) {
            if (parameterTypes[i].isAssignableFrom(values.get(i).getClass())) {
                count++;
            }
        }
        return count;
    }

    private static void setAdditionalFields(Object object, List<String> fieldNames, List<Object> fieldValues) throws IllegalAccessException {
        int fieldIndex = 0;
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (fieldNames.contains(field.getName()) && fieldIndex < fieldValues.size()) {
                field.set(object, fieldValues.get(fieldIndex));
                fieldIndex++;
            }
        }
    }
}

