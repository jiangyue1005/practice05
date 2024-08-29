package omikuji;

/**
 * おみくじ抽象クラス
 * @author e_kou
 */
public abstract class Omikuji implements Fortune {

	/** 運勢 */
	protected String unsei;
	/** おみくじコード */
	protected String omikujiCode;
	/** 運勢コード */
	protected String unseiCode;
	/** 願い事 */
	protected String negaigoto;
	/** 商い */
	protected String akinai;
	/** 学問 */
	protected String gakumon;
	/** 占い日 */
	protected String uranaiDate;

	/**
	 * 運勢の抽象メソッド
	 */
	public abstract void setUnsei();

	/**
	 * 運勢結果の表示内容
	 * @return 運勢結果
	 */
	public String disp() {
		return String.format(DISP_STR, getUnsei());
	}

	/**
	 * 運勢を取得する
	 * @return 運勢
	 */
	public String getUnsei() {
		return unsei;
	}

	/**
	 * おみくじコードを取得する
	 * @return おみくじコード
	 */
	public String getOmikujiCode() {
		return omikujiCode;
	}

	/**
	 * おみくじコードを設定する
	 * @param omikujiCode おみくじコード
	 */
	public void setOmikujiCode(String omikujiCode) {
		this.omikujiCode = omikujiCode;
	}

	/**
	 * 運勢コードを取得する
	 * @return 運勢コード
	 */
	public String getUnseiCode() {
		return unseiCode;
	}

	/**
	 * 運勢コードを設定する
	 * @param unseiCode 運勢コード
	 */
	public void setUnseiCode(String unseiCode) {
		this.unseiCode = unseiCode;
	}

	/**
	 * 願い事を取得する
	 * @return 願い事
	 */
	public String getNegaigoto() {
		return negaigoto;
	}

	/**
	 * 願い事を設定する
	 * @param negaigoto 願い事
	 */
	public void setNegaigoto(String negaigoto) {
		this.negaigoto = negaigoto;
	}

	/**
	 * 商いを取得する
	 * @return 商い
	 */
	public String getAkinai() {
		return akinai;
	}

	/**
	 * 商いを設定する
	 * @param akinai 商い
	 */
	public void setAkinai(String akinai) {
		this.akinai = akinai;
	}

	/**
	 * 学問を取得する
	 * @return 学問
	 */
	public String getGakumon() {
		return gakumon;
	}

	/**
	 * 学問を設定する
	 * @param gakumon 学問
	 */
	public void setGakumon(String gakumon) {
		this.gakumon = gakumon;
	}
	
	/**
	 * 占い日付を取得する
	 * @return 占い日付
	 */
	public String getUranaiDate() {
		return uranaiDate;
	}

	/**
	 * 占い日付を設定する
	 * @param gakumon 占い日付
	 */
	public void setUranaiDate(String uranaiDate) {
		this.uranaiDate = uranaiDate;
	}

}
