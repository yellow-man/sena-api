package yokohama.yellow_man.sena.controllers;

import play.mvc.Result;
import yokohama.yellow_man.sena.controllers.response.ApiResult;

/**
 * ユーザーAPIコントローラークラス。
 *
 * @author yellow-man
 * @since 1.0
 */
public class UsersController extends AppWebApiController {

	public static Result postCreate() {
		// 返却値初期化
		ApiResult ret = new ApiResult(API_RES_SUCCESS);
		return ok(ret.render());
	}

	public static Result postLogin() {
		// 返却値初期化
		ApiResult ret = new ApiResult(API_RES_SUCCESS);
		return ok(ret.render());
	}
}
