package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import dao.ResultDAO;
import form.HalfYearResultForm;

/**
 * 過去半年の占い結果を取得する
 * @author e_kou
 */
public class HalfYearResultAction extends Action {

	/**
	 * 実行するメソッド
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//セッション
		HttpSession session = request.getSession();
		String birthday = (String) session.getAttribute("birthday");

		//resultDAOオブジェクトの生成
		ResultDAO resultDAO = new ResultDAO();
		//formに保存
		HalfYearResultForm HalfYearResultForm = resultDAO.findHalfYearResult(birthday);
		//リクエストスコープに値を保存
		request.setAttribute("HalfYearResultForm", HalfYearResultForm);
		request.setAttribute("birthday", birthday);

		return mapping.findForward("success");
	}
}
