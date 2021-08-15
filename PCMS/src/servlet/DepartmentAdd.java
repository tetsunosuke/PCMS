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

import dao.DepartmentDAO;
import dto.Employee;

/**
 *@author Akihiro Nakamura
 *部署テーブルに機械情報を登録するクラス
 */
@WebServlet("/DepartmentAdd")
public class DepartmentAdd extends HttpServlet{
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
	*機械名と機械番号を取得してデータベースに新規登録する。
	*/
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//セッションオブジェクトの開始
		HttpSession session = request.getSession();

		//ログイン中の社員情報を取得
		Employee syain = (Employee)session.getAttribute("employee");
		//社員番号
		int number = syain.getNumber();
		//部署コード
		String department_code = syain.getDepartment_Code();
		//部署名
		String department_name = syain.getDepartment_Name();

		//工数記録画面入力情報を取得
		//追加機械名を取得
		String machine_name = request.getParameter("machine_name");

		//機械名登録準備
		DepartmentDAO dd = new DepartmentDAO();

		//追加登録判定
		boolean addJudge = false;

		//ゲストユーザーの場合、登録不可
		if(number == 0){
			RequestDispatcher disp = request.getRequestDispatcher("not_regist_guest.jsp");
			disp.forward(request, response);
			return;
		}

		try {
			//データベース接続
			dd.dbConnect();

			//機械名の追加登録
			addJudge = dd.addDepartment(department_code,department_name,machine_name);

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

		if(addJudge){
			//部署テーブルに機械名登録の成功
			RequestDispatcher disp = request.getRequestDispatcher("./ReportAdd");
			disp.forward(request, response);
		}else{
			//追加登録失敗
			RequestDispatcher disp = request.getRequestDispatcher("error.jsp");
			disp.forward(request, response);
		}
	}
}
