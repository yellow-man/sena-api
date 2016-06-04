package yokohama.yellow_man.sena;


import play.Application;
import play.GlobalSettings;
import play.libs.F.Promise;
import play.mvc.Http.RequestHeader;
import play.mvc.Result;
import play.mvc.Results;
import yokohama.yellow_man.sena.controllers.AppWebApiController;
import yokohama.yellow_man.sena.core.components.AppLogger;
import yokohama.yellow_man.sena.response.ApiResult;

/**
 * アプリケーショングローバル設定クラス。
 * <p>起動、停止、エラー関連のイベントリスナーを実装。
 *
 * @author yellow-man
 * @since 1.1
 */
public class Global extends GlobalSettings {

	/**
	 * アプリケーション起動イベント。
	 * @see play.GlobalSettings#onStart(play.Application)
	 * @since 1.1
	 */
	@Override
	public void onStart(Application app) {
		super.onStart(app);
		AppLogger.info("=== Sena-api start ===");
	}

	/**
	 * アプリケーション停止イベント。
	 * @see play.GlobalSettings#onStop(play.Application)
	 * @since 1.1
	 */
	@Override
	public void onStop(Application app) {
		AppLogger.info("=== Sena-api stop ===");
		super.onStop(app);
	}

	/**
	 * 404：アクションが見つからない場合。
	 * @see play.GlobalSettings#onHandlerNotFound(play.mvc.Http.RequestHeader)
	 * @since 1.1
	 */
	@Override
	public Promise<Result> onHandlerNotFound(RequestHeader request) {
		AppLogger.warn(new StringBuffer("Request 404 Error：request=").append(request).toString());

		// API結果：1.失敗
		ApiResult ret = new ApiResult(AppWebApiController.API_RES_FAILURE);
		return Promise.<Result>pure(Results.notFound(ret.render()));
	}

	/**
	 * 400：リクエストエラー。
	 * @see play.GlobalSettings#onBadRequest(play.mvc.Http.RequestHeader, java.lang.String)
	 * @since 1.1
	 */
	@Override
	public Promise<Result> onBadRequest(RequestHeader request, String error) {
		AppLogger.warn(new StringBuffer("Request 400 Error：request=").append(request).append(", error=").append(error).toString());

		// API結果：1.失敗
		ApiResult ret = new ApiResult(AppWebApiController.API_RES_FAILURE);
		return Promise.<Result>pure(Results.badRequest(ret.render()));
	}

	/**
	 * 500：エラー共通。
	 * @see play.GlobalSettings#onError(play.mvc.Http.RequestHeader, java.lang.Throwable)
	 * @since 1.1
	 */
	@Override
	public Promise<Result> onError(RequestHeader request, Throwable t) {
		AppLogger.error(new StringBuffer("Request 500 Error：request=").append(request).toString(), t);

		// 999.システムエラー
		ApiResult ret = new ApiResult(AppWebApiController.API_RES_SYS_ERROR);
		return Promise.<Result>pure(Results.internalServerError(ret.render()));
	}
}
