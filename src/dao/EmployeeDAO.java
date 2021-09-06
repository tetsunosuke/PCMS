package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.Employee;

/**
 *@author Akihiro Nakamura
 *社員テーブルに接続するDAOクラス
 */
public class EmployeeDAO {

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
	 *@throws SQLException データベース接続処理でエラー
	 *データベースと接続するメソッド
	 */
	public void dbConnect() throws SQLException {
        System.out.println("EmployeeDAO.dbConnect()");
		ConnectionManager cm = new ConnectionManager();
		con = cm.connect();
        System.out.println("con(dbConnect)= " + con);
	}

	/**
	 *@throws SQLException データベース切断処理でエラー
	 *データベースとの接続を切断するメソッド
	 */
	public void dbClose() throws SQLException {
        System.out.println("EmployeeDAO.dbClose()");
		ConnectionManager cm = new ConnectionManager();
		con = cm.close();
        System.out.println("con(dbConnect)= " + con);
	}

	/**
	 *リストに格納
	 */
	private List<Employee> elist = new ArrayList<>();

	/**
	 *@param employee_id 社員ID
	 *@param employee_password 社員パスワード
	 *@return ログインした社員情報を返す
	 *@throws SQLException データベース接続処理でエラー
	 *社員ログインメソッド
	 */
	public Employee loginEmployee(int employee_id,String employee_password) throws SQLException{

		//初期化
		Employee employee = null;

		//データベースからログインした社員情報を取得するSQL文
		String sql ="select * from employees where employee_id = ? and employee_password = ?";
		ps = con.prepareStatement(sql);

		//プレースホルダに値をセット
		ps.setInt(1,employee_id);
		ps.setString(2,employee_password);

		//SQL文の実行
		rs = ps.executeQuery();

		if(rs.next()){

			//データベースから取得した値をセット
			 employee = new Employee();

			//社員ID
			employee.setEmployee_Id(rs.getInt("employee_id"));
			//姓
			employee.setLast_Name(rs.getString("last_name"));
			//名
			employee.setFirst_Name(rs.getString("first_name"));
			//姓フリガナ
			employee.setLast_Kana(rs.getString("last_kana"));
			//名フリガナ
			employee.setFirst_Kana(rs.getString("first_kana"));
			//部署名
			employee.setDepartment_Name(rs.getString("department_name"));
			//部署ID
			employee.setDepartment_Id(rs.getString("department_id"));
			//血液型
			employee.setBlood(rs.getString("blood"));
			//年齢
			employee.setAge(rs.getInt("age"));
			//生年月日
			employee.setBirthday(rs.getString("birthday"));
			//性別
			employee.setGender(rs.getString("gender"));
			//パスワード
			employee.setEmployee_Password(rs.getString("employee_password"));
		}
		return employee;
	}

	/**
	 *@return 新規社員IDを返す
	 *@throws SQLException データベース接続処理でエラー
	 *新規登録画面に社員IDを自動表示するメソッド
	 */
	public Employee IdAutoDisplay() throws SQLException{

		//初期化
		Employee employee = null;

		//データべ―スに新規社員IDを登録するSQL文
		String sql = "select max(employee_id) from employees";
		ps = con.prepareStatement(sql);

		//SQL文の実行
		rs = ps.executeQuery();

		//社員ID新規登録
		if(rs.next()){
			employee = new Employee();
			employee.setEmployee_Id(rs.getInt(1) + 1);
		}
		return employee;
	}

	/**
	 *@param employee_id 社員ID
	 *@param last_name 姓
	 *@param first_name 名
	 *@param last_kana 姓カナ
	 *@param first_kana 名カナ
	 *@param department_name 部署名
	 *@param blood 血液型
	 *@param employee_password 社員パスワード
	 *@return 新規社員登録出来たらtrue,出来なかったらfalse
	 *@throws SQLException  データベース接続処理でエラー
	 *新規社員登録メソッド
	 */
	public boolean registEmployee(int employee_id,String last_name,String first_name,String last_kana,String first_kana,String department_name,String blood,String employee_password) throws SQLException{

		//オートコミットの無効
		con.setAutoCommit(false);

		//オブジェクトの生成
		Employee employee = new Employee();

		//データベースに部署IDを登録するSQL文
		String sql1 = "select department_id from departments where department_name = ?";
		ps = con.prepareStatement(sql1);

		//部署名
		ps.setString(1,department_name);

		//SQL文の実行
		rs = ps.executeQuery();

		//部署ID登録
		if(rs.next()){
			employee.setDepartment_Id(rs.getString("department_id"));
		}

		//社員登録判定
		boolean registJudge = false;

		//データベースに新規社員登録するSQL文
		String sql2 = "insert into employees (employee_id,last_name,first_name,last_kana,first_kana,department_name,department_id,blood,employee_password) values (?,?,?,?,?,?,?,?,?)";
		ps = con.prepareStatement(sql2);

		//プレースホルダに値をセット
		//社員ID
		ps.setInt(1,employee_id);
		//姓
		ps.setString(2,last_name);
		//名
		ps.setString(3,first_name);
		//姓フリガナ
		ps.setString(4,last_kana);
		//名フリガナ
		ps.setString(5,first_kana);
		//部署名
		ps.setString(6,department_name);
		//部署ID
		ps.setString(7,employee.getDepartment_Id());
		//血液型
		ps.setString(8,blood);
		//パスワード
		ps.setString(9,employee_password);

		//SQL文の実行
		int re = ps.executeUpdate();

		//登録成功したらコミットする
		if(re > 0){
			registJudge = true;
			con.commit();
		}
		return registJudge;
	}

	/**
	 *@param employee 社員情報
	 *@return 社員情報を編集し、更新した社員情報を返す
	 *@throws SQLException データベース接続処理でエラー
	 *社員情報を更新するメソッド
	 */
	public Employee updateEmployee(Employee employee) throws SQLException{

		//初期化
		employee = null;

		//オートコミットの無効
		con.setAutoCommit(false);

		//データベースの社員情報を更新するSQL文
		String sql = "update employees set last_name = ?,first_name = ?,last_kana = ?,first_kana = ?,department_name = ?,blood = ?,age = ?,gender = ?,birthday = ? where employee_id = ?";
		ps = con.prepareStatement(sql);

		//プレースホルダに値をセット
		//姓
		ps.setString(1,employee.getLast_Name());
		//名
		ps.setString(2,employee.getFirst_Name());
		//姓フリガナ
		ps.setString(3,employee.getLast_Kana());
		//名フリガナ
		ps.setString(4,employee.getFirst_Kana());
		//部署名
		ps.setString(5,employee.getDepartment_Name());
		//血液型
		ps.setString(6,employee.getBlood());
		//年齢
		ps.setInt(7,employee.getAge());
		//性別
		ps.setString(8,employee.getGender());
		//生年月日
		ps.setString(9,employee.getBirthday());
		//社員番号
		ps.setInt(10,employee.getEmployee_Id());

		//SQL文の実行
		int ue = ps.executeUpdate();

		//更新したらコミットする
		if(ue > 0){
			con.commit();
		}
		return employee;
	}

	/**
	 *@param employee_id 社員ID
	 *@return 選択した社員を削除出来たらtrue、出来なかったらfalse
	 *@throws SQLException データベース接続処理でエラー
	 *社員を削除するメソッド
	 */
	public boolean deleteEmployee(int employee_id) throws SQLException{

		//オートコミットの無効
		con.setAutoCommit(false);

		//データベースから社員を削除するSQL文
		String sql = "delete from employees where employee_id = ?";
		ps = con.prepareStatement(sql);

		//削除判定
		boolean deleteJudge = false;

		//プレースホルダに値をセット
		//社員ID
		ps.setInt(1,employee_id);

		//SQL文の実行
		int de = ps.executeUpdate();

		//削除したらコミットする
		if(de > 0){
			deleteJudge = true;
			con.commit();
		}
	    return deleteJudge;
	}

	/**
	 *@param employee_id 社員ID
	 *@return 選択した社員情報を返す
	 *@throws SQLException データベース接続処理でエラー
	 *選択した社員情報を探すメソッド
	 */
	public Employee selectEmployee(int employee_id) throws SQLException{

		//初期化
		Employee employee = null;

		//データベースから社員情報を取得するSQL文
		String sql ="select * from employees where employee_id = ?";
		ps = con.prepareStatement(sql);

		//プレースホルダに値をセット
		ps.setInt(1,employee_id);

		//SQL文の実行
		rs = ps.executeQuery();

		if(rs.next()){

			//データベースから取得した値をセット
			employee = new Employee();

			//社員ID
			employee.setEmployee_Id(rs.getInt("employee_id"));
			//姓
			employee.setLast_Name(rs.getString("last_name"));
			//名
			employee.setFirst_Name(rs.getString("first_name"));
			//姓フリガナ
			employee.setLast_Kana(rs.getString("last_kana"));
			//名フリガナ
			employee.setFirst_Kana(rs.getString("first_kana"));
			//部署名
			employee.setDepartment_Name(rs.getString("department_name"));
			//部署ID
			employee.setDepartment_Id(rs.getString("department_id"));
			//血液型
			employee.setBlood(rs.getString("blood"));
			//年齢
			employee.setAge(rs.getInt("age"));
			//生年月日
			employee.setBirthday(rs.getString("birthday"));
			//性別
			employee.setGender(rs.getString("gender"));
			//パスワード
			employee.setEmployee_Password(rs.getString("employee_password"));
		}
		return employee;
	}

	/**
	 *@return List<Employee> 社員一覧
	 *@throws SQLException データベース接続処理でエラー
	 *全社員一覧表示するメソッド
	 */
	public List<Employee> showAllList() throws SQLException{

		//初期化
		Employee employee = null;

		//データベースから全社員情報を取得するSQL文
		String sql ="select employee_id,last_name,first_name,last_kana,first_kana,department_name,blood,age,birthday,gender from employees order by employee_id";
		ps = con.prepareStatement(sql);

		//SQL文の実行
		rs = ps.executeQuery();

		while(rs.next()){

			//データベースから取得した値をセット
			employee = new Employee();

			//社員ID
			employee.setEmployee_Id(rs.getInt("employee_id"));
			//姓
			employee.setLast_Name(rs.getString("last_name"));
			//名
			employee.setFirst_Name(rs.getString("first_name"));
			//姓フリガナ
			employee.setLast_Kana(rs.getString("last_kana"));
			//名フリガナ
			employee.setFirst_Kana(rs.getString("first_kana"));
			//部署名
			employee.setDepartment_Name(rs.getString("department_name"));
			//血液型
			employee.setBlood(rs.getString("blood"));
			//年齢
			employee.setAge(rs.getInt("age"));
			//生年月日
			employee.setBirthday(rs.getString("birthday"));
			//性別
			employee.setGender(rs.getString("gender"));

			elist.add(employee);
		}
		return elist;
	}

	/**
	 *@param employee_password 社員パスワード
	 *@param employee_id 社員ID
	 *@return パスワード変更出来たらtrue,出来なかったらfalse
	 *@throws SQLException データベース接続処理でエラー
	 *社員パスワード変更メソッド
	 */
	public boolean changeEmployeePassword(String employee_password,int employee_id) throws SQLException{

		//オートコミットの無効
		con.setAutoCommit(false);

		//パスワード変更判定
		boolean changeJudge = false;

		//データベースのパスワードを変更するSQL文
		String sql ="update employees set employee_password = ? where employee_id = ?";
		ps = con.prepareStatement(sql);

		//プレースホルダに値をセット
		ps.setString(1,employee_password);
		ps.setInt(2,employee_id);

		//SQL文の実行
		int cp = ps.executeUpdate();

		//変更したらコミットする
		if(cp > 0){
			changeJudge = true;
			con.commit();
		}
		return changeJudge;
	}

	/**
	 *@return ゲストログインした社員情報を返す
	 *@throws SQLException データベース接続処理でエラー
	 *ゲストログインメソッド
	 */
	public Employee loginGuest() throws SQLException{

		//初期化
		Employee employee = null;

		//データベースからゲスト情報を取得するSQL文
		String sql ="select * from employees where employee_id = 0";
		ps = con.prepareStatement(sql);

		//SQL文の実行
		rs = ps.executeQuery();

		if(rs.next()){

			//データベースから取得した値をセット
			employee = new Employee();

			//社員ID
			employee.setEmployee_Id(rs.getInt("employee_id"));
			//姓
			employee.setLast_Name(rs.getString("last_name"));
			//名
			employee.setFirst_Name(rs.getString("first_name"));
			//姓フリガナ
			employee.setLast_Kana(rs.getString("last_kana"));
			//名フリガナ
			employee.setFirst_Kana(rs.getString("first_kana"));
			//部署名
			employee.setDepartment_Name(rs.getString("department_name"));
			//部署コード
			employee.setDepartment_Id(rs.getString("department_id"));
			//血液型
			employee.setBlood(rs.getString("blood"));
			//年齢
			employee.setAge(rs.getInt("age"));
			//生年月日
			employee.setBirthday(rs.getString("birthday"));
			//性別
			employee.setGender(rs.getString("gender"));
			//パスワード
			employee.setEmployee_Password(rs.getString("employee_password"));
		}
		return employee;
	}
}
