<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="header.jsp" %>

<%--BANK ACCOUNT HISTORY--%>
<section id="history">
    <div class="row center-lg center-md center-sm center-xs title">
        ${subTitle} <span class="titleUuid">${uuid}</span>
    </div>

    <div class="row center-lg center-md center-sm center-xs title">
        <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">${accountFrom}</div>
        <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">${accountTo}</div>
        <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">${date}</div>
        <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">${target}</div>
        <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">${amount}</div>
    </div>

    <c:forEach items="${transactionsHistory}" var="transaction">
        <div class="row center-lg center-md center-sm center-xs">
            <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">${transaction.bankAccountFrom.accountUuid}</div>
            <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">${transaction.bankAccountTo.accountUuid}</div>
            <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">${dateFormat.format(transaction.transactionDate)}</div>
            <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">${transaction.transactionTarget}</div>
            <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">${numberFormat.format(transaction.transactionAmount)} ${currency}</div>
        </div>
    </c:forEach>

    <c:if test="${transactionsHistory != null}">
        <%--PAGINATION--%>
        <section id="pagination">
            <div class="row center-lg center-md center-xs center-sm">
                <c:forEach items="${allHistory}" var="page">
                    <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">
                        <a href="/history?uuid=${uuid}&page=${page}">${page}</a>
                    </div>
                </c:forEach>
            </div>
        </section>
    </c:if>

    <c:if test="${noHistory != null}">
        <div class="row center-lg center-md center-sm center-xs title">
            ${noHistory}
        </div>
    </c:if>
</section>

<%@ include file="bankAccountManageInterface.jsp" %>

<%@ include file="footer.jsp" %>
