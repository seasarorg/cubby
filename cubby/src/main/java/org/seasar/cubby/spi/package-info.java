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
/**
 * 置き換え可能な拡張ポイントとなる Service Provider Interface を提供します。
 * <p>
 * {@link Provider} は各種サービスを提供するクラスであることをあらわすマーカインタフェイスで、そのサブインターフェイスが拡張ポイントを表します。
 * それらは {@link ProviderFactory#get(Class)} によってインスタンスを取得します。
 * 拡張ポイントとなるインターフェイスとその実装クラスの関連付けは、クラスパス上の <code>META-INF/cubby</code> ディレクトリによって行われます。
 * 上記ディレクトリにインターフェイスの FQCN をファイル名とし、ファイル中に実装クラスの FQCN を記述することでインターフェイスと実装クラスが関連付けられます。
 * </p>
 * @since 2.0.0
 */
package org.seasar.cubby.spi;

