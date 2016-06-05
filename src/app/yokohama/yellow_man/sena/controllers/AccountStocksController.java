package yokohama.yellow_man.sena.controllers;

import play.data.Form;
import play.mvc.Result;
import yokohama.yellow_man.sena.annotations.UsersTrace;
import yokohama.yellow_man.sena.params.AccountStocksCreateParams;
import yokohama.yellow_man.sena.params.AccountStocksCreateParams.StocksJson;
import yokohama.yellow_man.sena.response.ApiResult;

/**
 * 口座銘柄APIコントローラークラス。
 * <p>口座銘柄登録、一覧取得の機能を提供する。
 *
 * @author yellow-man
 * @since 1.1
 */
@UsersTrace
public class AccountStocksController extends AppWebApiController {

	public static Result getList() {
		// 返却値初期化
		ApiResult ret = new ApiResult(API_RES_SUCCESS);
		return ok(ret.render());
	}

	/**
	 * 口座銘柄を登録する。
	 * <p>日付、銘柄コード、取引種別で既に登録済みの場合、
	 * データベースに保存されている同一レコードの情報を更新する。
	 *
	 * @return json文字列
	 * @since 1.1
	 */
	public static Result postCreate() {
		// 返却値初期化
		ApiResult ret = new ApiResult(API_RES_SUCCESS);

		// パラメータマッピングバリデーションチェック
		Form<AccountStocksCreateParams> requestParams = Form.form(AccountStocksCreateParams.class).bindFromRequest();

		// バリデーションチェック結果
		if (requestParams.hasErrors()) {
			ret.setErrors(requestParams.errorsAsJson());
			ret.setResult(API_RES_FAILURE);

			return badRequest(ret.render());
		}

		// パラメータ取得
		AccountStocksCreateParams accountStocksParams = requestParams.get();

		// jsonをパース
		StocksJson stocksJson = accountStocksParams.paseStocksJson();

		return ok(ret.render());
	}
}
