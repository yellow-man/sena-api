package yokohama.yellow_man.common_tools;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

/**
 * フィールド操作に関する機能を提供します。
 * @author yellow-man
 * @since 1.0
 */
public class FieldUtils {

	/**
	 * 引数に指定されたオブジェクトのプロパティと、値を文字列に変換します。
	 * <br>また、staticフィールドに置いては文字列変換から除外します。
	 * @param obj プロパティと、値を文字列変換対象とするオブジェクト
	 * @return 変換後の文字列。
	 * @since 1.1
	 */
	public static String toStringField(Object obj) {
		StringBuffer buff = new StringBuffer(obj.getClass().getName()).append("{");

		Field[] fs = obj.getClass().getFields();
		StringBuffer fieldBuff = new StringBuffer();
		for (Field f : fs) {
			// フィールドの属性（修飾子）を取得
			int mod = f.getModifiers();

			// 静的要素は除外
			if (Modifier.isStatic(mod)) {
				continue;
			}

			try {
				fieldBuff.append(f.getName()).append("=");
				if (f.get(obj) == null) {
					fieldBuff.append("null, ");
				} else {
					if (f.get(obj) instanceof List) {
						StringBuffer listBuff = new StringBuffer("[");

						List<?> list = (List<?>) f.get(obj);
						for (Object tmpObj : list) {
							listBuff.append(tmpObj.toString()).append(", ");
						}
						if (listBuff.length() > 1) {
							listBuff.delete(listBuff.length()-2, listBuff.length());
						}

						fieldBuff.append(listBuff.toString()).append("], ");
					} else {
						fieldBuff.append(f.get(obj).toString()).append(", ");
					}
				}

			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (fieldBuff.length() > 1) {
			fieldBuff.delete(fieldBuff.length()-2, fieldBuff.length());
		}
		buff.append(fieldBuff);
		buff.append("}");
		return buff.toString();
	}
}
