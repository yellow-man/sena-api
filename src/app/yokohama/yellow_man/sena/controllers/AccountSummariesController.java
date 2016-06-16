package yokohama.yellow_man.sena.controllers;

import java.util.Date;

import play.data.Form;
import play.mvc.Result;
import yokohama.yellow_man.common_tools.DateUtils;
import yokohama.yellow_man.sena.annotations.UsersTrace;
import yokohama.yellow_man.sena.annotations.action.UsersTraceAction;
import yokohama.yellow_man.sena.components.db.secure.AccountStocksComponent;
import yokohama.yellow_man.sena.core.models.secure.Users;
import yokohama.yellow_man.sena.params.AccountStocksCreateParams;
import yokohama.yellow_man.sena.params.AccountStocksCreateParams.StocksJson;
import yokohama.yellow_man.sena.response.ApiResult;

/**
 * 口座サマリーAPIコントローラークラス。
 * <p>口座サマリー登録、一覧取得の機能を提供する。
 *
 * @author yellow-man
 * @since 1.1
 */
@UsersTrace
public class AccountSummariesController extends AppWebApiController {

	/**
	 * 口座サマリーを登録する。
	 * <p>日付、銘柄コード、取引種別で既に登録済みの場合、
	 * データベースに保存されている同一レコードの情報を更新する。
	 *
	 * @return json文字列
	 * <pre>
	 * {
	 *   "result": 0
	 * }
	 * </pre>
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

		// 日付を取得
		Date acquisitionDatetime = DateUtils.toDate(accountStocksParams.date, DateUtils.DATE_FORMAT_YYYYMMDDHHMMSS);

		// コンテキストに詰めたユーザー情報取得
		Users users = (Users) ctx().args.get(UsersTraceAction.CONTEXT_PARAMETER_NAME_USERS);

		// 口座銘柄作成
		AccountStocksComponent.create(users.id, acquisitionDatetime, stocksJson.stocks);

		return ok(ret.render());
	}
}
