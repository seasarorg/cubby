package org.seasar.cubby.action.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.FieldInfo;

/**
 * アクションエラーの実装クラス
 * 
 * @author agata
 * @author baba
 */
public class ActionErrorsImpl implements ActionErrors {

	/**
	 * 全てのエラーの一覧。
	 */
	private final List<String> all = new ArrayList<String>();

	/**
	 * フィールドで発生したエラーの一覧。
	 */
	private final Map<String, List<String>> fields = new NotNullHashMap<String, List<String>>(
			new MessageListEmptyValueFactory());

	/**
	 * インデックス付きフィールドで発生したエラーの一覧。
	 */
	private final Map<String, Map<Integer, List<String>>> indexedFields = new NotNullHashMap<String, Map<Integer, List<String>>>(
			new IndexMapEmptyValueFactory());

	/**
	 * フィールド以外で発生したエラーの一覧。
	 */
	private final List<String> others = new ArrayList<String>();

	/**
	 * アクションで発生した全てのエラーの一覧を取得します。
	 * 
	 * @return アクションで発生した全てエラーの一覧
	 */
	public List<String> getAll() {
		return all;
	}

	/**
	 * フィールドで発生したエラーの一覧を取得します。
	 * 
	 * @return フィールドで発生したエラーの一覧
	 */
	public Map<String, List<String>> getFields() {
		return fields;
	}

	/**
	 * インデックス付きフィールドで発生したエラーの一覧を取得します。
	 * 
	 * @return インデックス付きフィールドで発生したエラーの一覧
	 */
	public Map<String, Map<Integer, List<String>>> getIndexedFields() {
		return indexedFields;
	}

	/**
	 * フィールド以外で発生したエラーの一覧を取得します。
	 * 
	 * @return フィールド以外で発生したエラーの一覧
	 */
	public List<String> getOthers() {
		return others;
	}

	/**
	 * エラーが存在しないかどうかを判定します。
	 * 
	 * @return エラーが存在しなければtrue
	 */
	public boolean isEmpty() {
		return all.isEmpty();
	}

	/**
	 * エラーメッセージを追加します。
	 * 
	 * @param message
	 *            メッセージ
	 */
	public void add(final String message) {
		this.add(message, new FieldInfo[0]);
	}

	/**
	 * エラーメッセージを追加します。
	 * 
	 * @param message
	 *            メッセージ
	 * @param fieldInfos
	 *            フィールド情報
	 */
	public void add(final String message, final String... fieldNames) {
		final FieldInfo[] fieldInfos = new FieldInfo[fieldNames.length];
		for (int i = 0 ; i < fieldNames.length; i++) {
			fieldInfos[i] = new FieldInfo(fieldNames[i]);
		}
		this.add(message, fieldInfos);
	}

	/**
	 * エラーメッセージを追加します。
	 * 
	 * @param message
	 *            メッセージ
	 * @param fieldInfos
	 *            フィールド情報
	 */
	public void add(final String message, final FieldInfo... fieldInfos) {
		if (fieldInfos == null || fieldInfos.length == 0) {
			others.add(message);
		} else {
			for (final FieldInfo fieldInfo : fieldInfos) {
				addFields(message, fieldInfo);
			}
		}
		this.all.add(message);
	}

	private void addFields(final String message, final FieldInfo fieldInfo) {
		final String name = fieldInfo == null ? null : fieldInfo.getName();
		final Integer index = fieldInfo == null ? null : fieldInfo.getIndex();

		final List<String> messages = this.fields.get(name);
		messages.add(message);

		final List<String> indexedMessages = this.indexedFields.get(name).get(
				index);
		indexedMessages.add(message);
	}

	class NotNullHashMap<K, V> extends HashMap<K, V> {

		private static final long serialVersionUID = 1L;

		private final EmptyValueFactory<V> emptyValueFactory;

		public NotNullHashMap(EmptyValueFactory<V> emptyValueFactory) {
			this.emptyValueFactory = emptyValueFactory;
		}

		@SuppressWarnings("unchecked")
		@Override
		public V get(final Object key) {
			final V value = super.get(key);
			if (value != null) {
				return value;
			}

			final V emptyValue = emptyValueFactory.create();
			this.put((K) key, emptyValue);
			return emptyValue;
		}

	}

	interface EmptyValueFactory<T> {
		T create();
	}

	class MessageListEmptyValueFactory implements EmptyValueFactory<List<String>> {

		public List<String> create() {
			return new ArrayList<String>();
		}
		
	}

	class IndexMapEmptyValueFactory implements EmptyValueFactory<Map<Integer, List<String>>> {

		public Map<Integer, List<String>> create() {
			return new NotNullHashMap<Integer, List<String>>(
					new MessageListEmptyValueFactory());
		}

	}

	/**
	 * エラーをクリアします。
	 */
	public void clear() {
		this.all.clear();
		this.fields.clear();
		this.indexedFields.clear();
		this.others.clear();
	}

}