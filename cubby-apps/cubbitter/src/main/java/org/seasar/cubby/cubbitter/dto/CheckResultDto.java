package org.seasar.cubby.cubbitter.dto;

/** Ajaxでのデータチェック戻り値用クラス */

public class CheckResultDto {
	/** エラーかどうか */
	public boolean isError;

	/** エラーメッセージ */
	public String errorMessage;
}