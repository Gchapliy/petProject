<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>

<section id="accounts">
    <div  class="row center-lg center-md center-sm center-xs">
        <div class="center-lg center-md center-sm center-xs col-lg-9 col-md-9 col-sm-9 col-xs-9">
            <div class="row center-lg center-md center-sm center-xs account_row">
                <h3>Welcome back <span class="user_name">${user.userAccountName}</span></h3>
            </div>
            <div class="row center-lg center-md center-sm center-xs account_row">
                <h4>Your accounts</h4>
            </div>
            <div class="row center-lg center-md center-sm center-xs account_sub_row">
                <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">account's number</div>
                <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">type</div>
                <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">balance</div>
            </div>
            <c:forEach items="${bankAccounts}" var="account">
                <a href="/bankAccount?uuid=${account.accountUuid}">
                    <div class="row center-lg center-md center-sm center-xs account_sub_row acc_data">
                        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">${account.accountUuid}</div>
                        <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">${account.accountType}</div>
                        <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">${account.accountBalance}</div>
                    </div>
                </a>
            </c:forEach>
            <c:if test="${noAccaunts != null}">
                <div class="row center-lg center-md center-sm center-xs">
                    <h3>you don't have any accounts</h3>
                </div>
            </c:if>
        </div>
    </div>
</section>


<%@ include file="footer.jsp" %>
