package yokohama.yellow_man.sena.params;

import yokohama.yellow_man.common_tools.FieldUtils;

/**
 * リクエストパラメータマッピング基底クラス。
 * @author yellow-man
 * @since 1.1
 */
public class AppParams {

	/**
	 * フィールドの値を文字列として取得する。
	 *
	 * @see java.lang.Object#toString()
	 * @since 1.1
	 */
	@Override
	public String toString() {
		return FieldUtils.toStringField(this);
	}
}
