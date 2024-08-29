package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 誕生日入力画面に遷移する
 * @author e_kou
 */
public class ReturnAction extends Action {
	/**
	 * 実行するメソッド
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		//誕生日入力画面に遷移
		return mapping.findForward("success");
	}
}
