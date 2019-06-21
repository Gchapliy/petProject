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
            <form method="POST" action="/paymentTransfers?type=transfer&uuid=${uuid}">
                <div class="row center-lg center-md center-sm center-xs  login_row">
                    <h3>${transferTitle}</h3>
                </div>
                <div class="row center-lg center-md center-sm center-xs  login_row">
                    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 start-lg start-md start-sm start-xs">
                        ${transferSpecify}
                    </div>
                    <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3 start-lg start-md start-sm start-xs login_input">
                        <input type="text" name="recepAccount" value="${recepAccount}">
                    </div>
                </div>
                <c:if test="${errorTransferSpecify != null}">
                    <div class="row center-lg center-md center-sm center-xs login_row error">
                        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 center-lg center-md center-sm center-xs">
                            <h3>${errorTransferSpecify}</h3>
                        </div>
                    </div>
                </c:if>
                <div class="row center-lg center-md center-sm center-xs  login_row">
                    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 start-lg start-md start-sm start-xs">
                        ${transferSum}
                    </div>
                    <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3 start-lg start-md start-sm start-xs login_input">
                        <input type="text" name="recepSum" value="${recepSum}">
                    </div>
                </div>
                <c:if test="${errorTransferSum != null}">
                    <div class="row center-lg center-md center-sm center-xs login_row error">
                        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 center-lg center-md center-sm center-xs">
                            <h3>${errorTransferSum}</h3>
                        </div>
                    </div>
                </c:if>
                <div class="row center-lg center-md center-sm center-xs login_row">
                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 center-lg center-md center-sm center-xs login_btn">
                        <input type="submit" value="${transferBtn}">
                    </div>
                </div>
                <c:if test="${errorRequiredTransfer != null}">
                    <div class="row center-lg center-md center-sm center-xs login_row error">
                        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 center-lg center-md center-sm center-xs">
                            <h3>${errorRequiredTransfer}</h3>
                        </div>
                    </div>
                </c:if>
            </form>
        </div>
        <div class="row center-lg center-md center-sm center-xs login_row">
            <form method="POST" action="/paymentTransfers?type=payment&uuid=${uuid}">
                <div class="row center-lg center-md center-sm center-xs  login_row">
                    <h3>${payTitle}</h3>
                </div>
                <div class="row center-lg center-md center-sm center-xs  login_row">
                    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 start-lg start-md start-sm start-xs">
                        ${paySpecify}
                    </div>
                    <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3 start-lg start-md start-sm start-xs login_input">
                        <input type="text" name="recepAccount" value="${recepAccountPay}">
                    </div>
                </div>
                <c:if test="${errorPaySpecify != null} ">
                    <div class="row center-lg center-md center-sm center-xs login_row error">
                        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 center-lg center-md center-sm center-xs">
                            <h3>${errorPaySpecify}</h3>
                        </div>
                    </div>
                </c:if>
                <div class="row center-lg center-md center-sm center-xs  login_row">
                    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 start-lg start-md start-sm start-xs">
                        ${payTarget}
                    </div>
                    <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3 start-lg start-md start-sm start-xs login_input">
                        <input type="text" name="target" value="${target}">
                    </div>
                </div>
                <c:if test="${errorPayTarget != null} ">
                    <div class="row center-lg center-md center-sm center-xs login_row error">
                        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 center-lg center-md center-sm center-xs">
                            <h3>${errorPayTarget}</h3>
                        </div>
                    </div>
                </c:if>
                <div class="row center-lg center-md center-sm center-xs  login_row">
                    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 start-lg start-md start-sm start-xs">
                        ${transferSum}
                    </div>
                    <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3 start-lg start-md start-sm start-xs login_input">
                        <input type="text" name="recepSum" value="${recepSumPay}">
                    </div>
                </div>
                <c:if test="${errorPayTransferSum != null} ">
                    <div class="row center-lg center-md center-sm center-xs login_row error">
                        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 center-lg center-md center-sm center-xs">
                            <h3>${errorPayTransferSum}</h3>
                        </div>
                    </div>
                </c:if>
                <div class="row center-lg center-md center-sm center-xs login_row">
                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 center-lg center-md center-sm center-xs login_btn">
                        <input type="submit" value="${payBtn}">
                    </div>
                </div>
                <c:if test="${errorRequiredPay != null}">
                    <div class="row center-lg center-md center-sm center-xs login_row error">
                        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 center-lg center-md center-sm center-xs">
                            <h3>${errorRequiredPay}</h3>
                        </div>
                    </div>
                </c:if>
            </form>
        </div>
        </c:if>

    <c:if test="${depositCredit != null}">
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

        <c:if test="${transactionsHistory != null}">
            <%--PAGINATION--%>
            <section id="pagination">
                <div class="row center-lg center-md center-xs center-sm">
                    <c:forEach items="${allHistory}" var="page">
                        <c:if test="${pageHistoryId == page}">
                            <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1 navigatorBtn typedBtn">
                                <a href="/history?uuid=${uuid}&page=${page}">${page}</a>
                            </div>
                        </c:if>
                        <c:if test="${pageHistoryId != page}">
                            <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1 navigatorBtn">
                                <a href="/history?uuid=${uuid}&page=${page}">${page}</a>
                            </div>
                        </c:if>
                    </c:forEach>
                </div>
            </section>
        </c:if>

        <c:if test="${noHistory != null}">
            <div class="row center-lg center-md center-sm center-xs title">
                    ${noHistory}
            </div>
        </c:if>
    </c:if>
</section>

<%@ include file="footer.jsp" %>