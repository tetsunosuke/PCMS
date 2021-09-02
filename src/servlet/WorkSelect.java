package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.WorkDAO;
import dto.Employee;
import dto.Work;

/**
 *@author Akihiro Nakamura
 *所属部署の作業項目を取得するクラス
 */
@WebServlet("/WorkSelect")
public class WorkSelect extends HttpServlet{
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
	*データベースに接続して部署コードから該当の作業項目を取得し、セッションにセットする。<br>
	*作業項目を表示
	*/
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//セッションオブジェクトの開始
		HttpSession session = request.getSession();

		//部署IDを取得
		Employee syain = (Employee)session.getAttribute("employee");
		String department_id = syain.getDepartment_Id();

		//作業項目検索準備
		WorkDAO wd = new WorkDAO();
		List<Work> wlist = new ArrayList<>();

		try {
			//データベース接続
			wd.dbConnect();

			//該当部署の作業項目取得
			wlist = wd.selectTask(department_id);

			//セッションスコープに保存
			session.setAttribute("wlist",wlist);

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

		//工数記録編集ボタンのname属性を取得
		String er = request.getParameter("edit_report");

		//作業項目編集ボタンのname属性を取得
		String ew = request.getParameter("edit_work");

		if(wlist != null && er != null && ew == null){
			//工数編集ボタンを選択
			RequestDispatcher disp = request.getRequestDispatcher("./MachineListDisplay");
			disp.forward(request, response);
		}else if(wlist != null && er == null && ew != null){
			//作業項目編集ボタンを選択
			RequestDispatcher disp = request.getRequestDispatcher("edit_work_list.jsp");
			disp.forward(request, response);
		}else if(wlist != null && er == null && ew == null){
			//ゲストログインボタンを選択
			RequestDispatcher disp = request.getRequestDispatcher("./MachineListDisplay");
			disp.forward(request, response);
		}else{
			//読み込み失敗
			RequestDispatcher disp = request.getRequestDispatcher("error.jsp");
			disp.forward(request, response);
		}
	}
}