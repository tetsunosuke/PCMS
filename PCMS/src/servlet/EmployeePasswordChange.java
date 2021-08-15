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

import check.CheckControlManager;
import dao.EmployeeDAO;
import dto.Employee;

/**
 *@author Akihiro Nakamura
 *社員パスワード変更クラス
 */
@WebServlet("/EmployeePasswordChange")
public class EmployeePasswordChange extends HttpServlet{
	private static final long serialVersionUID = 1L;

	/**
	 *検査対象文字列
	 */
	private String target;

	/**
	 *エラー該当文字列
	 */
	private String error_target;

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
	*新しい社員パスワードをnull,未入力,正規表現でチェックする。<br>
	*問題なければ新しい社員パスワードとして変更する。<br>
	*社員パスワード変更画面へ画面偏移する。
	*/
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		//セッションオブジェクトの生成
		HttpSession session = request.getSession();

		//ログインした社員情報の取得
		Employee syain = (Employee)session.getAttribute("employee");

		//社員番号の取得
		int number = syain.getNumber();

		//社員パスワードの取得
		String employee_password = syain.getEmployee_Password();

		//パスワード変更画面から現在のパスワードを取得
		String currentPassword = request.getParameter("current_password");

		//パスワード変更画面から新しいパスワードを取得
		String newPassword = request.getParameter("new_password");

		//パスワード変更画面から確認用パスワードを取得
		String renewPassword = request.getParameter("renew_password");

		//パスワード検査準備
		CheckControlManager ccm = new CheckControlManager();

		//配列に検査対象パスワードを代入
		String[] password = {employee_password,currentPassword,newPassword,renewPassword};

		//null・未入力判定
		boolean nullEmptyJudge = true;

		//検査対象パスワードを順番に処理
		for(String target:password){

			//null・入力チェック
			nullEmptyJudge = ccm.nullEmpty(target);

			//falseなら処理を抜ける
			if(!nullEmptyJudge) {
				//エラー該当箇所を特定
				error_target = target;
				break;
			}
		}

		//employee_passwordでエラー
		if(error_target == employee_password) {
			RequestDispatcher disp = request.getRequestDispatcher("employee_password_error.jsp");
			disp.forward(request, response);
			return;
		}

		//currentPasswordでエラー
		if(error_target == currentPassword) {
			RequestDispatcher disp = request.getRequestDispatcher("current_password_error.jsp");
			disp.forward(request, response);
			return;
		}

		//newPasswordでエラー
		if(error_target == newPassword) {
			RequestDispatcher disp = request.getRequestDispatcher("new_password_error.jsp");
			disp.forward(request, response);
			return;
		}

		//renewPasswordでエラー
		if(error_target == renewPassword) {
			RequestDispatcher disp = request.getRequestDispatcher("renew_password_error.jsp");
			disp.forward(request, response);
			return;
		}

		//データベース登録パスワードと入力した現在パスワードを照合
		if(!employee_password.equals(currentPassword)){
			RequestDispatcher disp = request.getRequestDispatcher("password_match_error.jsp");
			disp.forward(request, response);
			return;
		}

		//新しいパスワードと確認用パスワードを照合
		if(!newPassword.equals(renewPassword)){
			RequestDispatcher disp = request.getRequestDispatcher("new_password_match_error.jsp");
			disp.forward(request, response);
			return;
		}

		//新しいパスワードの正規表現判定
		boolean regixJudge = true;

		//新しいパスワードを検査対象文字列へ代入
		target = newPassword;

		//新しいパスワードを正規表現でチェック
		regixJudge = ccm.regix(target);

		if(!regixJudge) {
			//正規表現チェックでエラー
			RequestDispatcher disp = request.getRequestDispatcher("employee_password_regix_error.jsp");
			disp.forward(request, response);
			return;
		}

		//パスワード変更判定
		boolean changeJudge = false;

		//パスワード変更登録準備
		EmployeeDAO ed = new EmployeeDAO();

		try {

			//データベース接続
			ed.dbConnect();

			//新しいパスワードに変更
			employee_password = newPassword;

			//データベースに新しいパスワードを変更登録
			changeJudge = ed.changeEmployeePassword(employee_password,number);

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

		if(changeJudge) {
			//パスワード変更
			RequestDispatcher disp = request.getRequestDispatcher("password_change_succeed.jsp");
			disp.forward(request, response);
		}else{
			//パスワード変更失敗
			RequestDispatcher disp = request.getRequestDispatcher("password_change_error.jsp");
			disp.forward(request, response);
		}
	}
}
