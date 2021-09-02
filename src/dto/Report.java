package dto;

import java.io.Serializable;

/**
 *@author Akihiro Nakamura
 *工数クラス
 */
public class Report implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 *社員ID
	 */
	private int employee_id;

	/**
	 *姓
	 */
	private String last_name;

	/**
	 *名
	 */
	private String first_name;

	/**
	 *部署ID
	 */
	private String department_id;

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
	 *@return number - 社員ID
	 *社員IDを返す
	 */
	public int getEmployee_Id(){
		return employee_id;
	}

	/**
	 *@param employee_id - 社員ID
	 *社員IDのセット
	 */
	public void setEmployee_Id(int employee_id){
		this.employee_id = employee_id;
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
	public void setLast_Name(String last_name){
		this.last_name = last_name;
	}

	/**
	 *@return first_name - 名
	 *名を返す
	 */
	public String getFirst_Name(){
		return first_name;
	}

	/**
	 *@param first_name - 名
	 *名のセット
	 */
	public void setFirst_Name(String first_name){
		this.first_name = first_name;
	}

	/**
	 *@return department_id - 部署ID
	 *部署IDのセット
	 */
	public String getDepartment_Id(){
		return department_id;
	}
	/**
	 *@param department_id - 部署ID
	 *部署IDのセット
	 */
	public void setDepartment_Id(String department_id){
		this.department_id = department_id;
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