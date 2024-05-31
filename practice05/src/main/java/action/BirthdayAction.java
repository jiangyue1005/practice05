package action;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import form.BirthdayForm;
import omikuji.Omikuji;

public class BirthdayAction extends Action {
	/**
	 * 実行するメソッドです
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		//誕生日を取得
		BirthdayForm birthdayForm = (BirthdayForm) form;
		String birthday = birthdayForm.getBirthday();
		//フォマート指定
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		//おみくじの宣言
		Omikuji omikuji = null;

		try {
			// フォーマット設定
			sdf.setLenient(false);
			//フォームに入力した日付をDate型に変換
			Date inputBirthday = sdf.parse(birthday);

			//入力した日付をフォーマット
			birthday = sdf.format(inputBirthday);

			//birthdayをsessionから取得する準備
			HttpSession session = request.getSession();

			//セッションスコープに入力した値（誕生日）を保存
			session.setAttribute("birthday", birthday);

		} catch (Exception e) {

		}
		//おみくじの結果を/jsp/result.jspに渡す
		request.setAttribute("omikuji", omikuji);

		return mapping.findForward("success");
	}

}
