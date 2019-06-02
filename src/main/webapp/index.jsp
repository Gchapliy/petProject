<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<a href="/home?language=en_US">English</a>
<a href="/home?language=ru_RU">Russian</a>

<h3>Locale of ${country} or ${requestScope.country} or <%=request.getAttribute("country")%></h3>
Format Number: ${fnumber}
<br>
Format Currency: ${requestScope.fcurrency}
<br>
Format Percent: ${requestScope.fpercent}
<br>
Format Date: ${requestScope.fdate}
<br>
Format String: ${requestScope.fstring}
<br>
</body>
</html>
