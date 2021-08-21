package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *@author Akihiro Nakamura
 *データベースへ接続するクラス
 */
public class ConnectionManager {

	/**
	 *Javaとpcmsデータベースを接続するためのURL
	 */
	private static final String url = "jdbc:mysql://localhost/pcms";

	/**
	 *pcmsデータベースを使用するユーザー
	 */
	private static final String user = "root";

	/**
	 *pcmsデータベースを使用するために必要なパスワード
	 */
	private static final String pass = "pw";

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
	 *@return conを返す
	 *@throws SQLException データベース接続処理でエラー
	 *データベースへ接続するメソッド
	 */
	public Connection connect() throws SQLException{

		//初期化
		con = null;

		try{
			//データベース接続準備
			Class.forName("com.mysql.cj.jdbc.Driver");

			//データベース接続情報
			con = DriverManager.getConnection(url,user,pass);

		//Class.forName()で例外発生
		}catch(ClassNotFoundException e){
			e.printStackTrace();

		//getConnection()で例外発生
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	/**
	 *@return conを返す
	 *@throws SQLException データベース接続処理でエラー
	 *データベース切断メソッド
	 */
	public Connection close() throws SQLException{

		//データベースとの接続を切断
		try{
			if(con!= null)
				con.close();

			if(ps != null)
				ps.close();

			if(rs != null)
				rs.close();

		}catch(SQLException e){
			e.printStackTrace();
		}
		return con;
	}
}