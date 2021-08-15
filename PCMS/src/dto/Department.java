package dto;

import java.io.Serializable;

/**
 *@author Akihiro Nakamura
 *部署クラス
 */

public class Department implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 *部署コード
	 */
	private String department_code;

	/**
	 *部署名
	 */
	private String department_name;

	/**
	 *機械名
	 */
	private String machine_name;

	/**
	 *部署別工数合計時間
	 */
	private int department_hours;

	/**
	 *コンストラクタ
	 */
	public Department(){}

	/**
	 *@return department_code - 部署コード
	 *部署コードを返す
	 */
	public String getDepartment_Code(){
		return department_code;
	}

	/**
	 *@param department_code - 部署コード
	 *	部署コードのセット
	 */
	public void setDepartment_Code(String department_code){
		this.department_code = department_code;
	}

	/**
	 *@return department_code - 部署名
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
	 *@return machine_name - 機械名
	 *機械名を返す
	 */
	public String getMachine_Name(){
		return machine_name;
	}

	/**
	 *@param machine_name - 機械名
	 *機械名のセット
	 */
	public void setMachine_Name(String machine_name){
		this.machine_name = machine_name;
	}

	/**
	 *@return department_hours - 部署別工数合計時間
	 *部署別工数合計時間を返す
	 */
	public int getDepartment_Hours(){
		return department_hours;
	}

	/**
	 *@param department_hours - 部署別工数合計時間
	 *部署別工数合計時間のセット
	 */
	public void setDepartment_Hours(int department_hours){
		this.department_hours = department_hours;
	}
}
