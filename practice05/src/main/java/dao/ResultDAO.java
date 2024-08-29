package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import constant.CommonEnum;
import constant.MsgEnum;
import form.HalfYearResultForm;
import form.RatioForm;
import omikuji.Chukichi;
import omikuji.Daikichi;
import omikuji.Kichi;
import omikuji.Kyo;
import omikuji.Omikuji;
import omikuji.Shokichi;
import omikuji.Suekichi;

/**
 * 占い結果TBL
 * 占い結果テーブルへの検索、登録
 * @author e_kou
 */
public class ResultDAO {

	/** おみくじ結果検索 **/
	final String SQL_FIND_RESULT = "SELECT omikuji_code FROM result WHERE uranai_date = ? and birthday = ?;";
	/** おみくじ結果登録 **/
	final String SQL_INSERT_RESULT = "INSERT INTO result(uranai_date,birthday,omikuji_code,register_datetime,register_user,update_datetime,update_user)"
			+ "values(?,?,?,CURRENT_TIMESTAMP,'e_kou',CURRENT_TIMESTAMP,'e_kou');";
	/** 過去半年の割合検索 **/
	final String SQL_FIND_HALFYEAR_RATIO = "SELECT unsei_mst.unsei_code as unseiCode, unsei_name, ROUND(CAST(CAST(COUNT(result.omikuji_code) AS numeric) / (SELECT COUNT(omikuji_code) FROM result) * 100 AS numeric),2) as ratio "
			+ "FROM unsei_mst LEFT JOIN omikuji  ON unsei_mst.unsei_code = omikuji.unsei_code LEFT JOIN result ON omikuji.omikuji_code = result.omikuji_code AND result.uranai_date >= now() - interval '6 months' GROUP BY unseiCode,unsei_mst.unsei_name ORDER BY unseiCode ASC;";
	/** 今日の割合検索 **/
	final String SQL_FIND_TODAY_RATIO = "SELECT unsei_mst.unsei_code as unseiCode, unsei_name, ROUND(CAST(CAST(COUNT(result.omikuji_code) AS numeric) / (SELECT COUNT(omikuji_code) FROM result WHERE uranai_date = CURRENT_DATE) * 100 AS numeric),2) as ratio "
			+ "FROM unsei_mst LEFT JOIN omikuji  ON unsei_mst.unsei_code = omikuji.unsei_code LEFT JOIN result ON omikuji.omikuji_code = result.omikuji_code AND result.uranai_date = CURRENT_DATE GROUP BY unseiCode,unsei_mst.unsei_name ORDER BY unseiCode ASC;";
	/** 過去半年の結果検索 **/
	final String SQL_FIND_HALFYEAR_RESULT = "SELECT uranai_date,omikuji.omikuji_code,unsei_name,negaigoto,akinai,gakumon FROM result JOIN omikuji ON omikuji.omikuji_code = result.omikuji_code JOIN unsei_mst ON unsei_mst.unsei_code = omikuji.unsei_code "
			+ "WHERE result.uranai_date >= now() - interval '6 months' AND birthday = ? ORDER BY uranai_date DESC;";

	//DBに接続するための宣言
	Connection con = null;

	//PreparedStatemenrの準備
	PreparedStatement preparedStatement = null;

	//ResultSetの準備
	ResultSet resultSet = null;

	/**
	 * 占い結果を検索する
	 * @param today 占い日付
	 * @param birthday 誕生日
	 * @return omikujiCode 検索したおみくじコード
	 */
	public String findResult(Date today, Date birthday) {
		String omikujiCode = null;
		try {
			//JDBCドライバクラスをJVMに登録
			Class.forName(CommonEnum.DRIVER.getConstant());
			//データベースへ接続
			con = DriverManager.getConnection(CommonEnum.URL.getConstant(), CommonEnum.USER.getConstant(),
					CommonEnum.PASS.getConstant());
			//接続に成功した場合に表示
			if (con != null) {
				System.out.println(MsgEnum.I0002.getMessage() + ":findResult");
			}

			PreparedStatement resultInfo = con.prepareStatement(SQL_FIND_RESULT);
			resultInfo.setDate(1, toSqlDate(today));
			resultInfo.setDate(2, toSqlDate(birthday));
			ResultSet resultRs = resultInfo.executeQuery();
			while (resultRs.next()) {
				// おみくじコード取得
				omikujiCode = resultRs.getString("omikuji_code");
			}

		} catch (SQLException se) {
			//DB接続エラー
			System.out.println(MsgEnum.E0003.getMessage());
			se.printStackTrace();

		} catch (ClassNotFoundException ce) {
			//ドライバクラスが見つからなかった場合
			System.out.println(MsgEnum.E0006.getMessage());
			ce.printStackTrace();

		} finally {
			//DBとの接続を切断
			disconnect();
			System.out.println(MsgEnum.I0004.getMessage() + "findResult");
		}
		return omikujiCode;

	}

	/**
	 * 占い結果が登録する
	 * @param today 占い日付
	 * @param birthday 誕生日
	 * @param omikujiCode おみくじコード
	 * @return omikujiCode 検索したおみくじコード
	 */
	public void insertResult(Date today, Date birthday, String omikujiCode) {
		try {
			//JDBCドライバクラスをJVMに登録
			Class.forName(CommonEnum.DRIVER.getConstant());
			//データベースへ接続
			con = DriverManager.getConnection(CommonEnum.URL.getConstant(), CommonEnum.USER.getConstant(),
					CommonEnum.PASS.getConstant());
			//接続に成功した場合に表示
			if (con != null) {
				System.out.println(MsgEnum.I0002.getMessage() + ":insertResult");
			}

			PreparedStatement insertResult = con.prepareStatement(SQL_INSERT_RESULT);
			insertResult.setDate(1, toSqlDate(today));
			insertResult.setDate(2, toSqlDate(birthday));
			insertResult.setString(3, omikujiCode);
			insertResult.executeUpdate();

		} catch (SQLException se) {
			//DB接続エラー
			System.out.println(MsgEnum.E0003.getMessage());
			se.printStackTrace();

		} catch (ClassNotFoundException ce) {
			//ドライバクラスが見つからなかった場合
			System.out.println(MsgEnum.E0006.getMessage());
			ce.printStackTrace();

		} finally {
			//DBとの接続を切断
			disconnect();
			System.out.println(MsgEnum.I0004.getMessage() + "insertResult");
		}
	}

	/**
	 * 全体の過去半年と本日の占い結果の割合を取得する
	 * @return omikujiCode 検索したおみくじコード
	 */
	public RatioForm findRatio() {
		RatioForm ratioForm = new RatioForm();
		LinkedHashMap<String, Double> halfYearRatio = new LinkedHashMap<>();
		LinkedHashMap<String, Double> todayRatio = new LinkedHashMap<>();
		try {
			//JDBCドライバクラスをJVMに登録
			Class.forName(CommonEnum.DRIVER.getConstant());
			//データベースへ接続
			con = DriverManager.getConnection(CommonEnum.URL.getConstant(), CommonEnum.USER.getConstant(),
					CommonEnum.PASS.getConstant());
			//接続に成功した場合に表示
			if (con != null) {
				System.out.println(MsgEnum.I0002.getMessage());
			}
			//過去半年の割合結果を取得
			PreparedStatement findHalfYearRatio = con.prepareStatement(SQL_FIND_HALFYEAR_RATIO);
			ResultSet halfYearRatioInfo = findHalfYearRatio.executeQuery();
			while (halfYearRatioInfo.next()) {
				halfYearRatio.put(halfYearRatioInfo.getString("unsei_name"), halfYearRatioInfo.getDouble("ratio"));
			}
			ratioForm.setHalfYearRatio(halfYearRatio);

			//本日の割合結果を取得
			PreparedStatement findTodayRatio = con.prepareStatement(SQL_FIND_TODAY_RATIO);
			ResultSet todayRatioInfo = findTodayRatio.executeQuery();
			while (todayRatioInfo.next()) {
				todayRatio.put(todayRatioInfo.getString("unsei_name"), todayRatioInfo.getDouble("ratio"));
			}
			ratioForm.setTodayRatio(todayRatio);

		} catch (SQLException se) {
			//DB接続エラー
			System.out.println(MsgEnum.E0003.getMessage());
			se.printStackTrace();

		} catch (ClassNotFoundException ce) {
			//ドライバクラスが見つからなかった場合
			System.out.println(MsgEnum.E0006.getMessage());
			ce.printStackTrace();

		} finally {
			//DBとの接続を切断
			disconnect();
			System.out.println(MsgEnum.I0004.getMessage() + "findRatio");
		}
		return ratioForm;
	}

	/**
	 * 全体の過去半年と本日の占い結果の割合を取得する
	 * @return omikujiCode 検索したおみくじコード
	 */
	public HalfYearResultForm findHalfYearResult(String birthday) {
		HalfYearResultForm halfYearResultForm = new HalfYearResultForm();
		List<Omikuji> halfYearResult = new ArrayList<>();
		Omikuji omikuji = null;
		try {
			//JDBCドライバクラスをJVMに登録
			Class.forName(CommonEnum.DRIVER.getConstant());
			//データベースへ接続
			con = DriverManager.getConnection(CommonEnum.URL.getConstant(), CommonEnum.USER.getConstant(),
					CommonEnum.PASS.getConstant());
			//接続に成功した場合に表示
			if (con != null) {
				System.out.println(MsgEnum.I0002.getMessage() + "findHalfYearResult");
			}

			//過去半年の割合結果を取得
			PreparedStatement findHalfYearResult = con.prepareStatement(SQL_FIND_HALFYEAR_RESULT);
			findHalfYearResult.setDate(1, toSqlDate(toDate(birthday)));
			ResultSet halfYearResultInfo = findHalfYearResult.executeQuery();
			while (halfYearResultInfo.next()) {
				String uranaiDate = halfYearResultInfo.getString("uranai_date");
				String unseiName = halfYearResultInfo.getString("unsei_name");
				omikuji = getOmikuji(unseiName);

				omikuji.setUnsei();
				omikuji.setUranaiDate(uranaiDate);
				omikuji.setNegaigoto(halfYearResultInfo.getString("negaigoto"));
				omikuji.setAkinai(halfYearResultInfo.getString("akinai"));
				omikuji.setGakumon(halfYearResultInfo.getString("gakumon"));
				halfYearResult.add(omikuji);

			}
			halfYearResultForm.setHalfYearResult(halfYearResult);

		} catch (SQLException se) {
			//DB接続エラー
			System.out.println(MsgEnum.E0003.getMessage());
			se.printStackTrace();

		} catch (ClassNotFoundException ce) {
			//ドライバクラスが見つからなかった場合
			System.out.println(MsgEnum.E0006.getMessage());
			ce.printStackTrace();

		} finally {
			//DBとの接続を切断
			disconnect();
			System.out.println(MsgEnum.I0004.getMessage() + "findHalfYearResult");
		}
		return halfYearResultForm;
	}

	/**
	 * 運勢名でオブジェクト生成するメソッド
	 * @param unseiCode 運勢名
	 * @return おみくじオブジェクト
	 */
	private Omikuji getOmikuji(String unseiName) {

		switch (unseiName) {
		case "大吉":
			return new Daikichi();
		case "吉":
			return new Kichi();
		case "中吉":
			return new Chukichi();
		case "小吉":
			return new Shokichi();
		case "末吉":
			return new Suekichi();
		case "凶":
			return new Kyo();
		default:
			//例外を投げる
			throw new IllegalArgumentException(MsgEnum.E0007.getMessage());
		}
	}

	/**
	 * DBとの接続を切断する処理です
	 */
	private void disconnect() {
		try {
			if (resultSet != null) {
				//nullじゃなかったら閉じる
				resultSet.close();
			}
			if (preparedStatement != null) {
				//nullじゃなかったら閉じる
				preparedStatement.close();
			}
			if (con != null) {
				//nullじゃなかったら閉じる
				con.close();
			}
		} catch (SQLException se) {
			System.out.println(MsgEnum.E0008.getMessage());
			se.printStackTrace();
		}
	}

	/**
	 * DateをsqlDateに変換する
	 * @param value 変換対象
	 * @return 変換後の日付
	 */
	private java.sql.Date toSqlDate(Date date) {
		long day = date.getTime();
		java.sql.Date sqlDate = new java.sql.Date(day);
		return sqlDate;
	}

	/**
	 * 文字列をDateに変換する
	 * @param value 変換対象
	 * @return 変換後の日付
	 */
	private Date toDate(String value) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		try {
			date = sdf.parse(value);
		} catch (ParseException e) {
			System.out.println(MsgEnum.E0009.getMessage());
			e.printStackTrace();
		}
		return date;
	}
}
