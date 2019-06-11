<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>

<c:forEach items="${bankAccount}" var="account">
    <a href="/bankAccount?uuid=${account.accountUuid}">
        <div class="row center-lg center-md center-sm center-xs account_sub_row acc_data">
            <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">${account.accountUuid}</div>
            <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">${account.accountType}</div>
            <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">${account.accountBalance}</div>
        </div>
    </a>
</c:forEach>

<%@ include file="footer.jsp" %>

