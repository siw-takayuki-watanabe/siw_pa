<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"

import="jp.siw.pa.yamazumi.util.PropertyLoader"
%>
<%
String today = (String)request.getAttribute("today");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
		<title>休日登録</title>
		<link href="../../siwportalsite/SIW-Portal-site/css/common_zangyou.css" rel="stylesheet" type="text/css"></link>
		<link href="../../siw_pa/CSS/table.css" rel="stylesheet" type="text/css"></link>

	</head>
	<body>
		<div class="wrapper row1">
			<header id="main_header" >
				<h1>休日登録</h1>
				<p id="logo"><a href="index.html">
				<img src="../../siwportalsite/SIW-Portal-site/images/siw-logo.png" width="150" height="25" alt="シンバテッコウショ" class="auto-style1"/></a><%= today %></p>
			</header>
		</div>
		<!-- /ヘッダーエリアここまで -->

		<!-- グローバルナビゲーションエリアここから -->
		<div class="wrapper row2">
			<nav id="g_navi">
				<ul>
				</ul>
			</nav>
		</div>
		<!-- /グローバルナビゲーションエリアここまで -->

		<article>
			<form method="get" action="<%=PropertyLoader.getProperty("url.servlet.CsvImport") %>" >
				<section>
					<table >
					<caption>当月取込期間</caption>
					<a href="file:\\192.168.101.236\riseプロジェクト\15.生産負荷計算">生産負荷計算フォルダ</a>
					<p>読み込むファイル名を　01XXHACHU.csv　に修正して登録お願いします。</p>
						<tr>
							<th style="width: 50px;">年</th>
							<th style="width: 50px;">月</th>
							<th style="width: 50px;">旬</th>
						</tr>
						<tr>
							<td>
								<select name="year" >
									<option value=""></option>
									<option value="2019" style="width: 100%;">2019</option>
									<option value="2020">2020</option>
									<option value="2021">2021</option>
									<option value="2022">2022</option>
									<option value="2023">2023</option>
									<option value="2024">2024</option>
									<option value="2025">2025</option>
									<option value="2026">2026</option>
									<option value="2027">2027</option>
									<option value="2028">2028</option>
									<option value="2029">2029</option>
									<option value="2030">2030</option>
								</select>
							</td>
							<td>
								<select name="month" >
									<option value=""></option>
									<option value="01">01</option>
									<option value="02">02</option>
									<option value="03">03</option>
									<option value="04">04</option>
									<option value="05">05</option>
									<option value="06">06</option>
									<option value="07">07</option>
									<option value="08">08</option>
									<option value="09">09</option>
									<option value="10">10</option>
									<option value="11">11</option>
									<option value="12">12</option>
								</select>
							</td>
							<td>
								<select name="half" >
									<option value=""></option>
									<option value="A">A</option>
									<option value="B">B</option>
								</select>
							</td>
						</tr>
					</table>

					<table >
					<caption>内示削除期間</caption>
						<tr>
							<th style="width: 50px;">年</th>
							<th style="width: 50px;">月</th>
							<th style="width: 50px;">旬</th>
						</tr>
						<tr>
							<td>
								<select name="del_year" >
									<option value=""></option>
									<option value="2019" style="width: 100%;">2019</option>
									<option value="2020">2020</option>
									<option value="2021">2021</option>
									<option value="2022">2022</option>
									<option value="2023">2023</option>
									<option value="2024">2024</option>
									<option value="2025">2025</option>
									<option value="2026">2026</option>
									<option value="2027">2027</option>
									<option value="2028">2028</option>
									<option value="2029">2029</option>
									<option value="2030">2030</option>
								</select>
							</td>
							<td>
								<select name="del_month" >
									<option value=""></option>
									<option value="01">01</option>
									<option value="02">02</option>
									<option value="03">03</option>
									<option value="04">04</option>
									<option value="05">05</option>
									<option value="06">06</option>
									<option value="07">07</option>
									<option value="08">08</option>
									<option value="09">09</option>
									<option value="10">10</option>
									<option value="11">11</option>
									<option value="12">12</option>
								</select>
							</td>
							<td>
								<select name="del_half" >
									<option value=""></option>
									<option value="A">A</option>
									<option value="B">B</option>
								</select>
							</td>
						</tr>
					</table>
				<input type="submit" value="処理開始" />
				</section>
			</form>
		</article>
	</body>
</html>