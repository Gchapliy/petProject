<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>

<%--LOGIN--%>
<section id="login">
        <div  class="row center-lg center-md center-sm center-xs">
            <div class="center-lg center-md center-sm center-xs col-lg-9 col-md-9 col-sm-9 col-xs-9">
                <div class="row center-lg center-md center-sm center-xs login_row">
                    <h3>${title}</h3>
                </div>
                <div class="row center-lg center-md center-sm center-xs">
                    <form method="POST" action="/login">
                        <div class="row center-lg center-md center-sm center-xs login_row">
                            <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 start-lg start-md start-sm start-xs">
                                <h3>${email}</h3>
                            </div>
                            <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 start-lg start-md start-sm start-xs login_input">
                                <input type="email" name="userEmail" value= "${userEmail}" >
                            </div>
                        </div>
                        <div class="row center-lg center-md center-sm center-xs login_row">
                            <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 center-lg center-md center-sm center-xs">
                                <h3>${password}</h3>
                            </div>
                            <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 start-lg start-md start-sm start-xs  login_input">
                                <input type="password" name="password" >
                            </div>
                        </div>
                        <div class="row center-lg center-md center-sm center-xs login_row">
                            <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 center-lg center-md center-sm center-xs login_btn">
                                <input type="submit" value="${loginBtn}">
                            </div>
                            <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 center-lg center-md center-sm center-xs register_btn">
                                <a href="/register">${registerBtn}</a>
                            </div>
                        </div>
                        <div class="row center-lg center-md center-sm center-xs login_row error">
                            <h3>${error}</h3>
                        </div>
                    </form>
                </div>
            </div>
        </div>
</section>

<%@ include file="footer.jsp" %>
