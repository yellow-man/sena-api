package yokohama.yellow_man.sena.controllers;

import play.data.Form;
import play.mvc.Result;
import yokohama.yellow_man.sena.annotations.UsersTrace;
import yokohama.yellow_man.sena.annotations.action.UsersTraceAction;
import yokohama.yellow_man.sena.components.db.secure.AccountSummariesComponent;
import yokohama.yellow_man.sena.core.models.secure.Users;
import yokohama.yellow_man.sena.params.AccountSummariesCreateParams;
import yokohama.yellow_man.sena.response.ApiResult;

/**
 * 口座サマリーAPIコントローラークラス。
 * <p>口座サマリー登録の機能を提供する。
 *
 * @author yellow-man
 * @since 1.1
 */
@UsersTrace
public class AccountSummariesController extends AppWebApiController {

	/**
	 * 口座サマリーを登録する。
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
		Form<AccountSummariesCreateParams> requestParams = Form.form(AccountSummariesCreateParams.class).bindFromRequest();

		// バリデーションチェック結果
		if (requestParams.hasErrors()) {
			ret.setErrors(requestParams.errorsAsJson());
			ret.setResult(API_RES_FAILURE);

			return badRequest(ret.render());
		}

		// パラメータ取得
		AccountSummariesCreateParams accountStocksParams = requestParams.get();

		// コンテキストに詰めたユーザー情報取得
		Users users = (Users) ctx().args.get(UsersTraceAction.CONTEXT_PARAMETER_NAME_USERS);

		// 口座銘柄作成
		AccountSummariesComponent.create(users.id, accountStocksParams);

		return ok(ret.render());
	}
}
