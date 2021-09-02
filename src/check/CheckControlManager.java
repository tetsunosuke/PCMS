package check;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *@author Akihiro Nakamura
 *入力したテキストやパスワードをチェックするクラス
 */
public class CheckControlManager {

	/**
	 *半角英数字の判定をする正規表現
	 */
	private static final Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[0-9]).{6,11}");

	/**
	 *検査対象文字列
	 */
	private String target;

	/**
	 *@param target 検査対象文字列
	 *@return null又は未入力でなければtrue,null又は未入力であればfalse
	 *null・入力チェックメソッド
	 */
	public boolean nullEmpty(String target){

		//nullと入力有無の判定
		boolean nullEmptyCheck = true;

		//nullと入力有無のチェック
		if(target == null || target.isEmpty()){
			return false;
		}
		return nullEmptyCheck;
	}

	/**
	 *@param target 検査対象文字列
	 *@return 正規表現でマッチしていればtrue,マッチしてなければfalse
	 *正規表現チェックメソッド
	 */
	public boolean regix(String target){

		//正規表現判定
		boolean regixCheck = true;

		//検査対象文字列を正規表現で照合
		Matcher m = pattern.matcher(target);

		//正規表現の照合結果
		regixCheck = m.matches();

		return regixCheck;
	}
}
