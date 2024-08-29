package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import dao.ResultDAO;
import form.RatioForm;

/**
 * 全体の過去半年と本日の占い結果の割合を取得する
 * @author e_kou
 */
public class OmikujiRatioAction extends Action {
	/**
	 * 実行するメソッド
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		//resultDAOオブジェクトの生成
		ResultDAO resultDAO = new ResultDAO();
		//formに保存
		RatioForm ratioForm = resultDAO.findRatio();
		//リクエストスコープに値を保存
		request.setAttribute("RatioForm", ratioForm);

		return mapping.findForward("success");

	}

}
