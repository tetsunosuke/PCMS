package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.Admin;

/**
 *@author Akihiro Nakamura
 *管理者テーブルに接続するDAOクラス
 */
public class AdminDAO {

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
	 *@param number 社員番号
	 *@param admin_password 社員パスワード
	 *@return 管理者情報を返す
	 *@throws SQLException データベース接続処理でエラー
	 *管理者ログインメソッド
	 */
	public Admin loginAdmin(int number,String admin_password) throws SQLException{

		//初期化
		Admin admin = null;

		//データベースから管理者情報を取得するSQL文
		String sql ="select * from db_admin where number = ? and admin_password = ?";
		ps = con.prepareStatement(sql);

		//プレースホルダに値をセット
		ps.setInt(1,number);
		ps.setString(2,admin_password);

		//SQL文の実行
		rs = ps.executeQuery();

		if(rs.next()){

			//データベースから取得した値をセット
			admin = new Admin();

			//社員番号
			admin.setNumber(rs.getInt(1));
			//姓
			admin.setLastName(rs.getString(2));
			//名
			admin.setFirstName(rs.getString(3));
			//管理者パスワード
			admin.setAdmin_Password(rs.getString(4));
		}
		return admin;
	}

	/**
	 *@return 管理者パスワードを返す
	 *@throws SQLException データベース接続処理でエラー
	 *管理者パスワードを取得するメソッド
	 */
	public Admin getAdminPassword() throws SQLException{

		//初期化
		Admin admin = null;

		//データベースから管理者パスワードをを取得するSQL文
		String sql ="select data from db_admin_password";
		ps = con.prepareStatement(sql);

		//SQL文の実行
		rs = ps.executeQuery();

		if(rs.next()){

			//データベースから取得した値をセット
			admin = new Admin();

			//管理者パスワード
			admin.setAdmin_Password(rs.getString("data"));
		}
		return admin;
	}

	/**
	 *@param number 社員番号
	 *@param lastName 姓
	 *@param firstName 名
	 *@param admin_password 管理者パスワード
	 *@return 管理者権限を付与出来たらtrue,出来なかったらfalse
	 *@throws SQLException データベース接続処理でエラー
	 *管理者権限を付与するメソッド
	 */
	public boolean registAdmin(int number,String lastName,String firstName,String admin_password) throws SQLException{

		//オートコミットの無効
		con.setAutoCommit(false);

		//管理者権限付与判定
		boolean registJudge = false;

		//データベースに管理者権限を登録するSQL文
		String sql = "insert into db_admin(number,lastName,firstName,admin_password) values (?,?,?,?)";
		ps = con.prepareStatement(sql);

		//プレースホルダに値をセット
		//社員番号
		ps.setInt(1,number);
		//姓
		ps.setString(2,lastName);
		//名
		ps.setString(3,firstName);
		//管理者パスワード
		ps.setString(4,admin_password);

		//SQL文の実行
		int ra = ps.executeUpdate();

		//登録したらコミットする
		if(ra > 0){
			registJudge = true;
			con.commit();
		}
		return registJudge;
	}

	/**
	 *@param number 社員番号
	 *@return 管理者権限を削除出来たらtrue,出来なかったらfalse
	 *@throws SQLException データベース接続処理でエラー
	 *管理者権限を削除するメソッド
	 */
	public boolean deleteAdmin(int number) throws SQLException{

		//オートコミットの無効
		con.setAutoCommit(false);

		//データベースから指定した管理者を削除するSQL文
		String sql = "delete from db_admin where number = ?";
		ps = con.prepareStatement(sql);

		//削除判定
		boolean deleteJudge = false;

		//プレースホルダに値をセット
		//社員番号
		ps.setInt(1,number);

		//SQL文の実行
		int da = ps.executeUpdate();

		//削除したらコミットする
		if(da > 0){
			deleteJudge = true;
			con.commit();
		}
		return deleteJudge;
	}

	/**
	 *@param admin_password 管理者パスワード
	 *@return 管理者パスワードを変更出来たらture,出来なかったらfalse
	 *@throws SQLException データベース接続処理でエラー
	 *管理者パスワード変更メソッド
	 */
	public boolean changeAdminPassword(String admin_password) throws SQLException{

		//オートコミットの無効
		con.setAutoCommit(false);

		//管理者パスワード変更判定
		boolean changeJudge = false;

		//データベースから管理者パスワードを取得するSQL文
		String sql ="update db_admin_password set data = ?";
		ps = con.prepareStatement(sql);

		//プレースホルダに値をセット
		ps.setString(1,admin_password);

		//SQL文の実行
		int cap = ps.executeUpdate();

		//変更したらコミットする
		if(cap > 0){
			changeJudge = true;
			con.commit();
		}
		return changeJudge;
	}

	/**
	 *@return 管理者ゲストユーザー情報を返す
	 *@throws SQLException データベース接続処理でエラー
	 *管理者ゲストユーザーログインメソッド
	 */
	public Admin loginGuestAdmin() throws SQLException{

		//初期化
		Admin admin = null;

		//データベースから管理者ゲストユーザー情報を取得するSQL文
		String sql ="select * from db_admin where number = 0";
		ps = con.prepareStatement(sql);

		//SQL文の実行
		rs = ps.executeQuery();

		if(rs.next()){

			//データベースから取得した値をセット
			admin = new Admin();

			//社員番号
			admin.setNumber(rs.getInt(1));
			//姓
			admin.setLastName(rs.getString(2));
			//名
			admin.setFirstName(rs.getString(3));
			//管理者パスワード
			admin.setAdmin_Password(rs.getString(4));
		}
		return admin;
	}
}
