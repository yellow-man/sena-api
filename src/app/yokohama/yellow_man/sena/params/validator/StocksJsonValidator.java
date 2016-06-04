package yokohama.yellow_man.sena.params.validator;

import play.data.validation.Constraints.Validator;
import play.libs.F;
import play.libs.F.Tuple;
import yokohama.yellow_man.common_tools.CheckUtils;
import yokohama.yellow_man.common_tools.DateUtils;
import yokohama.yellow_man.common_tools.NumberUtils;
import yokohama.yellow_man.sena.core.components.AppLogger;
import yokohama.yellow_man.sena.core.definitions.AppConsts;
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

	/** 最大件数（50件） */
	private static final int STOCKS_JSON_LIST_MAX_SIZE = 50;

	/** エラー値：BigDecimalパラメータチェックエラー時の値 */
	private static final String DECIMAL_CHECK_ERROR = "-1";

	/**
	 * バリデーションチェック。
	 * <p>
	 * @see play.data.validation.Constraints.Validator#isValid(T)
	 * @since 1.1
	 */
	@Override
	public boolean isValid(String stocksJson) {
		String errorMessage = new StringBuffer("：stocksJson=").append(stocksJson).toString();

		// json解析
		StocksJson retStocksJson = AccountStocksCreateParams.paseStocksJson(stocksJson);
		if (retStocksJson == null || CheckUtils.isEmpty(retStocksJson.stocks)) {
			AppLogger.warn("json解析ができない。" + errorMessage);
			return false;
		}

		// 最大件数（50件チェック）
		int size = retStocksJson.stocks.size();
		if (size > STOCKS_JSON_LIST_MAX_SIZE) {
			AppLogger.warn("指定最大件数を超えている。" + errorMessage + ", size=" + size);
		}

		// 項目ごとのバリデーション
		for (Stocks stocks : retStocksJson.stocks) {
			// 日付チェック
			if (DateUtils.toDate(stocks.date, DateUtils.DATE_FORMAT_YYYYMMDD) == null) {
				AppLogger.warn("日付（date）が不正" + errorMessage);
				return false;
			}

			// 取引種別チェック
			if (!AppConsts.STOCKS_TYPE_MAP.containsKey(stocks.type)) {
				AppLogger.warn("取引種別（type）が不正" + errorMessage);
				return false;
			}

			// 保有株数チェック
			if (DECIMAL_CHECK_ERROR.equals(NumberUtils.toBigDecimal(stocks.amount, DECIMAL_CHECK_ERROR))) {
				AppLogger.warn("保有株数（amount）が不正" + errorMessage);
				return false;
			}

			// 平均取得単価チェック
			if (DECIMAL_CHECK_ERROR.equals(NumberUtils.toBigDecimal(stocks.averageCost, DECIMAL_CHECK_ERROR))) {
				AppLogger.warn("平均取得単価（averageCost）が不正" + errorMessage);
				return false;
			}

			// 現在値チェック
			if (DECIMAL_CHECK_ERROR.equals(NumberUtils.toBigDecimal(stocks.currentValue, DECIMAL_CHECK_ERROR))) {
				AppLogger.warn("現在値（currentValue）が不正" + errorMessage);
				return false;
			}
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
