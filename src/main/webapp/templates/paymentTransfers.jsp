<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>

<%--PAYMENT TRANSFERS--%>
<section id="paymentTransfers">
    <div class="row center-lg center-md center-sm center-xs">
        <div class="col-lg col-md col-xs col-sm">
            <div class="row center-lg center-md center-sm center-xs account_row">
                <h4>${account} <span class="titleUuid">${uuid}</span></h4>
            </div>
            <div class="row center-lg center-md center-sm center-xs account_sub_row">
                <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">${accType}</div>
                <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">${accBalance}</div>
            </div>
            <div class="row center-lg center-md center-sm center-xs account_sub_row">
                <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2"><h3>${type}</h3></div>
                <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2"><h3>${balance}</h3></div>
            </div>
        </div>
    </div>
</section>

<%--BANK ACCOUNT TRANSFERS INTERFACE--%>
<section id="transfersInterface">
    <c:if test="${payment != null}">
        <div class="row center-lg center-md center-sm center-xs login_row">
            <h4>${actions}</h4>
        </div>
        <div class="row center-lg center-md center-sm center-xs login_row">
            <form method="POST" action="/paymentTransfers">
                <div class="row center-lg center-md center-sm center-xs  login_row">
                    <h3>${transferTitle}</h3>
                </div>
                <div class="row center-lg center-md center-sm center-xs  login_row">
                    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 start-lg start-md start-sm start-xs">
                        ${transferSpecify}
                    </div>
                    <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3 start-lg start-md start-sm start-xs login_input">
                        <input type="text" name="recepAccount">
                    </div>
                </div>
                <div class="row center-lg center-md center-sm center-xs  login_row">
                    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 start-lg start-md start-sm start-xs">
                        ${transferSum}
                    </div>
                    <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3 start-lg start-md start-sm start-xs login_input">
                        <input type="text" name="recepSum">
                    </div>
                </div>
                <div class="row center-lg center-md center-sm center-xs login_row">
                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 center-lg center-md center-sm center-xs login_btn">
                        <input type="submit" value="${transferBtn}">
                    </div>
                </div>
            </form>
        </div>
        <div class="row center-lg center-md center-sm center-xs login_row">
            <form method="POST" action="/paymentTransfers">
                <div class="row center-lg center-md center-sm center-xs  login_row">
                    <h3>${payTitle}</h3>
                </div>
                <div class="row center-lg center-md center-sm center-xs  login_row">
                    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 start-lg start-md start-sm start-xs">
                        ${paySpecify}
                    </div>
                    <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3 start-lg start-md start-sm start-xs login_input">
                        <input type="text" name="recepAccount">
                    </div>
                </div>
                <div class="row center-lg center-md center-sm center-xs  login_row">
                    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 start-lg start-md start-sm start-xs">
                        ${payTarget}
                    </div>
                    <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3 start-lg start-md start-sm start-xs login_input">
                        <input type="text" name="target">
                    </div>
                </div>
                <div class="row center-lg center-md center-sm center-xs  login_row">
                    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 start-lg start-md start-sm start-xs">
                        ${transferSum}
                    </div>
                    <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3 start-lg start-md start-sm start-xs login_input">
                        <input type="text" name="recepSum">
                    </div>
                </div>
                <div class="row center-lg center-md center-sm center-xs login_row">
                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 center-lg center-md center-sm center-xs login_btn">
                        <input type="submit" value="${payBtn}">
                    </div>
                </div>
            </form>
        </div>
        </c:if>

    <c:if test="${deposit != null || credit != null}">
        <div class="row center-lg center-md center-sm center-xs login_row">
            <h4>${historyTitle}</h4>
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

        <c:if test="${noHistory != null}">
            <div class="row center-lg center-md center-sm center-xs title">
                    ${noHistory}
            </div>
        </c:if>
    </c:if>
</section>

<%@ include file="footer.jsp" %>