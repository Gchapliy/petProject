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
                        <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">
                            <h3>${numberFormat.format(account.accountBalance)}</h3></div>
                    </div>
                </a>
            </c:forEach>
            <c:if test="${bankAccounts == null}">
                <div class="row center-lg center-md center-sm center-xs">
                    <h3>${noAccounts}</h3>
                </div>
            </c:if>
        </div>
    </div>
</section>

<c:if test="${bankAccounts != null}">
    <%--PAGINATION--%>
    <section id="pagination">
        <div class="row center-lg center-md center-xs center-sm">
            <c:forEach items="${allAccountsPages}" var="page">
                <c:if test="${page == pageIdAccount}">
                    <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1 navigatorBtn typedBtn">
                        <a href="/userPage?pageA=${page}&pageUsO=${pageIdUsersOrders}&pageYO=${pageIdYourOrders}">${page}</a>
                    </div>
                </c:if>
                <c:if test="${page != pageIdAccount}">
                    <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1 navigatorBtn">
                        <a href="/userPage?pageA=${page}&pageUsO=${pageIdUsersOrders}&pageYO=${pageIdYourOrders}">${page}</a>
                    </div>
                </c:if>
            </c:forEach>
        </div>
    </section>
</c:if>


<<%--ADMIN INTERFACE--%>
<c:if test="${isAdmin != null}">
    <section id="adminInterface">
        <div class="row center-lg center-md center-xs center-sm  account_row">
            <h4>Users orders</h4>
        </div>
        <div class="row center-lg center-md center-sm center-xs account_sub_row">
            <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">${createDate}</div>
            <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">${expirationDate}</div>
            <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">${orderOwner}</div>
            <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">${accountType}</div>
            <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">${accountBalance}</div>
            <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">${accountLimit}</div>
            <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">${accountInterestRate}</div>
            <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">${status}</div>
            <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">${success}</div>
            <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">${deny}</div>
        </div>
        <c:forEach items="${usersBankAccountOrders}" var="order">
            <div class="row center-lg center-md center-sm center-xs account_sub_row">
                <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><h3>${dateFormat.format(order.orderCreateDate)}</h3></div>
                <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><h3>${dateFormat.format(order.accountExpirationDate)}</h3></div>
                <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2"><h3>${order.orderOwner.userAccountEmail}</h3></div>
                <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><h3>${order.accountType}</h3></div>
                <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><h3>${numberFormat.format(order.accountBalance)}</h3></div>
                <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><h3>${numberFormat.format(order.accountLimit)}</h3></div>
                <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><h3>${numberFormat.format(order.accountInterestRate)}</h3></div>
                <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><h3>${order.orderStatus}</h3></div>
                <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1  deleteBtn"
                     onclick="orderSuccess(${confirmSuccess}, ${successQuestion})">
                    <i class="fas fa-check-circle"></i>
                </div>
                <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1  deleteBtn"
                     onclick="orderDeny(${confirmDeny}, ${denyQuestion})">
                    <i class="fas fa-ban"></i>
                </div>

                <div id="userOrderId" style="display: none">${order.orderId}</div>
            </div>
        </c:forEach>
        <div class="row center-lg center-md center-sm center-xs">
            <h3>${noUserOrders}</h3>
        </div>
    </section>
    <c:if test="${usersBankAccountOrders != null}">
        <%--PAGINATION--%>
        <section id="pagination">
            <div class="row center-lg center-md center-xs center-sm">
                <c:forEach items="${allUsersOrdersPages}" var="page">
                    <c:if test="${pageIdUsersOrders == page}">
                        <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1 navigatorBtn typedBtn">
                            <a href="/userPage?pageA=${pageIdAccount}&pageUsO=${page}&pageYO=${pageIdYourOrders}">${page}</a>
                        </div>
                    </c:if>
                    <c:if test="${pageIdUsersOrders != page}">
                        <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1 navigatorBtn">
                            <a href="/userPage?pageA=${pageIdAccount}&pageUsO=${page}&pageYO=${pageIdYourOrders}">${page}</a>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </section>
    </c:if>
</c:if>

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
            <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1 deleteBtn"
                 onclick="orderDelete(${confirm}, ${confirmQuestion})">
                <i class="far fa-minus-square"></i>
            </div>

            <div id="orderId" style="display: none">${order.orderId}</div>
        </div>
    </c:forEach>
    <div class="row center-lg center-md center-sm center-xs">
        <h3>${noOrders}</h3>
    </div>
</section>

<c:if test="${bankAccountOrders != null}">
    <%--PAGINATION--%>
    <section id="pagination">
        <div class="row center-lg center-md center-xs center-sm">
            <c:forEach items="${allYourOrdersPages}" var="page">
                <c:if test="${pageIdYourOrders == page}">
                    <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1 navigatorBtn typedBtn">
                        <a href="/userPage?pageA=${pageIdAccount}&pageUsO=${pageIdUsersOrders}&pageYO=${page}">${page}</a>
                    </div>
                </c:if>
                <c:if test="${pageIdYourOrders != page}">
                    <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1 navigatorBtn">
                        <a href="/userPage?pageA=${pageIdAccount}&pageUsO=${pageIdUsersOrders}&pageYO=${page}">${page}</a>
                    </div>
                </c:if>
            </c:forEach>
        </div>
    </section>
</c:if>

<%--SERVICES--%>
<%@ include file="services.jsp" %>

<%@ include file="footer.jsp" %>
