package org.seasar.cubby.controller.impl;

import java.text.DateFormat;

import org.seasar.cubby.action.FormatPattern;
import org.seasar.cubby.controller.CubbyConfiguration;
import org.seasar.extension.dxo.converter.ConversionContext;
import org.seasar.extension.dxo.converter.Converter;
import org.seasar.extension.dxo.converter.ConverterFactory;
import org.seasar.extension.dxo.converter.DatePropertyInfo;
import org.seasar.extension.dxo.converter.NestedPropertyInfo;

public class ConversionContextImpl implements ConversionContext {

	private final ConverterFactory converterFactory;

	private final FormatPattern formatPattern;

	public ConversionContextImpl(ConverterFactory converterFactory,
			CubbyConfiguration cubbyConfiguration) {
		this.converterFactory = converterFactory;
		this.formatPattern = cubbyConfiguration.getFormatPattern();
	}

	public void addConvertedObject(Object source, Object dest) {
		throw new UnsupportedOperationException();
	}

	public void addEvaluatedValue(String name, Object value) {
		throw new UnsupportedOperationException();
	}

	public Object getContextInfo(String key) {
		throw new UnsupportedOperationException();
	}

	public Object getConvertedObject(Object source) {
		throw new UnsupportedOperationException();
	}

	public Converter getConverter(Class destClass, String destPropertyName) {
		throw new UnsupportedOperationException();
	}

	public ConverterFactory getConverterFactory() {
		return converterFactory;
	}

	public DateFormat getDateFormat() {
		return formatPattern.getDateFormat();
	}

	public DateFormat getDateFormat(String format) {
		throw new UnsupportedOperationException();
	}

	public DatePropertyInfo getDatePropertyInfo(Class srcClass,
			String propertyName) {
		throw new UnsupportedOperationException();
	}

	public Object getEvaluatedValue(String name) {
		throw new UnsupportedOperationException();
	}

	public NestedPropertyInfo getNestedPropertyInfo(Class srcClass,
			String propertyName) {
		throw new UnsupportedOperationException();
	}

	public String getSourcePrefix() {
		throw new UnsupportedOperationException();
	}

	public String getSourcePropertyName(String destPropertyName) {
		throw new UnsupportedOperationException();
	}

	public DateFormat getTimeFormat() {
		return formatPattern.getTimeFormat();
	}

	public DateFormat getTimestampFormat() {
		return formatPattern.getTimestampFormat();
	}

	public boolean hasEvalueatedValue(String name) {
		throw new UnsupportedOperationException();
	}

	public boolean isExcludeNull() {
		throw new UnsupportedOperationException();
	}

	public boolean isExcludeWhitespace() {
		throw new UnsupportedOperationException();
	}

	public boolean isIncludeNull() {
		throw new UnsupportedOperationException();
	}

	public boolean isIncludeWhitespace() {
		throw new UnsupportedOperationException();
	}

}
