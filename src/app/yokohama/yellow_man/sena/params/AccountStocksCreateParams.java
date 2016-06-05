package yokohama.yellow_man.sena.params;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import play.data.validation.Constraints.Required;
import yokohama.yellow_man.common_tools.DateUtils;
import yokohama.yellow_man.sena.core.components.AppLogger;
import yokohama.yellow_man.sena.params.validator.AppValidator;

/**
 * [POST] /account_stocks/create パラメータマッピングクラス。
 *
 * @author yellow-man
 * @since 1.1
 */
public class AccountStocksCreateParams extends AppParams {

	/** アクセストークン */
	@Required(message="アクセストークン（accessToken）は必須入力です。")
	@AppValidator.AccessToken(message="アクセストークン（accessToken）が不正です。")
	public String accessToken;

	/** 取得日時 */
	@Required(message="取得日時（date）は必須入力です。")
	@AppValidator.Date(value=DateUtils.DATE_FORMAT_YYYYMMDDHHMMSS, message="取得日時（date）が不正です。")
	public String date;

	/** 銘柄情報 */
	@Required(message="銘柄情報（stocksJson）は必須入力です。")
	@AppValidator.StocksJson(message="銘柄情報（stocksJson）が不正です。")
	public String stocksJson;

	/**
	 * 銘柄情報（stocksJson）マッピングクラス。
	 * @author yellow-man
	 * @since 1.1
	 */
	public static class StocksJson {

		/** 銘柄情報のリスト */
		public List<Stocks> stocks;

		/**
		 * 銘柄情報クラス。
		 * @author yellow-man
		 * @since 1.1
		 */
		public static class Stocks {
			/** 銘柄コード */
			public Integer stockCode;
			/** 取引種別（1...国内株式、2...金額・株数指定取引、3...信用建玉（買建）、4...信用建玉（売建）） */
			public Integer type;
			/** 保有株数 */
			public String amount;
			/** 平均取得単価 */
			public String averageCost;
			/** 現在値 */
			public String currentValue;
		}
	}

	/**
	 * 銘柄情報（stocksJson）パース結果を返す。
	 * @return 銘柄情報（stocksJson）パース結果を返す。パースできない場合{@code null}
	 * @since 1.1
	 */
	public StocksJson paseStocksJson() {
		return paseStocksJson(this.stocksJson);
	}

	/**
	 * 銘柄情報（stocksJson）パース結果を返す。
	 * @param stocksJson 銘柄情報
	 * @return 銘柄情報（stocksJson）パース結果を返す。パースできない場合{@code null}
	 * @since 1.1
	 */
	public static StocksJson paseStocksJson(String stocksJson) {
		StocksJson ret = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			ret = mapper.readValue(stocksJson, StocksJson.class);
		} catch (Exception e) {
			AppLogger.warn("銘柄情報（stocksJson）パース時にエラーが発生しました。：stocksJson=" + stocksJson, e);
		}
		return ret;
	}
}
