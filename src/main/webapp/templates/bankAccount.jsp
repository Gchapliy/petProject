<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="header.jsp" %>

<%--BANK ACCOUNT--%>
<section id="bankAccount">
    <div class="row center-lg center-md center-sm center-xs title">
        <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">${uuid}</div>
        <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">${type}</div>
        <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">${creationDate}</div>
        <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">${expirationDate}</div>
        <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">${balance}</div>
        <c:if test="${bankAccount.accountType == depositType || bankAccount.accountType == creditType}">
            <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">${interestRate}</div>
            <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">${debt}</div>
            <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">${limit}</div>
        </c:if>
    </div>
    <c:if test="${bankAccount != null}">
        <div class="row center-lg center-md center-sm center-xs">
            <div class="col-lg-3 col-md-3 col-sm-3 col-xs-2">${bankAccount.accountUuid}</div>
            <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">${bankAccount.accountType}</div>
            <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">${dateFormat.format(bankAccount.accountCreationDate)}</div>
            <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">${dateFormat.format(bankAccount.accountExpirationDate)}</div>
            <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">${numberFormat.format(bankAccount.accountBalance)} ${currency}</div>
            <c:if test="${bankAccount.accountType == depositType || bankAccount.accountType == creditType}">
                <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">${numberFormat.format(bankAccount.accountInterestRate)}</div>
                <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">${numberFormat.format(bankAccount.accountDebt)}</div>
                <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">${numberFormat.format(bankAccount.accountLimit)}</div>
            </c:if>
        </div>
    </c:if>
    <div class="row center-lg center-md center-sm center-xs">
        <h2>${noBankAccount}</h2>
    </div>
</section>

<%@ include file="bankAccountManageInterface.jsp" %>

<%@ include file="footer.jsp" %>

