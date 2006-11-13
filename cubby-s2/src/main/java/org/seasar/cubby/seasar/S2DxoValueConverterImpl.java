package org.seasar.cubby.seasar;

import java.lang.reflect.Method;
import java.util.Map;

import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.config.Configration;
import org.seasar.cubby.convert.ValueConverter;
import org.seasar.cubby.util.StringUtils;
import org.seasar.extension.dxo.annotation.AnnotationReader;
import org.seasar.extension.dxo.annotation.AnnotationReaderFactory;
import org.seasar.extension.dxo.converter.ConversionContext;
import org.seasar.extension.dxo.converter.Converter;
import org.seasar.extension.dxo.converter.ConverterFactory;
import org.seasar.extension.dxo.converter.impl.ConversionContextImpl;

public class S2DxoValueConverterImpl implements ValueConverter {

	private final ConverterFactory converterFactory;

	private final AnnotationReader reader;

	private final Configration configration;

	public S2DxoValueConverterImpl(ConverterFactory converterFactory,
			AnnotationReaderFactory annotationReaderFactory,
			Configration configration) {
		this.converterFactory = converterFactory;
		this.configration = configration;
		reader = new CubbyAnnotationReader(annotationReaderFactory
				.getAnnotationReader());
	}

	public Object convert(Object source, Object target, Method setter,
			Class destClass) {
		ConversionContext context = createContext(source, target, setter);
		if (destClass.isArray()) {
			Converter converter = getConverter(source, Object[].class);
			return converter.convert(source, destClass, context);
		} else {
			if (source == null) {
				return null;
			} else if (source instanceof String
					&& StringUtils.isEmpty((String) source)) {
				return null;
			}
			Converter converter = getConverter(source, destClass);
			return converter.convert(source, destClass, context);
		}
	}

	private Converter getConverter(Object source, Class destClass) {
		return converterFactory.getConverter(source.getClass(), destClass);
	}

	private ConversionContext createContext(Object source, Object target,
			Method setter) {
		ConversionContext context = new ConversionContextImpl(
				target.getClass(), setter, converterFactory, reader, source);
		return context;
	}

	class CubbyAnnotationReader implements AnnotationReader {
		private AnnotationReader reader;

		public CubbyAnnotationReader(AnnotationReader reader) {
			this.reader = reader;
		}

		public String getConversionRule(Class clazz, Method method) {
			return getGlobalContextInfo(
					reader.getConversionRule(clazz, method),
					CubbyConstants.KEY_GLOBAL_CONVERSION_RULE);
		}

		public String getDatePattern(Class clazz, Method method) {
			return getGlobalContextInfo(
					reader.getDatePattern(clazz, method),
					CubbyConstants.KEY_GLOBAL_DATE_PATTERN);
		}

		public String getTimePattern(Class clazz, Method method) {
			return getGlobalContextInfo(
					reader.getTimePattern(clazz, method),
					CubbyConstants.KEY_GLOBAL_TIME_PATTERN);
		}

		public String getTimestampPattern(Class clazz, Method method) {
			return getGlobalContextInfo(
					reader.getTimestampPattern(clazz, method),
					CubbyConstants.KEY_GLOBAL_TIMESTAMP_PATTERN);
		}

		private String getGlobalContextInfo(String value, String configKey) {
			if (value != null) {
				return value;
			}
			return (String)S2DxoValueConverterImpl.this.configration
					.getValue(configKey);
		}

		public Map getConverters(final Class clazz) {
			return reader.getConverters(clazz);
		}
	}
}
