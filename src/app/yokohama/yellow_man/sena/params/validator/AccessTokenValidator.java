package yokohama.yellow_man.sena.params.validator;

import play.data.validation.Constraints.Validator;
import play.libs.F;
import play.libs.F.Tuple;
import yokohama.yellow_man.sena.components.db.secure.UsersComponent;
import yokohama.yellow_man.sena.core.models.secure.Users;

/**
 * パラメータ アクセストークン（accessToken）に関するバリデーションクラス。
 *
 * @author yellow-man
 * @since 1.1
 */
public class AccessTokenValidator extends Validator<String> {

	/**
	 * バリデーションチェック。
	 * <p>アクセストークンからユーザー情報が取得できた場合{@code true}、
	 * 取得できない場合{@code false}を返す。
	 * @see play.data.validation.Constraints.Validator#isValid(T)
	 * @since 1.1
	 */
	@Override
	public boolean isValid(String accessToken) {
		Users users = UsersComponent.getUsersByAccessToken(accessToken);
		return (users != null);
	}

	/**
	 * デフォルトエラーメッセージキー。
	 * @see play.data.validation.Constraints.Validator#getErrorMessageKey()
	 * @since 1.1
	 */
	@Override
	public Tuple<String, Object[]> getErrorMessageKey() {
		return F.Tuple("error.invalid", new Object[] {});
	}
}