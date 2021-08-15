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
		ConnectionManager cm = new ConnectionManager();
		con = cm.connect();
	}

	/**
	 *@throws SQLException データベース切断処理でエラー
	 *データベースとの接続を切断するメソッド
	 */
	public void dbClose() throws SQLException {
		ConnectionManager cm = new ConnectionManager();
		con = cm.close();
	}

	/**
	 *リストに格納
	 */
	private List<Employee> elist = new ArrayList<>();

	/**
	 *@param number 社員番号
	 *@param employee_password 社員パスワード
	 *@return ログインした社員情報を返す
	 *@throws SQLException データベース接続処理でエラー
	 *社員ログインメソッド
	 */
	public Employee loginEmployee(int number,String employee_password) throws SQLException{

		//初期化
		Employee employee = null;

		//データベースからログインした社員情報を取得するSQL文
		String sql ="select * from db_employee where number = ? and employee_password = ?";
		ps = con.prepareStatement(sql);

		//プレースホルダに値をセット
		ps.setInt(1,number);
		ps.setString(2,employee_password);

		//SQL文の実行
		rs = ps.executeQuery();

		if(rs.next()){

			//データベースから取得した値をセット
			 employee = new Employee();

			//社員番号
			employee.setNumber(rs.getInt("number"));
			//姓
			employee.setLastName(rs.getString("lastName"));
			//名
			employee.setFirstName(rs.getString("firstName"));
			//姓フリガナ
			employee.setLastKana(rs.getString("lastKana"));
			//名フリガナ
			employee.setFirstKana(rs.getString("firstKana"));
			//部署名
			employee.setDepartment_Name(rs.getString("department_name"));
			//部署コード
			employee.setDepartment_Code(rs.getString("department_code"));
			//血液型
			employee.setBlood(rs.getString("blood"));
			//年齢
			employee.setAge(rs.getInt("age"));
			//生年月日
			employee.setBirthDay(rs.getString("birthDay"));
			//性別
			employee.setGender(rs.getString("gender"));
			//パスワード
			employee.setEmployee_Password(rs.getString("employee_password"));
		}
		return employee;
	}

	/**
	 *@return 新規社員番号を返す
	 *@throws SQLException データベース接続処理でエラー
	 *新規登録画面に社員番号を自動表示するメソッド
	 */
	public Employee numberAutoDisplay() throws SQLException{

		//初期化
		Employee employee = null;

		//データべ―スに新規社員番号を登録するSQL文
		String sql = "select max(number) from db_employee";
		ps = con.prepareStatement(sql);

		//SQL文の実行
		rs = ps.executeQuery();

		//社員番号新規登録
		if(rs.next()){
			employee = new Employee();
			employee.setNumber(rs.getInt(1) + 1);
		}
		return employee;
	}

	/**
	 *@param number 社員番号
	 *@param lastName 姓
	 *@param firstName 名
	 *@param lastKana 姓カナ
	 *@param firstKana 名カナ
	 *@param department_name 部署名
	 *@param blood 血液型
	 *@param employee_password 社員パスワード
	 *@return 新規社員登録出来たらtrue,出来なかったらfalse
	 *@throws SQLException  データベース接続処理でエラー
	 *新規社員登録メソッド
	 */
	public boolean registEmployee(int number,String lastName,String firstName,String lastKana,String firstKana,String department_name,String blood,String employee_password) throws SQLException{

		//オートコミットの無効
		con.setAutoCommit(false);

		//オブジェクトの生成
		Employee employee = new Employee();

		//データベースに部署コードを登録するSQL文
		String sql1 = "select department_code from db_department where department_name = ?";
		ps = con.prepareStatement(sql1);

		//部署名
		ps.setString(1,department_name);

		//SQL文の実行
		rs = ps.executeQuery();

		//部署コード新規登録
		if(rs.next()){
			employee.setDepartment_Code(rs.getString("department_code"));
		}

		//社員登録判定
		boolean registJudge = false;

		//データベースに新規社員登録するSQL文
		String sql2 = "insert into db_employee(number,lastName,firstName,lastKana,firstKana,department_name,department_code,blood,employee_password) values (?,?,?,?,?,?,?,?,?)";
		ps = con.prepareStatement(sql2);

		//プレースホルダに値をセット
		//社員番号
		ps.setInt(1,number);
		//姓
		ps.setString(2,lastName);
		//名
		ps.setString(3,firstName);
		//姓フリガナ
		ps.setString(4,lastKana);
		//名フリガナ
		ps.setString(5,firstKana);
		//部署名
		ps.setString(6,department_name);
		//部署コード
		ps.setString(7,employee.getDepartment_Code());
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
		String sql = "update db_employee set lastName = ?,firstName = ?,lastKana = ?,firstKana = ?,department_name = ?,blood = ?,age = ?,gender = ?,birthDay = ? where number = ?";
		ps = con.prepareStatement(sql);

		//プレースホルダに値をセット
		//姓
		ps.setString(1,employee.getLastName());
		//名
		ps.setString(2,employee.getFirstName());
		//姓フリガナ
		ps.setString(3,employee.getLastKana());
		//名フリガナ
		ps.setString(4,employee.getFirstKana());
		//部署名
		ps.setString(5,employee.getDepartment_Name());
		//血液型
		ps.setString(6,employee.getBlood());
		//年齢
		ps.setInt(7,employee.getAge());
		//性別
		ps.setString(8,employee.getGender());
		//生年月日
		ps.setString(9,employee.getBirthDay());
		//社員番号
		ps.setInt(10,employee.getNumber());

		//SQL文の実行
		int ue = ps.executeUpdate();

		//更新したらコミットする
		if(ue > 0){
			con.commit();
		}
		return employee;
	}

	/**
	 *@param number 社員番号
	 *@return 選択した社員を削除出来たらtrue、出来なかったらfalse
	 *@throws SQLException データベース接続処理でエラー
	 *社員を削除するメソッド
	 */
	public boolean deleteEmployee(int number) throws SQLException{

		//オートコミットの無効
		con.setAutoCommit(false);

		//データベースから社員を削除するSQL文
		String sql = "delete from db_employee where number = ?";
		ps = con.prepareStatement(sql);

		//削除判定
		boolean deleteJudge = false;

		//プレースホルダに値をセット
		//社員番号
		ps.setInt(1,number);

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
	 *@param number 社員番号
	 *@return 選択した社員情報を返す
	 *@throws SQLException データベース接続処理でエラー
	 *選択した社員情報を探すメソッド
	 */
	public Employee selectEmployee(int number) throws SQLException{

		//初期化
		Employee employee = null;

		//データベースから社員情報を取得するSQL文
		String sql ="select * from db_employee where number = ?";
		ps = con.prepareStatement(sql);

		//プレースホルダに値をセット
		ps.setInt(1,number);

		//SQL文の実行
		rs = ps.executeQuery();

		if(rs.next()){

			//データベースから取得した値をセット
			employee = new Employee();

			//社員番号
			employee.setNumber(rs.getInt("number"));
			//姓
			employee.setLastName(rs.getString("lastName"));
			//名
			employee.setFirstName(rs.getString("firstName"));
			//姓フリガナ
			employee.setLastKana(rs.getString("lastKana"));
			//名フリガナ
			employee.setFirstKana(rs.getString("firstKana"));
			//部署名
			employee.setDepartment_Name(rs.getString("department_name"));
			//部署コード
			employee.setDepartment_Code(rs.getString("department_code"));
			//血液型
			employee.setBlood(rs.getString("blood"));
			//年齢
			employee.setAge(rs.getInt("age"));
			//生年月日
			employee.setBirthDay(rs.getString("birthDay"));
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
		String sql ="select number,lastName,firstName,lastKana,firstKana,department_name,blood,age,birthDay,gender from db_employee order by number";
		ps = con.prepareStatement(sql);

		//SQL文の実行
		rs = ps.executeQuery();

		while(rs.next()){

			//データベースから取得した値をセット
			employee = new Employee();

			//社員番号
			employee.setNumber(rs.getInt("number"));
			//姓
			employee.setLastName(rs.getString("lastName"));
			//名
			employee.setFirstName(rs.getString("firstName"));
			//姓フリガナ
			employee.setLastKana(rs.getString("lastKana"));
			//名フリガナ
			employee.setFirstKana(rs.getString("firstKana"));
			//部署名
			employee.setDepartment_Name(rs.getString("department_name"));
			//血液型
			employee.setBlood(rs.getString("blood"));
			//年齢
			employee.setAge(rs.getInt("age"));
			//生年月日
			employee.setBirthDay(rs.getString("birthDay"));
			//性別
			employee.setGender(rs.getString("gender"));

			elist.add(employee);
		}
		return elist;
	}

	/**
	 *@param employee_password 社員パスワード
	 *@param number 社員番号
	 *@return パスワード変更出来たらtrue,出来なかったらfalse
	 *@throws SQLException データベース接続処理でエラー
	 *社員パスワード変更メソッド
	 */
	public boolean changeEmployeePassword(String employee_password,int number) throws SQLException{

		//オートコミットの無効
		con.setAutoCommit(false);

		//パスワード変更判定
		boolean changeJudge = false;

		//データベースのパスワードを変更するSQL文
		String sql ="update db_employee set employee_password = ? where number = ?";
		ps = con.prepareStatement(sql);

		//プレースホルダに値をセット
		ps.setString(1,employee_password);
		ps.setInt(2,number);

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
	 *@param number 社員番号
	 *@return ログインした社員情報を返す
	 *@throws SQLException データベース接続処理でエラー
	 *ゲストログインメソッド
	 */
	public Employee loginGuest() throws SQLException{

		//初期化
		Employee employee = null;

		//データベースからゲスト情報を取得するSQL文
		String sql ="select * from db_employee where number = 0";
		ps = con.prepareStatement(sql);

		//SQL文の実行
		rs = ps.executeQuery();

		if(rs.next()){

			//データベースから取得した値をセット
			 employee = new Employee();

			//社員番号
			employee.setNumber(rs.getInt("number"));
			//姓
			employee.setLastName(rs.getString("lastName"));
			//名
			employee.setFirstName(rs.getString("firstName"));
			//姓フリガナ
			employee.setLastKana(rs.getString("lastKana"));
			//名フリガナ
			employee.setFirstKana(rs.getString("firstKana"));
			//部署名
			employee.setDepartment_Name(rs.getString("department_name"));
			//部署コード
			employee.setDepartment_Code(rs.getString("department_code"));
			//血液型
			employee.setBlood(rs.getString("blood"));
			//年齢
			employee.setAge(rs.getInt("age"));
			//生年月日
			employee.setBirthDay(rs.getString("birthDay"));
			//性別
			employee.setGender(rs.getString("gender"));
			//パスワード
			employee.setEmployee_Password(rs.getString("employee_password"));
		}
		return employee;
	}
}