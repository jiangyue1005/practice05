package constant;

/**
 * メッセージEnum
 * @author e_kou
 */
public enum MsgEnum {
	I0001("占い開始します。 誕生日："), 
	I0002("DBと接続しました。"), 
	I0003("おみくじの登録が成功しました。"), 
	I0004("DBを切断しました。"),

	E0001("エラーが発生しました。"), 
	E0002("omikujiデータの登録が失敗しました。"), 
	E0003("DB接続にエラーが発生しました。"), 
	E0004("クラスが見つかりませんでした。"), 
	E0005("CSV処理にエラーが発生しました。"), 
	E0006("ドライバクラスが見つかりませんでした。"), 
	E0007("値が不正です。"), 
	E0008("DBとの切断処理が失敗しました。"), 
	E0009("日付の変換に失敗しました。"),
	;

	private String message;

	private MsgEnum(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

}
