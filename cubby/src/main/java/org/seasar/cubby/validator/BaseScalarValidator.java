package org.seasar.cubby.validator;

import org.seasar.cubby.validator.BaseValidator;
import org.seasar.cubby.validator.ValidationContext;

/**
 * スカラ値に対する入力検証の基底クラスです。
 * 
 * @author baba
 */
public abstract class BaseScalarValidator extends BaseValidator {

	/**
	 * 入力検証を行います。
	 * <p>
	 * コンテキストに配列で保持している入力値をひとつずつ取り出して、メソッド
	 * {@link #validate(Object, ValidationContext)} によって検証します。
	 * </p>
	 * 
	 * @param context
	 *            入力検証コンテキスト
	 * @see #validate(Object, ValidationContext)
	 */
	public void validate(final ValidationContext context) {
		for (final Object value : context.getValues()) {
			validate(value, context);
		}
	}

	/**
	 * 対象の値をひとつだけ検証します。
	 * 
	 * @param value
	 *            検証対象の値
	 * @param context
	 *            入力検証コンテキスト
	 */
	protected abstract void validate(Object value, ValidationContext context);

}
