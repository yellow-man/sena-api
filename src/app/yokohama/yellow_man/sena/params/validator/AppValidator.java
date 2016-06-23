package yokohama.yellow_man.sena.params.validator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.Payload;

import play.data.validation.Constraints;
import play.libs.F;
import play.libs.F.Tuple;
import yokohama.yellow_man.common_tools.CheckUtils;
import yokohama.yellow_man.common_tools.DateUtils;
import yokohama.yellow_man.common_tools.NumberUtils;
import yokohama.yellow_man.sena.components.db.secure.UsersComponent;
import yokohama.yellow_man.sena.core.components.AppLogger;
import yokohama.yellow_man.sena.core.definitions.AppConsts;
import yokohama.yellow_man.sena.core.models.secure.Users;
import yokohama.yellow_man.sena.params.AccountStocksCreateParams;
import yokohama.yellow_man.sena.params.AccountStocksCreateParams.StocksJson.Stocks;


/**
 * アプリケーション固有のバリデーション定義クラス。
 * @author yellow-man
 * @since 1.1
 */
public class AppValidator extends Constraints {

	/**
	 * 日付バリデーションアノテーション定義。
	 * <p>使用方法
	 * <pre>
	 * {@code @AppValidator.Date(value="yyyyMMdd", message="日付が不正です。")}
	 * </pre>
	 * @author yellow-man
	 * @since 1.1
	 * @see yokohama.yellow_man.sena.params.validator.AppValidator.DateValidator
	 */
	@Target({ java.lang.annotation.ElementType.FIELD })
	@Retention(RetentionPolicy.RUNTIME)
	@Constraint(validatedBy = { AppValidator.DateValidator.class })
	@play.data.Form.Display(name = "appvalidator.date", attributes = { "value" })
	public static @interface Date {
		String message() default "error.invalid";

		Class<?>[] groups() default {};

		Class<? extends Payload>[] payload() default {};

		String value();
	}

	/**
	 * 日付バリデーションクラス。
	 * @author yellow-man
	 * @since 1.1
	 * @see yokohama.yellow_man.sena.params.validator.AppValidator.Date
	 */
	public static class DateValidator extends Constraints.Validator<String>
			implements ConstraintValidator<AppValidator.Date, String>{

		private String format;

		/**
		 * 初期化処理。
		 * <p>このクラスの{@code format}を{@code paramFormat.value()}で初期化する。
		 * @since 1.1
		 */
		@Override
		public void initialize(AppValidator.Date paramFormat) {
			this.format = paramFormat.value();
		}

		/**
		 * バリデーションチェック。
		 * <p>指定された日付{@code date}が、フォーマット{@code format}に適合する場合、{@code true}。
		 * 適合しない場合、{@code false}を返す。
		 * @since 1.1
		 */
		@Override
		public boolean isValid(String date) {
			if (CheckUtils.isEmpty(date)) {
				return true;
			}
			return DateUtils.toDate(date, this.format) != null;
		}

		/**
		 * デフォルトエラーメッセージキー。
		 * @since 1.1
		 */
		@Override
		public Tuple<String, Object[]> getErrorMessageKey() {
			return F.Tuple("error.invalid", new Object[0]);
		}
	}

	/**
	 * アクセストークンバリデーションアノテーション定義。
	 * <p>使用方法
	 * <pre>
	 * {@code @AppValidator.AccessToken(message="アクセストークンが不正です。")}
	 * </pre>
	 * @author yellow-man
	 * @since 1.1
	 * @see yokohama.yellow_man.sena.params.validator.AppValidator.AccessTokenValidator
	 */
	@Target({ java.lang.annotation.ElementType.FIELD })
	@Retention(RetentionPolicy.RUNTIME)
	@Constraint(validatedBy = { AppValidator.AccessTokenValidator.class })
	@play.data.Form.Display(name = "appvalidator.access_token")
	public static @interface AccessToken {
		String message() default "error.invalid";

		Class<?>[] groups() default {};

		Class<? extends Payload>[] payload() default {};
	}

	/**
	 * アクセストークン（accessToken）バリデーションクラス。
	 * @author yellow-man
	 * @since 1.1
	 * @see yokohama.yellow_man.sena.params.validator.AppValidator.AccessToken
	 */
	public static class AccessTokenValidator extends Constraints.Validator<String>
			implements ConstraintValidator<AppValidator.AccessToken, String>{

		/**
		 * 初期化処理。
		 * @since 1.1
		 */
		@Override
		public void initialize(AppValidator.AccessToken paramAccessToken) {}

		/**
		 * バリデーションチェック。
		 * <p>アクセストークンからユーザー情報が取得できた場合{@code true}、
		 * 取得できない場合{@code false}を返す。
		 * @since 1.1
		 */
		@Override
		public boolean isValid(String accessToken) {
			if (CheckUtils.isEmpty(accessToken)) {
				return true;
			}
			Users users = UsersComponent.getUsersByAccessToken(accessToken);
			return (users != null);
		}

		/**
		 * デフォルトエラーメッセージキー。
		 * @since 1.1
		 */
		@Override
		public Tuple<String, Object[]> getErrorMessageKey() {
			return F.Tuple("error.invalid", new Object[0]);
		}
	}

	/**
	 * 銘柄情報バリデーションアノテーション定義。
	 * <p>使用方法
	 * <pre>
	 * {@code @AppValidator.StocksJson(message="銘柄情報が不正です。")}
	 * </pre>
	 * @author yellow-man
	 * @since 1.1
	 * @see yokohama.yellow_man.sena.params.validator.AppValidator.AccessTokenValidator
	 */
	@Target({ java.lang.annotation.ElementType.FIELD })
	@Retention(RetentionPolicy.RUNTIME)
	@Constraint(validatedBy = { AppValidator.StocksJsonValidator.class })
	@play.data.Form.Display(name = "appvalidator.stocks_json")
	public static @interface StocksJson {
		String message() default "error.invalid";

		Class<?>[] groups() default {};

		Class<? extends Payload>[] payload() default {};
	}

	/**
	 * 銘柄情報（stocksJson）バリデーションクラス。
	 * @author yellow-man
	 * @since 1.1
	 */
	public static class StocksJsonValidator extends Validator<String>
			implements ConstraintValidator<AppValidator.StocksJson, String>{

		/** 最大件数（50件） */
		private static final int STOCKS_JSON_LIST_MAX_SIZE = 50;

		/** エラー値：BigDecimalパラメータチェックエラー時の値 */
		private static final String DECIMAL_CHECK_ERROR = "-1";

		/**
		 * 初期化処理。
		 * @since 1.1
		 */
		@Override
		public void initialize(AppValidator.StocksJson paramStocksJson) {}

		/**
		 * バリデーションチェック。
		 * <p>チェック項目
		 * <ul>
		 * <li>銘柄情報jsonが、jsonとして解析できない場合エラー。
		 * <li>銘柄情報jsonで指定できる最大レコード件数{@code STOCKS_JSON_LIST_MAX_SIZE=50}を超える場合エラー。
		 * <li>取引種別（type）が指定項目ID{@link AppConsts#STOCKS_TYPE_MAP}以外の場合エラー。
		 * <li>保有株数（amount）が少数値として解析できない場合エラー。
		 * <li>平均取得単価（averageCost）が少数値として解析できない場合エラー。
		 * <li>現在値（currentValue）が少数値として解析できない場合エラー。
		 * </ul>
		 * 上記チェック項目がすべて正常な場合{@code true}を返す。
		 * @since 1.1
		 */
		@Override
		public boolean isValid(String stocksJson) {
			String errorMessage = new StringBuffer("：stocksJson=").append(stocksJson).toString();

			if (CheckUtils.isEmpty(stocksJson)) {
				return true;
			}

			// json解析
			yokohama.yellow_man.sena.params.AccountStocksCreateParams.StocksJson retStocksJson =
					AccountStocksCreateParams.paseStocksJson(stocksJson);

			if (CheckUtils.isEmpty(retStocksJson.stocks)) {
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
		 * @since 1.1
		 */
		@Override
		public Tuple<String, Object[]> getErrorMessageKey() {
			return F.Tuple("error.invalid", new Object[0]);
		}
	}

	/**
	 * BigDecimalバリデーションアノテーション定義。
	 * <p>使用方法
	 * <pre>
	 * {@code @AppValidator.BigDecimal(min="0.00", max="999999999.99" message="数値が不正です。")}
	 * </pre>
	 * @author yellow-man
	 * @since 1.1
	 * @see yokohama.yellow_man.sena.params.validator.AppValidator.BigDecimalValidator
	 */
	@Target({ java.lang.annotation.ElementType.FIELD })
	@Retention(RetentionPolicy.RUNTIME)
	@Constraint(validatedBy = { AppValidator.BigDecimalValidator.class })
	@play.data.Form.Display(name = "appvalidator.bigdecimal", attributes = { "min", "max" })
	public static @interface BigDecimal {
		String message() default "error.invalid";

		Class<?>[] groups() default {};

		Class<? extends Payload>[] payload() default {};

		String min();

		String max();
	}

	/**
	 * BigDecimalバリデーションクラス。
	 * @author yellow-man
	 * @since 1.1
	 * @see yokohama.yellow_man.sena.params.validator.AppValidator.BigDecimal
	 */
	public static class BigDecimalValidator extends Constraints.Validator<java.math.BigDecimal>
			implements ConstraintValidator<AppValidator.BigDecimal, java.math.BigDecimal>{

		private String min;

		private String max;

		/**
		 * 初期化処理。
		 * <p>このクラスの{@code min}、{@code max}を{@code param.min()}、{@code param.max()}で初期化する。
		 * @since 1.1
		 */
		@Override
		public void initialize(AppValidator.BigDecimal param) {
			this.min = param.min();
			this.max = param.max();
		}

		/**
		 * バリデーションチェック。
		 * <p>指定された日付{@code date}が、フォーマット{@code format}に適合する場合、{@code true}。
		 * 適合しない場合、{@code false}を返す。
		 * @since 1.1
		 */
		@Override
		public boolean isValid(java.math.BigDecimal targetDecimal) {
			if (targetDecimal == null) {
				return true;
			}
			java.math.BigDecimal minDecimal    = new java.math.BigDecimal(this.min);
			java.math.BigDecimal maxDecimal    = new java.math.BigDecimal(this.max);

			if (minDecimal.compareTo(targetDecimal) > 0) {
				return false;
			}
			if (maxDecimal.compareTo(targetDecimal) < 0) {
				return false;
			}

			return true;
		}

		/**
		 * デフォルトエラーメッセージキー。
		 * @since 1.1
		 */
		@Override
		public Tuple<String, Object[]> getErrorMessageKey() {
			return F.Tuple("error.invalid", new Object[0]);
		}
	}
}