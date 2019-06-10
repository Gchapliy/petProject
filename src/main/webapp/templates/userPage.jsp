<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>

<section id="accounts">
    <div  class="row center-lg center-md center-sm center-xs">
        <div class="center-lg center-md center-sm center-xs col-lg-9 col-md-9 col-sm-9 col-xs-9">
            <div class="row center-lg center-md center-sm center-xs">
                <h3>Welcome back ${user.userAccountName}</h3>
            </div>
            <div class="row center-lg center-md center-sm center-xs">
                <h4>Your accounts</h4>
            </div>
            <div class="row center-lg center-md center-sm center-xs">
                <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">number</div>
                <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">type</div>
                <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">balance</div>
            </div>
        </div>
    </div>
</section>


<%@ include file="footer.jsp" %>
