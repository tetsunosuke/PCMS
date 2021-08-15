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
import dto.Report;

/**
 *@author Akihiro Nakamura
 *社員番号と日付から工数記録を検索するクラス
 */
@WebServlet("/ReportEachSelect")
public class  ReportEachSelect extends HttpServlet{
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
	 *データベースに接続して社員番号、日付から指定した工数記録を取得し、セッションにセットする。<br>
	 *特定の工数記録画面へ画面偏移する。
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//セッションオブジェクトの生成
		HttpSession session = request.getSession();

		//社員番号の取得
		int number = Integer.parseInt(request.getParameter("number"));

		//選択した工数記録の日付を取得
		String day = request.getParameter("day");

		//該当社員工数記録検索準備
		ReportDAO rd = new ReportDAO();
		Report report = new Report();

		try {
			//データベース接続
			rd.dbConnect();

			//該当社員の工数記録を検索
			report = rd.selectReport(number,day);

			//セッションスコープに保存
			session.setAttribute("report",report);

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

		if(report != null){
			//工数編集ボタンを選択
			RequestDispatcher disp = request.getRequestDispatcher("./WorkSelect");
			disp.forward(request, response);
		}else{
			//読み込み失敗
			RequestDispatcher disp = request.getRequestDispatcher("error.jsp");
			disp.forward(request, response);
		}
	}
}
