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

import dao.DepartmentDAO;
import dto.Department;

/**
 *@author Akihiro Nakamura
 *全部署一覧表示クラス
 */
@WebServlet("/DepartmentListDisplay")
public class DepartmentListDisplay extends HttpServlet{
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
	*データベースに接続して全部署を取得し、セッションにセットする。<br>
	*全部署一覧表示画面へ画面偏移する。
	*/
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//セッションオブジェクトの生成
		HttpSession session = request.getSession();

		//全部署一覧表示準備
		DepartmentDAO dd = new DepartmentDAO();
		List<Department> dlist = new ArrayList<>();

		try {
			//データベース接続
			dd.dbConnect();

			//全部署一覧表示
			dlist = dd.showAllDepartment();

			//セッションスコープに保存
			session.setAttribute("dlist",dlist);

		}catch (SQLException e){
			e.printStackTrace();

		}finally{
			try{
				//データベースの切断
				dd.dbClose();

			}catch(SQLException e){
				e.printStackTrace();
			}
		}

		//新規登録ボタンのname属性を取得
		String nr = request.getParameter("new_regist");

		//全部署抽出結果
		if(dlist != null  && nr != null){
			//新規登録ボタンを選択
			RequestDispatcher disp = request.getRequestDispatcher("./EmployeeNumberAutoDisplay");
			disp.forward(request, response);
		}else if(dlist != null  && nr == null){
			//マイページ編集ボタンを選択
			RequestDispatcher disp = request.getRequestDispatcher("edit_mypage.jsp");
			disp.forward(request, response);
		}else{
			//読み込み失敗
			RequestDispatcher disp = request.getRequestDispatcher("error.jsp");
			disp.forward(request, response);
		}
	}
}
