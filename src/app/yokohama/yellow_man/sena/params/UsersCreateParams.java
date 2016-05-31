package yokohama.yellow_man.sena.params;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.Pattern;
import play.data.validation.Constraints.Required;

/**
 * [POST] /users/create パラメータマッピングクラス。
 *
 * @author yellow-man
 * @since 1.1
 */
public class UsersCreateParams {

	/** アカウントID */
	@Required(message="アカウントID（accountId）は必須入力です。")
	@Pattern(value="^[a-zA-Z0-9_-]{8,20}+$"
				, message="アカウントID（accountId）は8文字以上、20文字以内で指定してください。アルファベット大文字、小文字、数字、記号（_-）が利用可能です。")
	public String accountId;

	/** パスワード */
	@Required(message="パスワード（password）は必須入力です。")
	@Pattern(value="^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?\\d)[a-zA-Z\\d]{8,20}$"
				, message="パスワード（password）は8文字以上、20文字以内で指定してください。アルファベット大文字、小文字、数字それぞれ1種類以上含む組み合わせにより登録可能です。")
	public String password;

	/** ニックネーム */
	@Required(message="ニックネーム（nickname）は必須入力です。")
	@MaxLength(value=20
				, message="ニックネーム（nickname）は20文字以内で指定してください。全角半角日本語、アルファベット大文字、小文字、数字、記号（_-）が利用可能です。")
	public String nickname;
}
