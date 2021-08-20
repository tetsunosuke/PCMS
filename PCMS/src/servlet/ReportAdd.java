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

import dao.ReportDAO;
import dto.Employee;

/**
 *@author Akihiro Nakamura
 *工数記録クラス
 */
@WebServlet("/ReportAdd")
public class ReportAdd extends HttpServlet{
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
	*データベースに接続して入力した工数を追加記録する。<br>
	*工数記録完了画面へ画面偏移する。
	*/
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		//セッションオブジェクトの開始
		HttpSession session = request.getSession();

		//ログイン中の社員情報を取得
		Employee syain = (Employee)session.getAttribute("employee");
		//社員ID
		int employee_id = syain.getEmployee_Id();
		//姓
		String last_name = syain.getLast_Name();
		//名
		String first_name = syain.getFirst_Name();
		//部署ID
		String department_id = syain.getDepartment_Id();

		//工数記録画面から入力情報を取得
		//日付
		String day = request.getParameter("day");
		//機械名
		String machine_name = request.getParameter("machine_name");
		//作業項目
		String task = request.getParameter("task");
		//実働時間
		int work_time = Integer.parseInt(request.getParameter("work_time"));
		//残業時間
		int over_time = Integer.parseInt(request.getParameter("over_time"));
		//休日出勤
		int holiday_work = Integer.parseInt(request.getParameter("holiday_work"));
		//コメント
		String comment = request.getParameter("comment");

		//セレクトボックス未選択でエラー
		if(machine_name == "" || task == ""){
			//工数記録保存失敗
			RequestDispatcher disp = request.getRequestDispatcher("selectbox_null_error-2.jsp");
			disp.forward(request, response);
		}

		//工数記録準備
		ReportDAO rd = new ReportDAO();

		//工数記録判定
		boolean recordJudge = false;

		try {
			//データベース接続
			rd.dbConnect();

			//追加工数記録をデータベースに登録
			recordJudge = rd.addReport(employee_id,last_name,first_name,department_id,day,machine_name,task,work_time,over_time,holiday_work,comment);

		}catch (SQLException e){
			e.printStackTrace();

		}finally{
			try{
				//データベースの切断
				rd.dbClose();

			}catch(SQLException e){
				e.printStackTrace();
			}
		}

		if(recordJudge) {
			//工数追加記録の成功
			RequestDispatcher disp = request.getRequestDispatcher("report_add_succeed.jsp");
			disp.forward(request, response);
		}else{
			//保存失敗
			RequestDispatcher disp = request.getRequestDispatcher("report_add_error.jsp");
			disp.forward(request, response);
		}
	}
}
