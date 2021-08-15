package dto;

import java.io.Serializable;

/**
 *@author Akihiro Nakamura
 *工数クラス
 */
public class Report implements Serializable {

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
	 *部署コード
	 */
	private String department_code;

	/**
	 *日付
	 */
	private String day;

	/**
	 *機械名
	 */
	private String machine_name;

	/**
	 *作業項目
	 */
	private String task;

	/**
	 *実働時間
	 */
	private int work_time;

	/**
	 *残業時間
	 */
	private int over_time;

	/**
	 *休日出勤
	 */
	private int holiday_work;

	/**
	 *コメント
	 */
	private String comment;

	/**
	 *コンストラクタ
	 */
	public Report(){}

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
	 *@return department_code - 部署コード
	 *部署コードのセット
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
	 *@return day - 日付
	 *日付を返す
	 */
	public String getDay(){
		return day;
	}
	/**
	 *@param day - 日付
	 *日付のセット
	 */
	public void setDay(String day){
		this.day = day;
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
	 *@return task - 作業項目
	 *作業項目を返す
	 */
	public String getTask(){
		return task;
	}

	/**
	 *@param task - 作業項目
	 *作業項目のセット
	 */
	public void setTask(String task){
		this.task = task;
	}

	/**
	 *@return work_time - 実働時間
	 *実働時間を返す
	 */
	public int getWork_Time(){
		return work_time;
	}

	/**
	 *@param work_time - 実働時間
	 *実働時間のセット
	 */
	public void setWork_Time(int work_time){
		this.work_time = work_time;
	}

	/**
	 *@return over_time - 残業時間
	 *残業時間を返す
	 */
	public int getOver_Time(){
		return over_time;
	}
	/**
	 *@param over_time - 残業時間
	 *残業時間のセット
	 */
	public void setOver_Time(int over_time){
		this.over_time = over_time;
	}

	/**
	 *@return holiday_work - 休日出勤
	 *休日出勤を返す
	 */
	public int getHoliday_Work(){
		return holiday_work;
	}

	/**
	 *@param holiday_work - 休日出勤
	 *休日出勤のセット
	 */
	public void setHoliday_Work(int holiday_work){
		this.holiday_work = holiday_work;
	}

	/**
	 *@return comment - コメント
	 *コメントを返す
	 */
	public String getComment(){
		return comment;
	}

	/**
	 *@param comment - コメント
	 *コメントのセット
	 */
	public void setComment(String comment){
		this.comment = comment;
	}
}