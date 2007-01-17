package org.seasar.cubby.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.seasar.cubby.action.Action;

public class ClassUtils {

	public static Object getField(Object obj, String fieldName) {
		try {
			return obj.getClass().getField(fieldName).get(obj);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			throw new RuntimeException();
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static<T> List<Field> getFieldList(Class clazz, Class annotationClass) {
		List<Field> fields = new ArrayList<Field>();
		for (Field f : clazz.getFields()) {
			T annotation = (T) f.getAnnotation(annotationClass);
			if (annotation != null) {
				fields.add(f);
			}
		}
		return fields;
	}

	public static String getProperty(Object obj, String propertyName) {
		try {
			Method method = obj.getClass().getMethod("get" + StringUtils.toFirstUpper(propertyName), new Class[0]);
			Object result = method.invoke(obj, new Object[0]);
			return result != null ? result.toString() : null;
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	public static Class forName(String name) {
		try {
			return Class.forName(name);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static<T> T newInstance(Class<T> clazz) {
		try {
			return clazz.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public static Object newInstance(String contollerClassName) {
		Class<?> clazz = forName(contollerClassName);
		return newInstance(clazz);
	}

	public static Method getMethod(Class<?> clazz, String methodName, Class[] parameterTypes) {
		try {
			return clazz.getMethod(methodName, parameterTypes);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean isSubClass(Class superClass, Class clazz) {
		while ((clazz = clazz.getSuperclass()) != null) {
			if (clazz == superClass) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public static<T> T getMethodAnotation(Method m, Class<T> name) {
		for (Annotation a : m.getAnnotations()) {
			if (a.annotationType() == name) {
				return (T)a;
			}
		}
		return null;
	}
	
	public static Object invoke(Object instance, Method method, Object[] args) {
		try {
			return method.invoke(instance, args);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
	
	public  static Object invoke(Object bean, String methodName, Class[] paramTypes, Object[] args) {
		Method m = getMethod(bean.getClass(), methodName, paramTypes);
		return invoke(bean, m, args);
	}

	public static void setFieldValue(Field f, Object instance, Object value) {
		try {
			f.set(instance, value);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public static Object getFieldValue(Field f, Action instance) {
		try {
			return f.get(instance);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public static Method getSetter(Class clazz, String propertyName) {
		String methodName = "set" + StringUtils.toFirstUpper(propertyName);
		for (Method m : clazz.getMethods()) {
			if(m.getName().equals(methodName)
					&& m.getParameterTypes().length == 1) {
				return m;
			}
		}
		return null;
	}

	public static Field[] getFields(Class<? extends Object> clazz) {
		if (clazz == Object.class) {
			return null;
		} else {
			List<Field> fields = new ArrayList<Field>();
			Field[] fs = clazz.getFields();
			if (fs != null) {
				Collections.addAll(fields, fs);
				fs = getFields(clazz.getSuperclass());
				if (fs != null) {
					Collections.addAll(fields, fs);
				}
			}
			return fields.toArray(new Field[fields.size()]);
		}
	}
}
