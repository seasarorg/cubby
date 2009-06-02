/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.cubby.action;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.seasar.cubby.converter.Converter;

/**
 * リクエストパラメータのバインド対象であることを表す注釈です。
 * <p>
 * 以下のいずれかの場合に、この注釈で修飾された属性(プロパティまたはフィールド)がリクエストパラメータをバインドする対象になります。
 * <ul>
 * <li>アクションメソッド(またはクラス)が {@link Form} で修飾されていない</li>
 * <li>アクションメソッド(またはクラス)が {@link Form} で修飾されていて、{@link Form#bindingType()} に
 * {@link RequestParameterBindingType#ONLY_SPECIFIED_PROPERTIES} が指定されている</li>
 * </ul>
 * </p>
 * 
 * @see Form
 * @see RequestParameterBindingType
 * @see RequestParameterBindingType#ONLY_SPECIFIED_PROPERTIES
 * @author baba
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.METHOD, ElementType.FIELD })
public @interface RequestParameter {

	/** バインドする要求パラメータ名、指定しない場合は属性名が使用されます。 */
	String name() default "";

	/** 型変換に用いるコンバータ、指定しない場合はプロパティの型によって自動的に選択されます。 */
	Class<? extends Converter> converter() default Converter.class;

}
