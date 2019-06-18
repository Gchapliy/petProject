<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>

<%--REGISTER--%>
<section id="login">
    <div  class="row center-lg center-md center-sm center-xs">
        <div class="center-lg center-md center-sm center-xs col-lg-9 col-md-9 col-sm-9 col-xs-9">
            <div class="row center-lg center-md center-sm center-xs login_row">
                <h3>${title}</h3>
            </div>
            <div class="row center-lg center-md center-sm center-xs">
                <form method="POST" action="/register">
                    <div class="row center-lg center-md center-sm center-xs login_row">
                        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 start-lg start-md start-sm start-xs">
                            <h3>${email}</h3>
                        </div>
                        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 start-lg start-md start-sm start-xs login_input">
                            <input type="email" name="userEmail" value= "${userEmail}" >
                        </div>
                    </div>
                    <div class="row center-lg center-md center-sm center-xs login_row">
                        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 start-lg start-md start-sm start-xs">
                            <h3>${name}</h3>
                        </div>
                        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 start-lg start-md start-sm start-xs login_input">
                            <input type="text" name="name" value= "${userName}" >
                        </div>
                    </div>
                    <div class="row center-lg center-md center-sm center-xs login_row">
                        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 start-lg start-md start-sm start-xs">
                            <h3>${phone}</h3>
                        </div>
                        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 start-lg start-md start-sm start-xs login_input">
                            <input type="text" name="userPhone" value= "${userPhone}" >
                        </div>
                    </div>
                    <div class="row center-lg center-md center-sm center-xs login_row">
                        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 start-lg start-md start-sm start-xs">
                            <h3>${gender}</h3>
                        </div>
                        <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2 start-lg start-md start-sm start-xs">
                            <input type="radio" name="userGender" value= "male">${male}
                        </div>
                        <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2 start-lg start-md start-sm start-xs">
                            <input type="radio" name="userGender" value= "female">${female}
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
                        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 center-lg center-md center-sm center-xs">
                            <h3>${repPassword}</h3>
                        </div>
                        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 start-lg start-md start-sm start-xs  login_input">
                            <input type="password" name="repPassword" >
                        </div>
                    </div>
                    <div class="row center-lg center-md center-sm center-xs login_row">
                        <div class="col-lg-5 col-md-5 col-sm-5 col-xs-5 center-lg center-md center-sm center-xs login_btn">
                            <input type="submit" value="${registerBtn}">
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
