package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.WorkDAO;
import dto.Employee;

/**
 *@author Akihiro Nakamura
 *作業項目を追加登録するクラス
 */
@WebServlet("/WorkAdd")
public class WorkAdd extends HttpServlet{
	private static final long serialVersionUID = 1L;

	/**
	*@see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	*@param request クライアントがServletへ要求したリクエスト内容を含むHttpServletRequestオブジェクト
	*@param response Servletがクライアントに返すレスポンス内容を含むHttpServletResponseオブジェクト
	*@throws ServletException ServletException ServletがGetリクエストを処理中にServlet内で例外が発生
	*@throws IOException ServletがGetリクエストを処理中に入出力エラーが発生
	*/
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	*@see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	*@param request クライアントがServletへ要求したリクエスト内容を含むHttpServletRequestオブジェクト
	*@param response Servletがクライアントに返すレスポンス内容を含むHttpServletResponseオブジェクト
	*@throws ServletException ServletがPostリクエストを処理中にServlet内で例外が発生
	*@throws IOException ServletがPostリクエストを処理中に入出力エラーが発生
	*ログインしている社員の部署IDを取得する。<br>
	*データベースに接続して該当部署に記入した作業項目を追加登録する。<br>
	*作業項目追加登録完了画面へ画面偏移する。
	*/
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//セッションオブジェクトの開始
		HttpSession session = request.getSession();

		//ログインした社員の部署IDを取得
		Employee syain = (Employee)session.getAttribute("employee");
		String department_id = syain.getDepartment_Id();

		//追加作業項目を取得
		String task = request.getParameter("task");

		//作業項目追加登録準備
		WorkDAO wd = new WorkDAO();

		//追加登録判定
		boolean addJudge = false;

		try {
			//データベース接続
			wd.dbConnect();

			//作業項目の追加登録
			addJudge = wd.addTask(department_id,task);

		}catch (SQLException e){
			e.printStackTrace();

		}finally{
			try{
				//データベースの切断
				wd.dbClose();

			}catch(SQLException e){
				e.printStackTrace();
			}
		}

		if(addJudge) {
			//追加登録成功
			RequestDispatcher disp = request.getRequestDispatcher("work_add_succeed.jsp");
			disp.forward(request, response);
		}else{
			//追加登録失敗
			RequestDispatcher disp = request.getRequestDispatcher("error.jsp");
			disp.forward(request, response);
		}
	}
}