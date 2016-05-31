package yokohama.yellow_man.sena.controllers.response;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

import play.libs.Json;

/**
 * API結果格納クラス。
 *
 * @author yellow-man
 * @since 1.1
 */
public class ApiResult {

	/** 処理結果 **/
	private Integer result;

	/** コンテンツ **/
	private Map<String, Object> content;


	/**
	 * 処理結果{@code result}で初期化したコンストラクタ。
	 * @param result 処理結果
	 */
	public ApiResult(Integer result) {
		super();
		this.result = result;
		this.content = new HashMap<String, Object>();
	}

	/**
	 * 処理結果{@code result}、コンテンツ{@code content}で初期化したコンストラクタ。
	 * @param result 処理結果
	 * @param content コンテンツ
	 */
	public ApiResult(Integer result, Map<String, Object> content) {
		super();
		this.result = result;
		this.content = content;
	}

	/**
	 * 処理結果をセットする。
	 * @param result 処理結果
	 */
	public void setResult(Integer result) {
		this.result = result;
	}

	/**
	 * コンテンツをセットする。
	 * @param content コンテンツ
	 */
	public void setContent(Map<String, Object> content) {
		this.content = content;
	}

	/**
	 * コンテンツレンダリング処理。
	 * @return JSON jsonオブジェクト
	 */
	public JsonNode render() {
		content.put("result", result);
		return Json.toJson(content);
	}
}
