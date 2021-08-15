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
import dto.Machine;

/**
 *@author Akihiro Nakamura
 *選択した機械名から該当部署を検索するクラス
 */
@WebServlet("/DepartmentSearch")
public class DepartmentSearch extends HttpServlet{
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
	*選択した機械名から関連している部署をデータベースに接続して検索<br>
	*該当部署をセッションにセットする。<br>
	*選択した機械の工数記録詳細画面へ画面偏移する。
	*/
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//セッションオブジェクトの生成
		HttpSession session = request.getSession();

		//選択した機械情報の取得
		Machine kikai = (Machine)session.getAttribute("machine");
		//選択した機械名を取得
		String machine_name = kikai.getMachine_Name();

		//部署検索準備
		DepartmentDAO dd = new DepartmentDAO();
		List<Department> dlist = new ArrayList<>();

		try {
			//データベース接続
			dd.dbConnect();

			//選択した機械名に関連した部署を検索
			dlist = dd.getDepartment(machine_name);

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

		if(dlist != null){
			//部署検索結果
			RequestDispatcher disp = request.getRequestDispatcher("machine_each_time.jsp");
			disp.forward(request, response);
		}else{
			//読み込み失敗
			RequestDispatcher disp = request.getRequestDispatcher("error.jsp");
			disp.forward(request, response);
		}
	}
}