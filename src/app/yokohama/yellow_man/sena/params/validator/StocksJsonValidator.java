package yokohama.yellow_man.sena.params.validator;

import play.data.validation.Constraints.Validator;
import play.libs.F;
import play.libs.F.Tuple;
import yokohama.yellow_man.common_tools.CheckUtils;
import yokohama.yellow_man.common_tools.DateUtils;
import yokohama.yellow_man.sena.core.components.AppLogger;
import yokohama.yellow_man.sena.params.AccountStocksCreateParams;
import yokohama.yellow_man.sena.params.AccountStocksCreateParams.StocksJson;
import yokohama.yellow_man.sena.params.AccountStocksCreateParams.StocksJson.Stocks;

/**
 * パラメータ 銘柄情報（stocksJson）に関するバリデーションクラス。
 *
 * @author yellow-man
 * @since 1.1
 */
public class StocksJsonValidator extends Validator<String> {

	/**
	 * バリデーションチェック。
	 * <p>
	 * @see play.data.validation.Constraints.Validator#isValid(T)
	 * @since 1.1
	 */
	@Override
	public boolean isValid(String stocksJson) {
		String errorMessage = new StringBuffer("：stocksJson=").append(stocksJson).toString();

		StocksJson retStocksJson = AccountStocksCreateParams.paseStocksJson(stocksJson);
		if (retStocksJson == null || CheckUtils.isEmpty(retStocksJson.stocks)) {
			AppLogger.warn("json解析ができない。" + errorMessage);
			return false;
		}

		// 最大件数（50件チェック）

		// 項目ごとのバリデーション
		for (Stocks stocks : retStocksJson.stocks) {
			// 日付チェック
			if (DateUtils.toDate(stocks.date, DateUtils.DATE_FORMAT_YYYYMMDD) == null) {
				AppLogger.warn("日付（date）が不正" + errorMessage);
				return false;
			}

			// 取引種別チェック

			// 保有株数チェック

			// 平均取得単価チェック

			// 現在値チェック
		}

		return true;
	}

	/**
	 * デフォルトエラーメッセージキー。
	 * @see play.data.validation.Constraints.Validator#getErrorMessageKey()
	 * @since 1.1
	 */
	@Override
	public Tuple<String, Object[]> getErrorMessageKey() {
		return F.Tuple("error.invalid", new Object[] {});
	}

}
