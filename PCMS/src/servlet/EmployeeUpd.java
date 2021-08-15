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
 *社員情報を更新するクラス
 */
@WebServlet("/EmployeeUpd")
public class EmployeeUpd extends HttpServlet{
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
	*データベースに接続して編集した社員情報を更新する。<br>
	*更新完了画面に画面偏移する。
	*/
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//セッションオブジェクトの生成
		HttpSession session = request.getSession();

		//マイページから編集した社員情報を取得
		//社員番号
		int number = Integer.parseInt(request.getParameter("number"));
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
		//年齢
		int age = Integer.parseInt(request.getParameter("age"));
		//性別
		String gender = request.getParameter("gender");
		//生年月日
		String birthDay = request.getParameter("birthDay");

		//セレクトボックス未選択でエラー
		if(department_name == "" || blood == ""){
			//マイページ更新失敗
			RequestDispatcher disp = request.getRequestDispatcher("selectbox_null_error.jsp");
			disp.forward(request, response);
		}

		//編集した社員情報を社員表クラスにセット
		Employee employee = new Employee();

		//社員番号をセット
		employee.setNumber(number);
		//姓をセット
		employee.setLastName(lastName);
		//名をセット
		employee.setFirstName(firstName);
		//姓フリガナをセット
		employee.setLastKana(lastKana);
		//名フリガナをセット
		employee.setFirstKana(firstKana);
		//部署名をセット
		employee.setDepartment_Name(department_name);
		//血液型をセット
		employee.setBlood(blood);
		//年齢をセット
		employee.setAge(age);
		//生年月日をセット
		employee.setBirthDay(birthDay);
		//性別をセット
		employee.setGender(gender);

		//社員情報更新準備
		EmployeeDAO ed = new EmployeeDAO();

		try {
			//データベース接続
			ed.dbConnect();

			//編集した社員情報をデータベースに更新
			employee = ed.updateEmployee(employee);

			//セッションスコープに保存
			session.setAttribute("employee",employee);

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

		if(employee != null) {
			//更新成功
			RequestDispatcher disp = request.getRequestDispatcher("mypage_update_succeed.jsp");
			disp.forward(request, response);
		}else{
			//更新失敗
			RequestDispatcher disp = request.getRequestDispatcher("mypage_update_error.jsp");
			disp.forward(request, response);
		}
	}
}