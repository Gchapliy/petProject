<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>

<%--ACCOUNTS--%>
<section id="accounts">
    <div class="row center-lg center-md center-sm center-xs">
        <div class="center-lg center-md center-sm center-xs col-lg-9 col-md-9 col-sm-9 col-xs-9">
            <div class="row center-lg center-md center-sm center-xs account_row_welcome">
                <h3>${welcome} <span class="user_name">${user.userAccountName}</span></h3>
            </div>
            <div class="row center-lg center-md center-sm center-xs account_row">
                <h4>${yourAccounts}</h4>
            </div>
            <div class="row center-lg center-md center-sm center-xs account_sub_row">
                <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">${accNumber}</div>
                <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">${accType}</div>
                <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">${balance}</div>
            </div>
            <c:forEach items="${bankAccounts}" var="account">
                <a href="/bankAccount?uuid=${account.accountUuid}">
                    <div class="row center-lg center-md center-sm center-xs account_sub_row acc_data">
                        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4"><h3>${account.accountUuid}</h3></div>
                        <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2"><h3>${account.accountType}</h3></div>
                        <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3"><h3>${numberFormat.format(account.accountBalance)}</h3></div>
                    </div>
                </a>
            </c:forEach>
            <div class="row center-lg center-md center-sm center-xs">
                <h3>${noAccounts}</h3>
            </div>
        </div>
    </div>
</section>

<%--ORDERS--%>
<section id="userOrders">
    <div class="row center-lg center-md center-sm center-xs account_row">
        <h4>${yourOrders}</h4>
    </div>
    <div class="row center-lg center-md center-sm center-xs account_sub_row">
        <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">${createDate}</div>
        <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">${accountType}</div>
        <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">${status}</div>
        <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">${delete}</div>
    </div>
    <c:forEach items="${bankAccountOrders}" var="order">
        <div class="row center-lg center-md center-sm center-xs account_sub_row">
            <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3"><h3>${dateFormat.format(order.orderCreateDate)}</h3></div>
            <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><h3>${order.accountType}</h3></div>
            <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><h3>${order.orderStatus}</h3></div>
            <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1 deleteBtn" onclick="orderDelete(${confirm}, ${confirmQuestion})">
                <i class="far fa-minus-square"></i>
            </div>

            <div id="orderId" style="display: none">${order.orderId}</div>
        </div>
    </c:forEach>
    <div class="row center-lg center-md center-sm center-xs">
        <h3>${noOrders}</h3>
    </div>
</section>

<%--SERVICES--%>
<%@ include file="services.jsp" %>

<%@ include file="footer.jsp" %>
