<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理者権限取得</title>
<link rel="stylesheet" type="text/css" href="CSS/style.css">
</head>
<body>
<!-- ヘッダー -->
<header>
	<h1>PCMS</h1>
</header>

<!-- 画面名称 -->
<h5>管理者権限取得</h5>

<!-- ログイン情報の入力 -->
<div class="center_screen-3">
	<p>社員としてログインして下さい。</p>
		<form action="./EmployeeLogin"  method="post">
			<table class="border_table">
				<tr class="border_style">
					<td>社員番号</td>
					<td><input type="text" name="number" class="textbox" autocomplete="off" required></td>	<!-- <input>タグ:フォームを構成する様々な入力部品を作成する際に使用 -->	<!-- type="text"：一行テキストボックスを作成する。name：フォーム部品に名前をつける。value：送信される値を指定する。size：フォーム部品のサイズを指定する -->
				</tr>

				<tr class="border_style">
					<td>パスワード</td>
					<td><input type="password" name="employee_password" class="textbox" autocomplete="off" required></td>
				</tr>
			</table>

		<!-- ログインボタン -->
		<button type="submit" name="login_check" class="button blue">ログイン</button>
	</form>
</div>

<!-- 管理者ログイン画面へ戻るボタン -->
<a href="admin_login.jsp"><button type="button" class="back_button">戻る</button></a>

<!-- フッター -->
<footer>
	<div class="logo">PCMS</div>
	<h4><small>Copyright © 2020-2021 PCMS. All rights reserved.</small></h4>
</footer>
</body>
</html>
