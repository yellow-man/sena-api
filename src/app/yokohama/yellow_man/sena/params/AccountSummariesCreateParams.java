package yokohama.yellow_man.sena.params;

import java.math.BigDecimal;

import play.data.validation.Constraints.Max;
import play.data.validation.Constraints.Min;
import play.data.validation.Constraints.Required;
import yokohama.yellow_man.common_tools.DateUtils;
import yokohama.yellow_man.sena.params.validator.AppValidator;

/**
 * [POST] /account_summaries/create パラメータマッピングクラス。
 *
 * @author yellow-man
 * @since 1.1
 */
public class AccountSummariesCreateParams extends AppParams {

	/** アクセストークン */
	@Required(message="アクセストークン（accessToken）は必須入力です。")
	@AppValidator.AccessToken(message="アクセストークン（accessToken）が不正です。")
	public String accessToken;

	/** 取得日時 */
	@Required(message="取得日時（date）は必須入力です。")
	@AppValidator.Date(value=DateUtils.DATE_FORMAT_YYYYMMDDHHMMSS, message="取得日時（date）が不正です。")
	public String date;

	/** 信用建余力 */
	@Min(value=0L, message="信用建余力（freeMargin）は0～999999999999999で指定してください。")
	@Max(value=999999999999999L, message="信用建余力（freeMargin）は0～999999999999999で指定してください。")
	public Long freeMargin;

	/** 現引可能額 */
	@Min(value=0L, message="現引可能額（stockPurchased）は0～999999999999999で指定してください。")
	@Max(value=999999999999999L, message="現引可能額（stockPurchased）は0～999999999999999で指定してください。")
	public Long stockPurchased;

	/** 委託保証金現金 */
	@Min(value=0L, message="委託保証金現金（initialMarginCache）は0～999999999999999で指定してください。")
	@Max(value=999999999999999L, message="委託保証金現金（initialMarginCache）は0～999999999999999で指定してください。")
	public Long initialMarginCache;

	/** 代用有価証券評価額合計 */
	@Min(value=0L, message="代用有価証券評価額合計（initialMarginValue）は0～999999999999999で指定してください。")
	@Max(value=999999999999999L, message="代用有価証券評価額合計（initialMarginValue）は0～999999999999999で指定してください。")
	public Long initialMarginValue;

	/** 評価損・決済損益・支払諸経費等合計 */
	@Min(value=-999999999999999L, message="評価損・決済損益・支払諸経費等合計（paymentExpensesTotal）は-999999999999999～999999999999999で指定してください。")
	@Max(value=999999999999999L, message="評価損・決済損益・支払諸経費等合計（paymentExpensesTotal）は-999999999999999～999999999999999で指定してください。")
	public Long paymentExpensesTotal;

	/** 実質保証金 */
	@Min(value=0L, message="実質保証金（initialMargin）は0～999999999999999で指定してください。")
	@Max(value=999999999999999L, message="実質保証金（initialMargin）は0～999999999999999で指定してください。")
	public Long initialMargin;

	/** 建代金合計 */
	@Min(value=0L, message="建代金合計（openInterest）は0～999999999999999で指定してください。")
	@Max(value=999999999999999L, message="建代金合計（openInterest）は0～999999999999999で指定してください。")
	public Long openInterest;

	/** 委託保証金率 */
	@AppValidator.BigDecimal(min="0.00", max="999999999.99", message="委託保証金率（maintenanceRequirement）は0.00～999999999.99で指定してください。")
	public BigDecimal maintenanceRequirement;

	/** 参考委託保証金率 */
	@AppValidator.BigDecimal(min="0.00", max="999999999.99", message="参考委託保証金率（refMaintenanceRequirement）は0.00～999999999.99で指定してください。")
	public BigDecimal refMaintenanceRequirement;

	/** 買付余力 */
	@Required(message="買付余力（freeCache）は必須入力です。")
	@Min(value=0L, message="買付余力（freeCache）は0～999999999999999で指定してください。")
	@Max(value=999999999999999L, message="買付余力（freeCache）は0～999999999999999で指定してください。")
	public Long freeCache;

	/** 現金残高等 */
	@Required(message="現金残高等（freeCacheEtc）は必須入力です。")
	@Min(value=0L, message="現金残高等（freeCacheEtc）は0～999999999999999で指定してください。")
	@Max(value=999999999999999L, message="現金残高等（freeCacheEtc）は0～999999999999999で指定してください。")
	public Long freeCacheEtc;

	/** 株式 */
	@Required(message="株式（assetValue）は必須入力です。")
	@AppValidator.BigDecimal(min="0.00000", max="999999999999999.99999", message="株式（assetValue）は0.00000～999999999999999.99999で指定してください。")
	public BigDecimal assetValue;

	/** 建玉評価損益額 */
	@Required(message="建玉評価損益額（marginProfitLoss）は必須入力です。")
	@Min(value=-999999999999999L, message="建玉評価損益額（marginProfitLoss）は-999999999999999～999999999999999で指定してください。")
	@Max(value=999999999999999L, message="建玉評価損益額（marginProfitLoss）は-999999999999999～999999999999999で指定してください。")
	public Long marginProfitLoss;

	/** 計 */
	@Required(message="計（accountBalance）は必須入力です。")
	@AppValidator.BigDecimal(min="0.00000", max="999999999999999.99999", message="計（accountBalance）は0.00000～999999999999999.99999で指定してください。")
	public BigDecimal accountBalance;
}
