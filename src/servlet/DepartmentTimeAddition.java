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
import dto.Report;

/**
 *@author Akihiro Nakamura
 *部署別工数合計時間を計算するクラス
 */
@WebServlet("/DepartmentTimeAddition")
public class DepartmentTimeAddition extends HttpServlet{
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
	*部署ID、機械名を取得し、部署毎の工数合計時間を計算する。<br>
	*セッションに計算した工数合計時間をセットする。
	*/
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//セッションオブジェクトの開始
		HttpSession session = request.getSession();

		//選択した部署情報を取得
		Department busho = (Department)session.getAttribute("department");
		//部署IDの取得
		String department_id = busho.getDepartment_Id();
		//機械名の取得
		String machine_name = busho.getMachine_Name();

		//該当オブジェクトの生成
		Report report = new Report();
		Department department = new Department();

		//工数クラスに部署コードをセット
		report.setDepartment_Id(department_id);
		//工数クラスに機械名をセット
		report.setMachine_Name(machine_name);
		//部署クラスに部署コードをセット
		department.setDepartment_Id(department_id);
		//部署クラスに機械名をセット
		department.setMachine_Name(machine_name);

		//部署別工数合計時間の計算準備
		DepartmentDAO dd = new DepartmentDAO();

		try {
			//データベース接続
			dd.dbConnect();

			//部署別工数合計時間の計算
			department= dd.sumDepartmentHours(report,department);

			//計算した工数合計時間の取得
			department= dd.getDepartmentHours(department);

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

		if(department != null) {
			//計算結果
			RequestDispatcher disp = request.getRequestDispatcher("./ReportSelect");
			disp.forward(request, response);
		}else{
			//読み込み失敗
			RequestDispatcher disp = request.getRequestDispatcher("error.jsp");
			disp.forward(request, response);
		}
	}
}