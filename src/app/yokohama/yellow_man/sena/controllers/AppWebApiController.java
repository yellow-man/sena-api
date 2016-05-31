package yokohama.yellow_man.sena.controllers;

import play.mvc.Controller;

/**
 * WebAPIコントローラー基底クラス。
 * <p>WebAPIを実装する場合、このクラスを継承する。
 *
 * @author yellow-man
 * @since 1.1
 */
public class AppWebApiController extends Controller {

	/** API結果：0.成功 */
	public static final int API_RES_SUCCESS = 0;

	/** API結果：1.失敗 */
	public static final int API_RES_FAILURE = 1;

	/** API結果：999.システムエラー */
	public static final int API_RES_SYS_ERROR = 999;
}
