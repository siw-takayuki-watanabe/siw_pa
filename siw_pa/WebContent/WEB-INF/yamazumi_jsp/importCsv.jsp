<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"

    import="jp.siw.pa.yamazumi.bean.YamazumiBean"
    import="jp.siw.pa.yamazumi.dao.YamazumiDao"
    import="jp.siw.pa.yamazumi.util.YamazumiCast"

    import="java.util.Iterator"
	import="java.util.List"

	import="jp.siw.pa.yamazumi.util.PropertyLoader"
    %>
<%
    String today = (String)request.getAttribute("today");
    String[] temp_files = (String[])request.getAttribute("temp_files");
    String[] temp_files_URL = (String[])request.getAttribute("temp_files_URL");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>タイトル</title>

		<link href="../../siwportalsite/SIW-Portal-site/css/common_zangyou.css" rel="stylesheet" type="text/css"/>
		<link href="../../siwportalsite/SIW-Portal-site/css/zangyou.css" rel="stylesheet" type="text/css"/>

		<script type="text/javascript" src="../../siw_pm/JS/js/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="../../siw_pm/JS/js/jquery-ui.js"></script>

	</head>

	<body>
		<div class="wrapper row1">
			<header id="main_header" >
				<h1>年度残業集計　ダッシュボード</h1>
				<p id="logo"><a href="index.html">
				<img src="../../siwportalsite/SIW-Portal-site/images/siw-logo.png" width="150" height="25" alt="シンバテッコウショ" class="auto-style1"/></a><%= today %>　　${ sessionLoginName }　　${ status }</p>
			</header>
		</div><!-- /ヘッダーエリアここまで -->

		<!-- グローバルナビゲーションエリアここから -->
		<div class="wrapper row2">
			<nav id="g_navi">
				<ul>
				</ul>
			</nav>
		</div>
		<!-- /グローバルナビゲーションエリアここまで -->


		<h1>山積計算終了しました</h1>

			<table>
				<tr>
					<th>ファイル名</th>
				</tr>
				<tr>
				<% for(int i=0; i<temp_files.length ; i++ ){ %>
					<td><a href="<%  out.println(temp_files_URL[i]); %>" ><% out.println(temp_files[i]); %></a></td>
				</tr>
				<%} %>
			</table>

	</body>
</html>