package yokohama.yellow_man.sena.controllers;

import java.util.HashMap;
import java.util.Map;

import play.data.Form;
import play.mvc.Result;
import yokohama.yellow_man.sena.controllers.response.ApiResult;
import yokohama.yellow_man.sena.params.UsersCreateParams;

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
	 *   "accessToken": "DSppLO5fTAwieE5njSnN5hh2QqcGAVC4FnryyoSYzyUzinxxXpmNdtkVOnojHFo8q09TECo4zCRWWOsR0ERyirYonADkvNad73gYJP7z8F19jshvW7xF6IvO9pMTEKoF"
	 * }
	 * </pre>
	 * @since 1.1
	 * @see <a href="https://github.com/yellow-man/sena-api/wiki/1.1.0_users_create">[POST] /users/create - APIドキュメント</a>
	 */
	public static Result postCreate() {
		// 返却値初期化
		ApiResult ret = new ApiResult(API_RES_SUCCESS);

		Form<UsersCreateParams> userForm = Form.form(UsersCreateParams.class).bindFromRequest();
		if (userForm.hasErrors()) {
			ret.setResult(API_RES_FAILURE);
			Map<String, Object> map = new HashMap<>();
			map.put("errors", userForm.errorsAsJson());
			ret.setContent(map);
			return badRequest(ret.render());
		}

		UsersCreateParams usersForm = userForm.get();

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
