<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>

<%--LOGIN--%>
<section id="login">
    <div class="row">
        <div class="center-lg center-md center-sm center-xs col-lg-5 col-md-5 col-sm-5 col-xs-5">
            <div class="row center-lg center-md center-sm center-xs">
                <h3>Login Page</h3>
            </div>
            <div class="row center-lg center-md center-sm center-xs">
                <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1 center-lg center-md center-sm center-xs">
                    <h3>Login</h3>
                </div>
                <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 start-lg start-md start-sm start-xs">
                    <input type="email">
                </div>
            </div>
        </div>
    </div>
</section>


<%--<p style="color: red;">${errorString}</p>


<form method="POST" action="${pageContext.request.contextPath}/login">
    <table border="0">
        <tr>
            <td>User Email</td>
            <td><input type="text" name="userEmail" value= "${user.userEmail}" /> </td>
        </tr>
        <tr>
            <td>Password</td>
            <td><input type="text" name="password" value= "${user.password}" /> </td>
        </tr>
        <tr>
            <td>Remember me</td>
            <td><input type="checkbox" name="rememberMe" value= "Y" /> </td>
        </tr>
        <tr>
            <td colspan ="2">
                <input type="submit" value= "Submit" />
                <a href="${pageContext.request.contextPath}/">Cancel</a>
            </td>
        </tr>
    </table>
</form>--%>

<%@ include file="footer.jsp" %>
