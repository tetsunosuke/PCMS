package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.Report;

/**
 *@author Akihiro Nakamura
 *工数テーブルに接続するDAOクラス
 */
public class ReportDAO {

	/**
	 *特定のデータベースとの接続
	 */
	private Connection con;

	/**
	 *SQL文の解析と実行
	 */
	private PreparedStatement ps;

	/**
	 *SQL実行結果の取得
	 */
	private ResultSet rs;

	/**
	 *@throws SQLException
	 *データベースと接続するメソッド
	 */
	public void dbConnect() throws SQLException {
		ConnectionManager cm = new ConnectionManager();
		con = cm.connect();
	}

	/**
	 *@throws SQLException
	 *データベースとの接続を切断するメソッド
	 */
	public void dbClose() throws SQLException {
		ConnectionManager cm = new ConnectionManager();
		con = cm.close();
	}

	/**
	 *リストに格納
	 */
	private List<Report> rlist = new ArrayList<>();

	/**
	 *@param number 社員番号
	 *@return List<Report> 工数記録一覧
	 *@throws SQLException データベース接続処理でエラー
	 *ログインした社員の工数記録を探すメソッド
	 */
	public List<Report> findReport(int number) throws SQLException{

		//データベースからログインした社員の工数記録を取得するSQL文
		String sql ="select * from db_report where number = ?";
		ps = con.prepareStatement(sql);

		//プレースホルダに値をセット
		ps.setInt(1,number);

		//SQL文の実行
		rs = ps.executeQuery();

		while(rs.next()){

			//データベースから取得した値をセット
			Report report = new Report();

			//社員番号
			report.setNumber(rs.getInt("number"));
			//姓
			report.setLastName(rs.getString("lastName"));
			//名
			report.setFirstName(rs.getString("firstName"));
			//部署コード
			report.setDepartment_Code(rs.getString("department_code"));
			//日付
			report.setDay(rs.getString("day"));
			//機械名
			report.setMachine_Name(rs.getString("machine_name"));
			//作業項目
			report.setTask(rs.getString("task"));
			//実働時間
			report.setWork_Time(rs.getInt("work_time"));
			//残業時間
			report.setOver_Time(rs.getInt("over_time"));
			//休日出勤
			report.setHoliday_Work(rs.getInt("holiday_work"));
			//コメント
			report.setComment(rs.getString("comment"));

			rlist.add(report);
		}

		return rlist;
	}

	/**
	 *@param number 社員番号
	 *@param lastName 姓
	 *@param firstName 名
	 *@param department_code 部署コード
	 *@param day 日付
	 *@param machine_name 機械名
	 *@param task 作業項目
	 *@param work_time 実働時間
	 *@param over_time 残業時間
	 *@param holiday_work 休日出勤
	 *@param comment コメント
	 *@return 工数を記録出来たらtrue,出来なかったらfalse
	 *@throws SQLException データベース接続処理でエラー
	 *工数記録メソッド
	 */
	public boolean addReport(int number,String lastName,String firstName,String department_code,String day,String machine_name,String task,int work_time,int over_time,int holiday_work,String comment) throws SQLException{

		//オートコミットの無効
		con.setAutoCommit(false);

		//追加記録判定
		boolean recordJudge = false;

		//データベースに工数を追加記録するSQL文
		String sql = "insert into db_report(number,lastName,firstName,department_code,day,machine_name,task,work_time,over_time,holiday_work,comment) values (?,?,?,?,?,?,?,?,?,?,?)";
		ps = con.prepareStatement(sql);

		//プレースホルダに値をセット
		//社員番号
		ps.setInt(1,number);
		//姓
		ps.setString(2,lastName);
		//名
		ps.setString(3,firstName);
		//部署コード
		ps.setString(4,department_code);
		//日付
		ps.setString(5,day);
		//機械名
		ps.setString(6,machine_name);
		//作業項目
		ps.setString(7,task);
		//実働時間
		ps.setInt(8,work_time);
		//残業時間
		ps.setInt(9,over_time);
		//休日出勤
		ps.setInt(10,holiday_work);
		//コメント
		ps.setString(11,comment);

		//SQL文の実行
		int ar = ps.executeUpdate();

		//記録したらコミットする
		if(ar > 0){
			recordJudge = true;
			con.commit();
		}
		return recordJudge;
	}

	/**
	 *@param report 工数記録
	 *@return 編集し、更新した工数記録を返す
	 *@throws SQLException データベース接続処理でエラー
	 *工数記録を更新するメソッド
	 */
	public Report updateReport(Report report) throws SQLException{

		//オートコミットの無効
		con.setAutoCommit(false);

		//データベースの工数記録を更新するSQL文
		String sql = "update db_report set machine_name = ?,work_time = ?,over_time = ?,holiday_work = ?,task = ? where number = ? and day = ?";
		ps = con.prepareStatement(sql);

		//プレースホルダに値をセット
		//機械名
		ps.setString(1,report.getMachine_Name());
		//実働時間
		ps.setInt(2,report.getWork_Time());
		//残業時間
		ps.setInt(3,report.getOver_Time());
		//休日出勤
		ps.setInt(4,report.getHoliday_Work());
		//作業内容
		ps.setString(5,report.getTask());
		//社員番号
		ps.setInt(6,report.getNumber());
		//日付
		ps.setString(7,report.getDay());

		//SQL文の実行
		int ur = ps.executeUpdate();

		//更新したらコミットする
		if(ur != 0){
			con.commit();
		}
		return report;
	}

	/**
	 *@param number 社員番号
	 *@param day 日付
	 *@return 選択した工数記録を削除出来たらtrue,削除出来なかったらfalse
	 *@throws SQLException データベース接続処理でエラー
	 *選択した工数記録を削除するメソッド
	 */
	public boolean deleteReport(int number,String day) throws SQLException{

		//オートコミットの無効
		con.setAutoCommit(false);

		//データベースから選択した工数記録を削除するSQL文
		String sql = "delete from db_report where number = ? and day = ?";
		ps = con.prepareStatement(sql);

		//削除判定
		boolean deleteJudge = false;

		//プレースホルダに値をセット
		//社員番号
		ps.setInt(1,number);
		//日付
		ps.setString(2,day);

		//SQL文の実行
		int dr = ps.executeUpdate();

		//削除したらコミットする
		if(dr > 0){
			deleteJudge = true;
			con.commit();
		}
		return deleteJudge;
	}

	/**
	 *@param number 社員番号
	 *@param day 日付
	 *@return 選択した工数記録を返す
	 *@throws SQLException データベース接続処理でエラー
	 *選択した工数情報を探すメソッド
	 */
	public Report selectReport(int number,String day) throws SQLException{

		//初期化
		Report report = null;

		//データベースから工数記録を取得するSQL文
		String sql ="select * from db_report where number = ? and day = ?";
		ps = con.prepareStatement(sql);

		//プレースホルダに値をセット
		//社員番号
		ps.setInt(1,number);
		//日付
		ps.setString(2,day);

		//SQL文の実行
		rs = ps.executeQuery();

		if(rs.next()){

			//データベースから取得した値をセット
			report = new Report();

			//社員番号の取得
			report.setNumber(rs.getInt("number"));
			//姓の取得
			report.setLastName(rs.getString("lastName"));
			//名の取得
			report.setFirstName(rs.getString("firstName"));
			//部署コードの取得
			report.setDepartment_Code(rs.getString("department_code"));
			//日付の取得
			report.setDay(rs.getString("day"));
			//機械名の取得
			report.setMachine_Name(rs.getString("machine_name"));
			//作業項目の取得
			report.setTask(rs.getString("task"));
			//実働時間の取得
			report.setWork_Time(rs.getInt("work_time"));
			//残業時間の取得
			report.setOver_Time(rs.getInt("over_time"));
			//休日出勤の取得
			report.setHoliday_Work(rs.getInt("holiday_work"));
			//コメントの取得
			report.setComment(rs.getString("comment"));

		}
		return report;
	}

	/**
	 *@param department_code 部署コード
	 *@param machine_name 機械名
	 *@return List<Report> 工数記録一覧
	 *@throws SQLException データベース接続処理でエラー
	 *所属部署と機械名毎に工数記録を取得するメソッド
	 */
	public List<Report> eachReport(String department_code,String machine_name) throws SQLException{

		//データベースから工数記録を取得するSQL文
		String sql ="select lastName,firstName,day,task,work_time,over_time,holiday_work,comment from db_report where department_code = ? and machine_name = ? order by day";
		ps = con.prepareStatement(sql);

		//プレースホルダに値をセット
		ps.setString(1,department_code);
		ps.setString(2,machine_name);

		//SQL文の実行
		rs = ps.executeQuery();

		while(rs.next()){

			//データベースから取得した値をセット
			Report report = new Report();
			//姓
			report.setLastName(rs.getString(1));
			//名
			report.setFirstName(rs.getString(2));
			//日付
			report.setDay(rs.getString(3));
			//作業内容
			report.setTask(rs.getString(4));
			//実働時間
			report.setWork_Time(rs.getInt(5));
			//残業時間
			report.setOver_Time(rs.getInt(6));
			//休日出勤
			report.setHoliday_Work(rs.getInt(7));
			//コメント
			report.setComment(rs.getString(8));

			rlist.add(report);
		}
		return rlist;
	}

	/**
	 *@param number 社員番号
	 *@return  List<Report> 工数記録一覧
	 *@throws SQLException データベース接続処理でエラー
	 *選択した社員の工数記録を全件表示するメソッド
	 */
	public List<Report> selectAllReport(int number) throws SQLException{

		//初期化
		Report report = null;

		//データベースから選択した社員の工数記録を取得するSQL文
		String sql ="select lastName,firstName,day,machine_name,task,work_time,over_time,holiday_work,comment from db_report where number = ? order by day";
		ps = con.prepareStatement(sql);

		//プレースホルダに値をセット
		ps.setInt(1,number);

		//SQL文の実行
		rs = ps.executeQuery();

		while(rs.next()){

			//データベースから取得した値をセット
			report = new Report();

			//姓
			report.setLastName(rs.getString(1));
			//名
			report.setFirstName(rs.getString(2));
			//日付
			report.setDay(rs.getString(3));
			//機械名
			report.setMachine_Name(rs.getString(4));
			//作業内容
			report.setTask(rs.getString(5));
			//実働時間
			report.setWork_Time(rs.getInt(6));
			//残業時間
			report.setOver_Time(rs.getInt(7));
			//休日出勤
			report.setHoliday_Work(rs.getInt(8));
			//コメント
			report.setComment(rs.getString(9));

			rlist.add(report);
		}
		return rlist;
	}

	/**
	 *@param machine_name 機械名
	 *@return List<Report> 工数記録一覧
	 *@throws SQLException データベース接続処理でエラー
	 *選択した機械名から工数記録を探すメソッド
	 */
	public List<Report> selectMachineReport(String machine_name) throws SQLException{

		//初期化
		Report report = null;

		//データベースから選択した機械名の工数記録を取得するSQL文
		String sql ="select machine_name,work_time,over_time,holiday_work,comment from db_report where machine_name = ?";
		ps = con.prepareStatement(sql);

		//プレースホルダに値をセット
		//機械名
		ps.setString(1,machine_name);

		//SQL文の実行
		rs = ps.executeQuery();

		while(rs.next()){

			//データベースから取得した値をセット
			report = new Report();

			//機械名
			report.setMachine_Name(rs.getString("machine_name"));
			//実働時間
			report.setWork_Time(rs.getInt("work_time"));
			//残業時間
			report.setOver_Time(rs.getInt("over_time"));
			//休日出勤
			report.setHoliday_Work(rs.getInt("holiday_work"));
			//コメント
			report.setComment(rs.getString("comment"));

			rlist.add(report);
		}
		return rlist;
	}

	/**
	 *@return List<Report> 工数記録一覧
	 *@throws SQLException データベース接続処理でエラー
	 *全社員の工数記録を表示するメソッド
	 */
	public List<Report> showAllReport() throws SQLException{

		//初期化
		Report report = null;

		//データベースから全社員の工数記録を取得するSQL文
		String sql ="select number,lastName,firstName,day,machine_name,task,work_time,over_time,holiday_work,comment from db_report order by day";
		ps = con.prepareStatement(sql);

		//SQL文の実行
		rs = ps.executeQuery();

		while(rs.next()){

			//データベースから取得した値をセット
			report = new Report();

			//社員番号
			report.setNumber(rs.getInt("number"));
			//姓
			report.setLastName(rs.getString("lastName"));
			//名
			report.setFirstName(rs.getString("firstName"));
			//日付
			report.setDay(rs.getString("day"));
			//機械名
			report.setMachine_Name(rs.getString("machine_name"));
			//作業内容
			report.setTask(rs.getString("task"));
			//実働時間
			report.setWork_Time(rs.getInt("work_time"));
			//残業時間
			report.setOver_Time(rs.getInt("over_time"));
			//休日出勤
			report.setHoliday_Work(rs.getInt("holiday_work"));
			//コメント
			report.setComment(rs.getString("comment"));

			rlist.add(report);
		}
		return rlist;
	}
}