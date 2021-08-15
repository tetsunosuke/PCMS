package dto;

import java.io.Serializable;

/**
 *@author Akihiro Nakamura
 *社員クラス
 **/

public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 *社員番号
	 */
	private int number;

	/**
	 *姓
	 */
	private String lastName;

	/**
	 *名
	 */
	private String firstName;

	/**
	 *姓フリガナ
	 */
	private String lastKana;

	/**
	 *名フリガナ
	 */
	private String firstKana;

	/**
	 *部署名
	 */
	private String department_name;

	/**
	 *部署コード
	 */
	private String department_code;

	/**
	 *血液型
	 */
	private String blood;

	/**
	 *年齢
	 */
	private int age;

	/**
	 *性別
	 */
	private String gender;

	/**
	 *生年月日
	 */
	private String birthDay;

	/**
	 *社員パスワード
	 */
	private String employee_password;

	/**
	 *コンストラクタ
	 */
	public Employee(){}

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
	 *@return lastName - 姓
	 *姓を返す
	 */
	public String getLastName(){
		return lastName;
	}

	/**
	 *@param lastName - 姓
	 *姓のセット
	 */
	public void setLastName(String lastName){
		this.lastName = lastName;
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
	 *@return lastKana - 姓フリガナ
	 *姓フリガナを返す
	 */
	public String getLastKana(){
		return lastKana;
	}

	/**
	 *@param lastKana - 姓フリガナ
	 *姓フリガナを返す
	 */
	public void setLastKana(String lastKana){
		this.lastKana = lastKana;
	}

	/**
	 *@return firstKana - 名フリガナ
	 *名フリガナを返す
	 */
	public String getFirstKana(){
		return firstKana;
	}

	/**
	 *@param firstKana - 名フリガナ
	 *名フリガナを返す
	 */
	public void setFirstKana(String firstKana){
		this. firstKana = firstKana;
	}
	/**
	 *@return department_name - 部署名
	 *部署名を返す
	 */
	public String getDepartment_Name(){
		return department_name;
	}

	/**
	 *@param department_name - 部署名
	 *部署名のセット
	 */
	public void setDepartment_Name(String department_name){
		this.department_name = department_name;
	}

	/**
	 *@return department_code - 部署コード
	 *部署コードを返す
	 */
	public String getDepartment_Code(){
		return department_code;
	}

	/**
	 *@param department_code - 部署コード
	 *部署コードのセット
	 */
	public void setDepartment_Code(String department_code){
		this.department_code = department_code;
	}

	/**
	 *@return blood - 血液型
	 *血液型を返す
	 */
	public String getBlood(){
		return blood;
	}

	/**
	 *@param blood - 血液型
	 *血液型のセット
	 */
	public void setBlood(String blood){
		this.blood = blood;
	}

	/**
	 *@return age - 年齢
	 *年齢を返す
	 */
	public int getAge(){
		return age;
	}

	/**
	 *@param age - 年齢
	 *年齢のセット
	 */
	public void setAge(int age){
		this.age = age;
	}

	/**
	 *@return gender - 性別
	 *性別を返す
	 */
	public String getGender(){
		return gender;
	}

	/**
	 *@param gender - 性別
	 *性別のセット
	 */
	public void setGender(String gender){
		this.gender = gender;
	}

	/**
	 *@return birthDay - 生年月日
	 *生年月日を返す
	 */
	public String getBirthDay(){
		return birthDay;
	}

	/**
	 *@param birthDay - 生年月日
	 *生年月日のセット
	 */
	public void setBirthDay(String birthDay){
		this.birthDay = birthDay;
	}

	/**
	 *@return employee_password - 社員パスワード
	 *社員パスワードを返す
	 */
	public String getEmployee_Password(){
		return employee_password;
	}

	/**
	 *@param employee_password - 社員パスワード
	 *社員パスワードのセット
	 */
	public void setEmployee_Password(String employee_password){
		this.employee_password = employee_password;
	}
}
