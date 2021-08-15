package dto;

import java.io.Serializable;

/**
 *@author Akihiro Nakamura
 *管理者クラス
 */

public class Admin implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 *社員番号
	 */
	int number;

	/**
	 *姓
	 */
	private String last_name;

	/**
	 *名
	 */
	private String first_name;

	/**
	 *管理者パスワード
	 */
	private String admin_password;

	/**
	 *コンストラクタ
	 */
	public Admin(){}

	/**
	 *@return number - 社員番号
	 *社員番号を返す
	 */
	public int getNumber(){
		return number;
	}

	/**
	 *@param number - 社員番号
	 *社員番号のセット
	 */
	public void setNumber(int number){
		this.number = number;
	}

	/**
	 *@return last_name - 姓
	 *姓を返す
	 */
	public String getLast_Name(){
		return last_name;
	}

	/**
	 *@param last_name - 姓
	 *姓のセット
	 */
	public void setLast_name(String last_name){
		this.last_name = last_name;
	}

	/**
	 *@return firstName - 名
	 *名を返す
	 */
	public String getFirstName(){
		return firstName;
	}

	/**
	 *@param firstName - 名
	 *名のセット
	 */
	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	/**
	 *@return admin_password - 管理者パスワード
	 *管理者パスワードを返す
	 */
	public String getAdmin_Password(){
		return admin_password;
	}

	/**
	 *@param admin_password - 管理者パスワード
	 *管理者パスワードのセット
	 */
	public void setAdmin_Password(String admin_password){
		this.admin_password = admin_password;
	}
}
