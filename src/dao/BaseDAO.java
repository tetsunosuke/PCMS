package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 */
public class BaseDAO {

	/**
	 *特定のデータベースとの接続
	 */
	protected Connection con;

	/**
	 *SQL文の解析と実行
	 */
	protected PreparedStatement ps;

	/**
	 *SQL実行結果の取得
	 */
	protected  ResultSet rs;
    
    /**
     *@throws SQLException データベース接続処理でエラー
     *データベースと接続するメソッド
     */
    public void dbConnect() throws SQLException {
        ConnectionManager cm = new ConnectionManager();
        con = cm.connect();
        System.out.println("dbConnect:" + con);
    }

    /**
     *@throws SQLException データベース切断処理でエラー
     *データベースとの接続を切断するメソッド
     */
    public void dbClose() throws SQLException {
        ConnectionManager cm = new ConnectionManager();
        System.out.println("dbClose:" + con);
        cm.close(con);
        System.out.println("dbClose closed:" + con);
    }
}
