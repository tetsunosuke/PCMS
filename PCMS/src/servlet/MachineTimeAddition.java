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

import dao.MachineDAO;
import dto.Machine;
import dto.Report;

/**
 *@author Akihiro Nakamura
 *機械毎の工数合計時間を計算するクラス
 */
@WebServlet("/MachineTimeAddition")
public class MachineTimeAddition extends HttpServlet{
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
	*機械名を選択<br>
	*データベースに接続して機械毎の工数合計時間を計算し、セッションにセットする。<br>
	*選択した機械名から関連している該当部署を検索
	*/
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//セッションオブジェクトの開始
		HttpSession session = request.getSession();

		//選択した機械情報の取得
		Machine kikai = (Machine)session.getAttribute("machine");
		//機械名の取得
		String machine_name = kikai.getMachine_Name();

		//該当オブジェクトの生成
		Report report = new Report();
		Machine machine = new Machine();

		//工数クラスに機械名をセット
		report.setMachine_Name(machine_name);
		//機械クラスに機械名をセット
		machine.setMachine_Name(machine_name);

		//機械別工数合計時間の計算準備
		MachineDAO md = new MachineDAO();

		try {
			//データベース接続
			md.dbConnect();

			//機械別工数合計時間の計算
			machine = md.sumMachineHours(report,machine);

			//計算した工数合計時間の取得
			machine = md.getMachineHours(machine);

			//セッションスコープに上書き保存
			session.setAttribute("machine",machine);

		}catch (SQLException e){
			e.printStackTrace();

		}finally{
			try{
				//データベースの切断
				md.dbClose();

			}catch(SQLException e){
				e.printStackTrace();
			}
		}

		if(machine != null) {
			//計算結果
			RequestDispatcher disp = request.getRequestDispatcher("./DepartmentSearch");
			disp.forward(request, response);
		}else{
			//読み込み失敗
			RequestDispatcher disp = request.getRequestDispatcher("error.jsp");
			disp.forward(request, response);
		}
	}
}