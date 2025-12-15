package Lab2.MethodInvoker;

import Lab2.Repeat.Repeat;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;

public class MethodInvoker {

    public void invokeAnnotatedMethods(Object target) {
        System.out.println("=== Finding annotated methods in class " +
                target.getClass().getSimpleName() + " ===");

        Arrays.stream(target.getClass().getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(Repeat.class))
                .filter(this::isProtectedOrPrivate)
                .forEach(m -> invokeMethod(target, m, m.getAnnotation(Repeat.class).value()));
    }

    private boolean isProtectedOrPrivate(Method m) {
        int mod = m.getModifiers();
        return java.lang.reflect.Modifier.isProtected(mod) || java.lang.reflect.Modifier.isPrivate(mod);
    }

    private void invokeMethod(Object target, Method method, int times) {
        System.out.printf("\n--- Method: %s ---\nModifier: %s\nCalls: %d\nParams: %s\n",
                method.getName(), getModifier(method), times,
                Arrays.toString(method.getParameterTypes()));

        method.setAccessible(true);

        Object[] params = Arrays.stream(method.getParameterTypes())
                .map(this::createInstanceRecursive)
                .toArray();

        if (method.isVarArgs() && params.length > 0) {
            Class<?> varargType = method.getParameterTypes()[method.getParameterCount() - 1].getComponentType();
            Object varargsArray = java.lang.reflect.Array.newInstance(varargType, 0);
            params[params.length - 1] = varargsArray;
        }

        for (int i = 1; i <= times; i++) {
            try {
                System.out.printf("Call %d: ", i);
                Object result = method.invoke(target, params);
                if (method.getReturnType() != void.class) {
                    System.out.println("Returns: " + result);
                }
            } catch (Exception e) {
                System.out.println("Error: " + getExceptionMessage(e));
            }
        }
    }

    /**
     * Recursive instance for any type
     */
    public Object createInstanceRecursive(Class<?> type) {
        if (type.isPrimitive()) {
            return createPrimitiveDefault(type);
        }

        if (type.isArray()) {
            return createArrayInstance(type);
        }

        return createReferenceTypeInstance(type);
    }

    /**
     * Primitives
     */
    private Object createPrimitiveDefault(Class<?> primitiveType) {
        if (primitiveType == int.class) return 0;
        if (primitiveType == boolean.class) return false;
        if (primitiveType == double.class) return 0.0;
        if (primitiveType == float.class) return 0.0f;
        if (primitiveType == long.class) return 0L;
        if (primitiveType == char.class) return '\0';
        if (primitiveType == byte.class) return (byte) 0;
        if (primitiveType == short.class) return (short) 0;
        if (primitiveType == void.class) return null;
        return null;
    }

    /**
     * Creates arr with len=0
     */
    private Object createArrayInstance(Class<?> arrayType) {
        Class<?> componentType = arrayType.getComponentType();
        return java.lang.reflect.Array.newInstance(componentType, 0);
    }

    /**
     * For ref trying with constructor
     */
    private Object createReferenceTypeInstance(Class<?> type) {
        if (type == String.class) {
            return "default";
        }

        if (type == Integer.class) return 0;
        if (type == Boolean.class) return false;
        if (type == Double.class) return 0.0;
        if (type == Float.class) return 0.0f;
        if (type == Long.class) return 0L;
        if (type == Character.class) return '\0';
        if (type == Byte.class) return (byte) 0;
        if (type == Short.class) return (short) 0;

        try {
            Constructor<?> defaultConstructor = type.getDeclaredConstructor();
            defaultConstructor.setAccessible(true);
            return defaultConstructor.newInstance();
        } catch (NoSuchMethodException e) {
            return createWithParameterizedConstructor(type);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Create with params
     */
    private Object createWithParameterizedConstructor(Class<?> type) {
        Constructor<?>[] constructors = type.getDeclaredConstructors();

        Arrays.sort(constructors, Comparator.comparingInt(Constructor::getParameterCount));

        for (Constructor<?> constructor : constructors) {
            try {
                constructor.setAccessible(true);
                Class<?>[] paramTypes = constructor.getParameterTypes();
                Object[] parameters = Arrays.stream(paramTypes)
                        .map(this::createInstanceRecursive)
                        .toArray();

                return constructor.newInstance(parameters);
            } catch (Exception e) {
                continue;
            }
        }

        return null;
    }

    private String getModifier(Method m) {
        return switch (m.getModifiers() & java.lang.reflect.Modifier.methodModifiers()) {
            case java.lang.reflect.Modifier.PRIVATE -> "private";
            case java.lang.reflect.Modifier.PROTECTED -> "protected";
            case java.lang.reflect.Modifier.PUBLIC -> "public";
            default -> "package-private";
        };
    }

    private String getExceptionMessage(Exception e) {
        if (e instanceof java.lang.reflect.InvocationTargetException ite) {
            return ite.getTargetException().getMessage();
        }
        return e.getMessage();
    }
}