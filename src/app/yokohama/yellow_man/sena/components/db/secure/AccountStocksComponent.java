package yokohama.yellow_man.sena.components.db.secure;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import yokohama.yellow_man.common_tools.NumberUtils;
import yokohama.yellow_man.sena.core.models.secure.AccountStocks;

/**
 * 口座銘柄（account_stocks）モデルの操作を行うコンポーネントクラス。
 *
 * @author yellow-man
 * @since 1.1
 */
public class AccountStocksComponent extends yokohama.yellow_man.sena.core.components.db.secure.AccountStocksComponent {

	/**
	 * 口座銘柄（account_stocks）作成処理。
	 * @param usersId ユーザーID
	 * @param acquisitionDatetime 取得日時
	 * @param stocksList {@link yokohama.yellow_man.sena.params.AccountStocksCreateParams.StocksJson} 銘柄情報（stocksJson）マッピングリスト
	 * @return 登録成功件数を返す。
	 * @since 1.1
	 */
	public static int create(
			Long usersId,
			Date acquisitionDatetime,
			List<yokohama.yellow_man.sena.params.AccountStocksCreateParams.StocksJson.Stocks> stocksList) {

		int retCount = 0;
		// バルクインサート用リスト
		List<AccountStocks> accountStocksList = new ArrayList<>();
		Date now = new Date();
		for (yokohama.yellow_man.sena.params.AccountStocksCreateParams.StocksJson.Stocks stocksJson : stocksList) {
			AccountStocks accountStocks = new AccountStocks();
			accountStocks.usersId = usersId;
			accountStocks.acquisitionDatetime = acquisitionDatetime;
			accountStocks.stockCode = stocksJson.stockCode;
			accountStocks.type = stocksJson.type;
			accountStocks.closingDate = null;
			accountStocks.closingFlg = false;
			accountStocks.amount = NumberUtils.toBigDecimal(stocksJson.amount);
			accountStocks.averageCost = NumberUtils.toBigDecimal(stocksJson.averageCost);
			accountStocks.currentValue = NumberUtils.toBigDecimal(stocksJson.currentValue);
			accountStocks.marketValue = accountStocks.amount.multiply(accountStocks.currentValue);
			accountStocks.profitLoss = accountStocks.marketValue.subtract(accountStocks.amount.multiply(accountStocks.averageCost));
			accountStocks.profitLossRate =
					new BigDecimal(100).multiply(
							accountStocks.profitLoss.divide(accountStocks.marketValue, 4, BigDecimal.ROUND_HALF_UP));
			accountStocks.created = now;
			accountStocks.modified = now;
			accountStocks.deleteFlg = false;
			accountStocksList.add(accountStocks);
		}
		// バルクインサート
		retCount = executeBulkInsert(accountStocksList);
		return retCount;
	}
}
