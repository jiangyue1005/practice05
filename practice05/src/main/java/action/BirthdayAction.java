package action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import constant.MsgEnum;
import dao.OmikujiDAO;
import dao.ResultDAO;
import form.BirthdayForm;
import omikuji.Omikuji;

/**
 * 誕生日でおみくじの結果を取得する
 * @author e_kou
 */
public class BirthdayAction extends Action {

	/**
	 * 実行するメソッド
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		//誕生日を取得
		BirthdayForm birthdayForm = (BirthdayForm) form;
		String birthday = birthdayForm.getBirthday();
		System.out.println(MsgEnum.I0001.getMessage() + birthday);

		//おみくじの宣言
		Omikuji omikuji = null;
		//セッション
		HttpSession session = request.getSession();

		try {
			//誕生日
			Date inputBirthday = toDate(birthday);
			//本日の日付
			Date today = new Date();

			//セッションに保存
			session.setAttribute("birthday", toString(inputBirthday));
			session.setAttribute("today", toString(today));

			//結果TBLに同じ結果が存在するした場合、おみくじコードを取得
			//OmikujiDAOのオブジェクト生成
			ResultDAO resultDAO = new ResultDAO();
			String omikujiCode = resultDAO.findResult(today, inputBirthday);

			//OmikujiDAOのオブジェクト生成
			OmikujiDAO omikujiDao = new OmikujiDAO();
			//おみくじを引く
			omikuji = omikujiDao.findOmikuji(omikujiCode);

			//おみくじを結果TBLに登録
			if (omikuji != null) {
				//本日初占いの場合登録
				if (omikujiCode == null || !omikuji.getOmikujiCode().equals(omikujiCode)) {
					resultDAO.insertResult(today, inputBirthday, omikuji.getOmikujiCode());
				}
			} else {
				return mapping.findForward("fail");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(MsgEnum.E0001);
			return mapping.findForward("fail");
		}
		// セッションに保存
		session.setAttribute("omikuji", omikuji);
		//おみくじの結果を結果画面に渡す
		request.setAttribute("omikuji", omikuji);

		return mapping.findForward("success");
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

	/**
	 * 日付を文字列に変換する
	 * @param date 変換対象
	 * @return 変換後の文字列
	 */
	private String toString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String str = sdf.format(date);
		return str;
	}
}
