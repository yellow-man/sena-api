package yokohama.yellow_man.sena.controllers;

import java.util.HashMap;
import java.util.Map;

import play.data.Form;
import play.mvc.Result;
import yokohama.yellow_man.sena.components.db.secure.UsersComponent;
import yokohama.yellow_man.sena.core.models.secure.Users;
import yokohama.yellow_man.sena.params.UsersCreateParams;
import yokohama.yellow_man.sena.response.ApiResult;

/**
 * ユーザーAPIコントローラークラス。
 * <p>Sena-apiを利用するためのユーザー作成、ユーザーのアカウント認証を提供する。
 *
 * @author yellow-man
 * @since 1.1
 */
public class UsersController extends AppWebApiController {

	/**
	 * Sena-apiを利用するためのユーザーを作成する。
	 *
	 * @return json文字列
	 * <pre>
	 * {
	 *   "result": 0,
	 *   "accessToken": "ACCESS_TOKEN"
	 * }
	 * </pre>
	 * @since 1.1
	 * @see <a href="https://github.com/yellow-man/sena-api/wiki/1.1.0_users_create">[POST] /users/create - APIドキュメント</a>
	 */
	public static Result postCreate() {
		// 返却値初期化
		ApiResult ret = new ApiResult(API_RES_SUCCESS);

		// パラメータマッピングバリデーションチェック
		Form<UsersCreateParams> requestParams = Form.form(UsersCreateParams.class).bindFromRequest();

		// バリデーションチェック結果
		if (requestParams.hasErrors()) {
			ret.setErrors(requestParams.errorsAsJson());
			ret.setResult(API_RES_FAILURE);

			return badRequest(ret.render());
		}

		// パラメータ取得
		UsersCreateParams usersCreateParams = requestParams.get();

		// ユーザー作成
		Users users = UsersComponent.create(usersCreateParams.accountId, usersCreateParams.password, usersCreateParams.nickname);

		// レスポンスセット
		Map<String, Object> retMap = new HashMap<>();
		retMap.put("accessToken", users.accessToken);
		ret.setContent(retMap);

		return ok(ret.render());
	}

	/**
	 * アカウントID、パスワードによりアプリケーションにログインする。
	 *
	 * @return json文字列
	 * @since 1.1
	 * @see <a href="https://github.com/yellow-man/sena-api/wiki/1.1.0_users_login">[POST] /users/login - APIドキュメント</a>
	 */
	public static Result postLogin() {
		// 返却値初期化
		ApiResult ret = new ApiResult(API_RES_SUCCESS);
		return ok(ret.render());
	}
}
