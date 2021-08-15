package dto;

import java.io.Serializable;

/**
 *@author Akihiro Nakamura
 *作業クラス
 */

public class Work implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 *部署コード
	 */
	private String department_code;

	/**
	 *作業内容
	 */
	private String task;

	/**
	 *コンストラクタ
	 */
	public Work(){}

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
