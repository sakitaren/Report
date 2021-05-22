package controllers.reports;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Report;
import utils.DBUtil;



/**
 * Servlet implementation class ReportsDestroyServlet
 */
@WebServlet("/reports/destroy")
public class ReportsDestroyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsDestroyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String _token = request.getParameter("_token");
	     if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();


            // セッションスコープからメッセージのIDを取得して
            // 該当のIDのメッセージ1件のみをデータベースから取得
            Report r = em.find(Report.class, (Integer)(request.getSession().getAttribute("report_id")));


            em.getTransaction().begin();
            em.remove(r);              //データ削除
            em.getTransaction().commit();
            em.close();
            request.getSession().setAttribute("flush", "削除が完了しました。");

            // セッションスコープ上の不要になったデータを削除
            request.getSession().removeAttribute("report_id");
            // toppage/indexページへリダイレクト
            response.sendRedirect(request.getContextPath() + "/reports/index");
        }
    }
}