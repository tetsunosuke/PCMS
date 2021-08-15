package dto;

import java.io.Serializable;

/**
 *@author Akihiro Nakamura
 *機械クラス
 */
public class Machine implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 *機械番号
	 */
	private String machine_number;

	/**
	 *機械名
	 */
	private String machine_name;

	/**
	 *機械別工数合計時間
	 */
	private int machine_hours;

	/**
	 *コンストラクタ
	 */
	public Machine(){}

	/**
	 *@return machine_number - 機械番号
	 *機械番号を返す
	 */
	public String getMachine_Number(){
		return machine_number;
	}

	/**
	 *@param machine_number - 機械番号
	 *機械番号のセット
	 */
	public void setMachine_Number(String machine_number){
		this.machine_number = machine_number;
	}

	/**
	 *@return machine_number - 機械名
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
	 *@return machine_hours - 機械別工数合計時間
	 *機械別工数合計時間を返す
	 */
	public int getMachine_Hours(){
		return machine_hours;
	}

	/**
	 *@param machine_hours - 機械別工数合計時間
	 *機械別工数合計時間のセット
	 */
	public void setMachine_Hours(int machine_hours){
		this.machine_hours = machine_hours;
	}
}
