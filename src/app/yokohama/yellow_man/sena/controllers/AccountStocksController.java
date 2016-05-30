package yokohama.yellow_man.sena.controllers;

import play.mvc.Result;
import yokohama.yellow_man.sena.controllers.response.ApiResult;

/**
 * 口座銘柄APIコントローラークラス。
 *
 * @author yellow-man
 * @since 1.0
 */
public class AccountStocksController extends AppWebApiController {

	public static Result getList() {
		// 返却値初期化
		ApiResult ret = new ApiResult(API_RES_SUCCESS);
		return ok(ret.render());
	}

	public static Result postCreate() {
		// 返却値初期化
		ApiResult ret = new ApiResult(API_RES_SUCCESS);
		return ok(ret.render());
	}
}
