package yokohama.yellow_man.sena.components.db.secure;

import yokohama.yellow_man.common_tools.DateUtils;
import yokohama.yellow_man.sena.core.models.secure.AccountSummaries;
import yokohama.yellow_man.sena.params.AccountSummariesCreateParams;

/**
 * 口座銘柄（account_stocks）モデルの操作を行うコンポーネントクラス。
 *
 * @author yellow-man
 * @since 1.1
 */
public class AccountSummariesComponent extends yokohama.yellow_man.sena.core.components.db.secure.AccountSummariesComponent {

	/**
	 * 口座銘柄（account_stocks）作成処理。
	 * @param usersId ユーザーID
	 * @param accountStocksParams {@link yokohama.yellow_man.sena.params.AccountSummariesCreateParams}
	 * @since 1.1
	 */
	public static void create(Long usersId, AccountSummariesCreateParams accountStocksParams) {

		AccountSummaries accountSummaries = new AccountSummaries();
		accountSummaries.usersId = usersId;
		accountSummaries.acquisitionDatetime = DateUtils.toDate(accountStocksParams.date, DateUtils.DATE_FORMAT_YYYYMMDDHHMMSS);
		accountSummaries.closingDate = null;
		accountSummaries.closingFlg = false;
		accountSummaries.freeMargin = accountStocksParams.freeMargin;
		accountSummaries.stockPurchased = accountStocksParams.stockPurchased;
		accountSummaries.initialMarginCache = accountStocksParams.initialMarginCache;
		accountSummaries.initialMarginValue = accountStocksParams.initialMarginValue;
		accountSummaries.paymentExpensesTotal = accountStocksParams.paymentExpensesTotal;
		accountSummaries.initialMargin = accountStocksParams.initialMargin;
		accountSummaries.openInterest = accountStocksParams.openInterest;
		accountSummaries.maintenanceRequirement = accountStocksParams.maintenanceRequirement;
		accountSummaries.refMaintenanceRequirement = accountStocksParams.refMaintenanceRequirement;
		accountSummaries.freeCache = accountStocksParams.freeCache;
		accountSummaries.freeCacheEtc = accountStocksParams.freeCacheEtc;
		accountSummaries.assetValue = accountStocksParams.assetValue;
		accountSummaries.marginProfitLoss = accountStocksParams.marginProfitLoss;
		accountSummaries.accountBalance = accountStocksParams.accountBalance;
		accountSummaries.save();
	}
}
