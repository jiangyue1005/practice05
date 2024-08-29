package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

import constant.CommonEnum;
import constant.MsgEnum;
import omikuji.Chukichi;
import omikuji.Daikichi;
import omikuji.Kichi;
import omikuji.Kyo;
import omikuji.Omikuji;
import omikuji.Shokichi;
import omikuji.Suekichi;

/**
 * おみくじTBL
 * csvの読み込みとおみくじテーブルへの検索、登録、更新
 * @author e_kou
 */
public class OmikujiDAO {

	/** 件数チェック **/
	final String SQL_OMIKUJI_CNT = "SELECT COUNT(*) FROM omikuji;";
	/** おみくじ情報検索 **/
	final String SQL_FIND_OMIKUJI = "SELECT * FROM omikuji WHERE omikuji_code = ?;";
	/** おみくじ情報削除 **/
	final String SQL_DELETE_OMIKUJI = "DELETE FROM omikuji;";
	/** おみくじ情報登録 **/
	final String SQL_INSERT_OMIKUJI = "INSERT INTO omikuji(omikuji_code,unsei_code,negaigoto,akinai,gakumon,register_datetime,register_user,update_datetime,update_user)"
			+ "values(?,?,?,?,?,CURRENT_TIMESTAMP,'e_kou',CURRENT_TIMESTAMP,'e_kou');";

	//DBに接続するための宣言
	Connection con = null;

	//PreparedStatemenrの準備
	PreparedStatement preparedStatement = null;

	//ResultSetの準備
	ResultSet resultSet = null;

	/**
	 * おみくじTBLを検索するメソッド
	 * 
	 * @return Omikuji おみくじオブジェクト
	 */
	public Omikuji findOmikuji(String omikujiCode) {
		//おみくじオブジェクト
		Omikuji omikuji = null;

		try {
			//JDBCドライバクラスをJVMに登録
			Class.forName(CommonEnum.DRIVER.getConstant());
			//データベースへ接続
			con = DriverManager.getConnection(CommonEnum.URL.getConstant(), CommonEnum.USER.getConstant(),
					CommonEnum.PASS.getConstant());

			//接続に成功した場合に表示
			if (con != null) {
				System.out.println(MsgEnum.I0002.getMessage() + ":findOmikuji");
			}
			int cnt = 0;
			//omikujiTBL件数チェック
			PreparedStatement omikujiCnt = con.prepareStatement(SQL_OMIKUJI_CNT);
			ResultSet omikujiCntRs = omikujiCnt.executeQuery();
			while (omikujiCntRs.next()) {
				// おみくじコード取得
				cnt = omikujiCntRs.getInt(1);
			}
			if (cnt < 50) {
				//元情報削除
				con.prepareStatement(SQL_DELETE_OMIKUJI).executeUpdate();
				boolean success = insertOmikuji();
				if (!success) {
					throw new SQLException(MsgEnum.E0002.getMessage());
				}
				cnt = 50;
			}

			// おみくじコードが存在しない場合、ランダムで生成
			if (omikujiCode == null) {
				omikujiCode = Integer.toString(ThreadLocalRandom.current().nextInt(1, cnt + 1));
			}

			// omikujicdで結果を検索
			PreparedStatement omikujiInfo = con.prepareStatement(SQL_FIND_OMIKUJI);
			omikujiInfo.setString(1, omikujiCode);
			ResultSet omikujiRs = omikujiInfo.executeQuery();
			while (omikujiRs.next()) {
				//おみくじの情報を取得
				omikujiCode = omikujiRs.getString("omikuji_code");
				String unseiCode = omikujiRs.getString("unsei_code");
				String negaigoto = omikujiRs.getString("negaigoto");
				String akinai = omikujiRs.getString("akinai");
				String gakumon = omikujiRs.getString("gakumon");

				//オブジェクト生成
				omikuji = getOmikuji(unseiCode);

				//値をsetする
				omikuji.setUnsei();
				omikuji.setOmikujiCode(omikujiCode);
				omikuji.setNegaigoto(negaigoto);
				omikuji.setAkinai(akinai);
				omikuji.setGakumon(gakumon);
			}
		} catch (SQLException se) {
			//DB関連エラー
			System.out.println(MsgEnum.E0003.getMessage());
			se.printStackTrace();

		} catch (ClassNotFoundException ce) {
			//クラスが見つからなかった時のエラー
			System.out.println(MsgEnum.E0004.getMessage());
			ce.printStackTrace();

		} catch (Exception e) {
			//予想外のエラー
			System.out.println(MsgEnum.E0001.getMessage());
			e.printStackTrace();
		} finally {
			//DBとの接続を切断
			disconnect();
			System.out.println(MsgEnum.I0004.getMessage() + "findOmikuji");
		}
		return omikuji;
	}

	/**
	 * おみくじTBLを登録するメソッド
	 * 
	 * @return success 登録結果
	 */
	public boolean insertOmikuji() {
		//パスの取得
		File file = new File("/Users/jiang/git/practice05/practice05/src/main/webapp/data/Omikuji.csv");
		FileReader fr = null;
		boolean success = false;

		try {
			//JDBCドライバクラスをJVMに登録
			Class.forName(CommonEnum.DRIVER.getConstant());
			//データベースへ接続
			con = DriverManager.getConnection(CommonEnum.URL.getConstant(), CommonEnum.USER.getConstant(),
					CommonEnum.PASS.getConstant());

			//接続に成功した場合に表示
			if (con != null) {
				System.out.println(MsgEnum.I0002.getMessage() + ":insertOmikuji");
			}

			//csvファイル読み取り
			fr = new FileReader(file);
			BufferedReader in = new BufferedReader(fr);
			String data = null;
			//登録
			PreparedStatement omikujiInfo = con.prepareStatement(SQL_INSERT_OMIKUJI);
			//登録件数
			int i = 0;
			// CSVファイルの内容をDBに書き込み
			while ((data = in.readLine()) != null) {
				String[] arr = data.split(",");
				// おみくじコード
				omikujiInfo.setString(1, arr[0]);
				// 運勢コード
				omikujiInfo.setString(2, getUnseiCode(arr[1]));
				// 願い事
				omikujiInfo.setString(3, arr[2]);
				// 商い
				omikujiInfo.setString(4, arr[3]);
				// 学問
				omikujiInfo.setString(5, arr[4]);
				// SQL実行
				i += omikujiInfo.executeUpdate();
			}
			fr.close();
			if (i == 50) {
				System.out.println(MsgEnum.I0003.getMessage());
				success = true;
			}

		} catch (SQLException se) {
			//DB関連エラー
			System.out.println(MsgEnum.E0003.getMessage());
			se.printStackTrace();

		} catch (IOException ie) {
			//csvファイルの処理に失敗した場合
			System.out.println(MsgEnum.E0005.getMessage());
			ie.printStackTrace();

		} catch (ClassNotFoundException ce) {
			//ドライバクラスが見つからなかった場合
			System.out.println(MsgEnum.E0006.getMessage());
			ce.printStackTrace();

		} finally {
			//DBとの接続を切断
			disconnect();
			System.out.println(MsgEnum.I0004.getMessage() + "insertOmikuji");
		}
		return success;
	}

	/**
	 * 運勢名で運勢コードを返却するメソッド
	 * @param unseiName 運勢名
	 * @return　運勢コード
	 */
	private static String getUnseiCode(String unseiName) {

		switch (unseiName) {
		case "大吉":
			return "01";
		case "吉":
			return "02";
		case "中吉":
			return "03";
		case "小吉":
			return "04";
		case "末吉":
			return "05";
		case "凶":
			return "06";
		default:
			//01から06以外だったら例外を投げる
			throw new IllegalArgumentException(MsgEnum.E0007.getMessage());
		}
	}

	/**
	 * 運勢コードでオブジェクト生成するメソッド
	 * @param unseiCode 運勢コード
	 * @return おみくじオブジェクト
	 */
	private Omikuji getOmikuji(String unseiCode) {

		switch (unseiCode) {
		case "01":
			//大吉
			return new Daikichi();
		case "02":
			//吉
			return new Kichi();
		case "03":
			//中吉
			return new Chukichi();
		case "04":
			//小吉
			return new Shokichi();
		case "05":
			//末吉
			return new Suekichi();
		case "06":
			//凶
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
}
