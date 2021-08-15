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

import dao.MachineDAO;
import dto.Machine;

/**
 *@author Akihiro Nakamura
 *機械一覧表示クラス
 */
@WebServlet("/MachineListDisplay")
public class MachineListDisplay extends HttpServlet{
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
	*データベースに接続して機械一覧を取得し、セッションにセットする。<br>
	*機械名を一覧表示する。
	*/
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//セッションオブジェクトの生成
		HttpSession session = request.getSession();

		//機械一覧表示準備
		MachineDAO md = new MachineDAO();
		List<Machine> mlist = new ArrayList<>();

		try {
			//データベース接続
			md.dbConnect();

			//機械一覧表示
			mlist = md.showAllMachine();

			//セッションスコープに保存
        	session.setAttribute("mlist",mlist);

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

		//各機械工数ボタンのname属性を取得
		String mh = request.getParameter("machine_hours");

		//機械編集ボタンのname属性を取得
		String em = request.getParameter("edit_machine");

		//工数記録編集ボタンのname属性を取得
		String er = request.getParameter("edit_report");


		if(mlist != null && mh != null && em == null && er == null){
			//工数合計時間抽出成功
			RequestDispatcher disp = request.getRequestDispatcher("machine_list.jsp");
			disp.forward(request, response);
		}else if(mlist != null && mh == null && em != null && er == null){
			//機械編集ボタンを選択
			RequestDispatcher disp = request.getRequestDispatcher("edit_machine.jsp");
			disp.forward(request, response);
		}else if(mlist != null && mh == null && em == null && er == null){
			//ログイン、ゲストログインボタンを選択
			RequestDispatcher disp = request.getRequestDispatcher("work_report.jsp");
			disp.forward(request, response);
		}else if(mlist != null && mh == null && em == null && er != null){
			//工数記録編集ボタンを選択
			RequestDispatcher disp = request.getRequestDispatcher("edit_report.jsp");
			disp.forward(request, response);
		}else{
			//読み込み失敗
			RequestDispatcher disp = request.getRequestDispatcher("error.jsp");
			disp.forward(request, response);
		}
	}
}