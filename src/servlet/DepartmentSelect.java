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
import dto.Department;

/**
 *@author Akihiro Nakamura
 *選択した部署の部署情報を取得するクラス
 */
@WebServlet("/DepartmentSelect")
public class DepartmentSelect extends HttpServlet{
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
	*選択した部署名からデータベースに接続して部署情報を取得し、セッションにセットする。<br>
	*選択した部署名の工数合計時間を計算するサーブレットクラスへ
	*/
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//セッションオブジェクトの生成
		HttpSession session = request.getSession();

		//選択した部署名を取得
		String department_name = request.getParameter("department_name");
		//選択した機械名を取得
		String machine_name = request.getParameter("machine_name");

		//部署検索準備
		DepartmentDAO dd = new DepartmentDAO();
		Department department = new Department();

		try {
			//データベース接続
			dd.dbConnect();

			//選択した部署の部署情報を取得
			department = dd.findDepartment(department_name,machine_name);

			//セッションスコープに保存
			session.setAttribute("department",department);

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

		if(department != null){
			//部署選択結果
			RequestDispatcher disp = request.getRequestDispatcher("./DepartmentTimeAddition");
			disp.forward(request, response);
		}else{
			//読み込み失敗
			RequestDispatcher disp = request.getRequestDispatcher("error.jsp");
			disp.forward(request, response);
		}
	}
}