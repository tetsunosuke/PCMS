package dto;

import java.io.Serializable;

/**
 *@author Akihiro Nakamura
 *作業クラス
 */

public class Work implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 *部署ID
	 */
	private String department_id;

	/**
	 *作業内容
	 */
	private String task;

	/**
	 *コンストラクタ
	 */
	public Work(){}

	/**
	 *@return department_id - 部署ID
	 *部署IDを返す
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
	 *@return task - 作業内容
	 *作業内容を返す
	 */
	public String getTask(){
		return task;
	}

	/**
	 *@param task - 作業内容
	 *作業内容のセット
	 */
	public void setTask(String task){
		this.task = task;
	}
}
