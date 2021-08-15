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

import dao.EmployeeDAO;
import dto.Employee;

/**
 *@author Akihiro Nakamura
 *新規社員登録クラス
 */
@WebServlet("/EmployeeRegister")
public class EmployeeRegister extends HttpServlet{
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
	*データベースに接続して新規社員を登録する。<br>
	*新規社員登録完了画面に画面偏移する。
	*/
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		//セッションオブジェクトの生成
		HttpSession session = request.getSession();

		//自動表示した社員番号を取得
		Employee syain = (Employee)session.getAttribute("employee");
		//社員番号
		int number = syain.getNumber();

		//新規社員登録画面から社員情報を取得
		//姓
		String lastName = request.getParameter("lastName");
		//名
		String firstName = request.getParameter("firstName");
		//姓フリガナ
		String lastKana = request.getParameter("lastKana");
		//名フリガナ
		String firstKana = request.getParameter("firstKana");
		//部署名
		String department_name = request.getParameter("department_name");
		//血液型
		String blood = request.getParameter("blood");
		//パスワード
		String employee_password = request.getParameter("employee_password");

		//新規社員登録準備
		EmployeeDAO ed = new EmployeeDAO();

		//新規社員登録判定
		boolean registJudge = false;

		try{
			//データベース接続
			ed.dbConnect();

			//新規社員情報をデータベースに登録
			registJudge = ed.registEmployee(number,lastName,firstName,lastKana,firstKana,department_name,blood,employee_password);

		}catch (SQLException e){
			e.printStackTrace();

		}finally{
			try{
				//データベースの切断
				ed.dbClose();

			}catch(SQLException e){
				e.printStackTrace();
			}
		}

		if(registJudge) {
			//登録成功
			RequestDispatcher disp = request.getRequestDispatcher("employee_regist_succeed.jsp");
			disp.forward(request, response);
		}else{
			//登録失敗
			RequestDispatcher disp = request.getRequestDispatcher("employee_regist_error.jsp");
			disp.forward(request, response);
		}
	}
}
