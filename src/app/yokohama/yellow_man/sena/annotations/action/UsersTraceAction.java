package yokohama.yellow_man.sena.annotations.action;

import play.libs.F.Promise;
import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.Result;
import yokohama.yellow_man.common_tools.CheckUtils;
import yokohama.yellow_man.sena.components.db.secure.UsersComponent;
import yokohama.yellow_man.sena.core.components.AppLogger;
import yokohama.yellow_man.sena.core.components.RequestUtilityComponent;
import yokohama.yellow_man.sena.core.models.secure.Users;

/**
 * リクエストにアクセストークンを含むアクションに対するトレース中継処理クラス。
 * @author yellow-man
 * @since 1.1
 */
public class UsersTraceAction extends Action<UsersTraceAction> {

	/** コンテキストパラメータ：ユーザー情報 */
	public static final String CONTEXT_PARAMETER_NAME_USERS = "users";

	/** リクエストパラメータ：アクセストークン */
	private static final String PARAMS_NAME_ACCESS_TOKEN = "accessToken";

	@Override
	public Promise<Result> call(Context ctx) throws Throwable {
		AppLogger.info(new StringBuffer(ctx.toString()).append(" start").toString());

		// アクセストークン取得
		String accessToken = RequestUtilityComponent.getFromPostString(ctx.request(), PARAMS_NAME_ACCESS_TOKEN);
		AppLogger.info("accessToken=" + accessToken);

		// アクセストークンが取得できた場合、ユーザー情報を取得する。
		StringBuffer usersIdBuff = new StringBuffer("users_id:");
		if (!CheckUtils.isEmpty(accessToken)) {
			Users users = UsersComponent.getUsersByAccessToken(accessToken);
			if (users != null) {
				usersIdBuff.append(String.valueOf(users.id));

				// ユーザー情報をコンテキストから利用可能なように詰める
				ctx.args.put(CONTEXT_PARAMETER_NAME_USERS, users);
			}
		}

		Promise<Result> promise = null;
		try {
			AppLogger.putMDC(AppLogger.MDC_FORMAT_KEY_SID, usersIdBuff.toString());

			// メイン処理開始
			AppLogger.info(new StringBuffer(ctx.toString()).append(" action start").toString());
			promise = delegate.call(ctx);
			AppLogger.info(new StringBuffer(ctx.toString()).append(" action end").toString());

		} catch (Exception e) {
			throw e;
		} finally {
			// MDCは必ず破棄
			AppLogger.removeMDC(AppLogger.MDC_FORMAT_KEY_SID);
		}

		AppLogger.info(new StringBuffer(ctx.toString()).append(" end").toString());
		return promise;
	}
}
