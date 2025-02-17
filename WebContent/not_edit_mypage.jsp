<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="dto.Employee"%>

<%
//セッションスコープに保存された社員情報を取得
Employee syain = (Employee)session.getAttribute("employee");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>エラー</title>
<link rel="stylesheet" type="text/css" href="CSS/style.css">
</head>
<body>
<!-- ヘッダー -->
<header>
	<h1>PCMS</h1>

	<nav class="header_menu">
		<ul>
			<!-- ログイン中の社員ID、氏名、部署名,の表示 -->
			<li>社員ID:<%=syain.getEmployee_Id()%></li>
			<li>部署名:<%=syain.getDepartment_Name()%></li>
			<li>氏名:<%=syain.getLast_Name()%><%=syain.getFirst_Name()%></li>
			<!-- ヘッダーメニューの表示 -->
			<li><a href="mypage.jsp">マイページ</a></li>
			<li><a href="employee_password_change.jsp">パスワード変更</a></li>
			<li><a href="./Logout">ログアウト</a></li>
		</ul>
	</nav>
</header>

<div class="center_screen">
	<p>ゲストユーザーのためマイページは編集できません。</p>

	<!-- マイページへ -->
	<a href="mypage.jsp"><button type="button" class="button blue">マイページへ</button></a>
</div>

<!-- フッター -->
<footer>
	<div class="logo">PCMS</div>
	<h4><small>Copyright © 2020-2021 PCMS. All rights reserved.</small></h4>
</footer>
</body>
</html>