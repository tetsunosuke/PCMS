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

import dao.ReportDAO;
import dto.Department;
import dto.Report;

/**
 *@author Akihiro Nakamura
 *選択した部署の工数記録を全件取得するクラス
 */
@WebServlet("/ReportSelect")
public class ReportSelect extends HttpServlet{
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
	*@param request クライアントがServletへ要求したリクエスト内容を含むHttpServletRequestオブジェクト
	*@param response Servletがクライアントに返すレスポンス内容を含むHttpServletResponseオブジェクト
	*@throws ServletException ServletがPostリクエストを処理中にServlet内で例外が発生
	*@throws IOException ServletがPostリクエストを処理中に入出力エラーが発生
	*データベースに接続して選択した部署毎の工数記録を全件取得し、セッションにセットする。<br>
	*工数記録画面へ画面偏移する。
	*/
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//セッションオブジェクトの開始
		HttpSession session = request.getSession();

		//選択した部署情報の取得
		Department busho = (Department)session.getAttribute("department");
		//選択した部署コードを取得
		String department_code = busho.getDepartment_Code();
		//選択した機械名を取得
		String machine_name = busho.getMachine_Name();

		//工数記録検索準備
		ReportDAO rd = new ReportDAO();
		List<Report> rlist = new ArrayList<>();

		try {
			//データベース接続
			rd.dbConnect();

			//部署毎の工数記録検索
			rlist = rd.eachReport(department_code,machine_name);

			//セッションスコープに保存
			session.setAttribute("rlist",rlist);

		}catch (SQLException e){
			e.printStackTrace();

		}finally{
			try{
				//データベースの切断
				rd.dbClose();

			}catch(SQLException e){
				e.printStackTrace();
			}

		if(rlist != null) {
			//工数記録検索結果の表示
			RequestDispatcher disp = request.getRequestDispatcher("department_each_time.jsp");
			disp.forward(request, response);
		}else{
			//読み込み失敗
			RequestDispatcher disp = request.getRequestDispatcher("error.jsp");
			disp.forward(request, response);
		}
		}
	}
}