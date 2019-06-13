<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>

<%--NEW BANK ACCOUNT--%>
<section id="newBankAccount">
    <div class="row center-lg center-md center-sm center-xs title">
        ${title}
    </div>
    <form method="POST" action="/newBankAccount">
        <div class="row center-lg center-md center-sm center-xs acc_line">
            <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2 end-lg end-md end-sm end-xs">
                ${chooseAccType}
            </div>
            <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1 start-lg start-md start-sm start-xs">
                <select class="acc_type" name="accType" size="1">
                    <option value="standard">${standard}</option>
                    <option value="deposit">${deposit}</option>
                    <option value="credit">${credit}</option>
                </select>
            </div>
        </div>
        <div class="row center-lg center-md center-sm center-xs acc_line dep_line">
            <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2 end-lg end-md end-sm end-xs">
                ${chooseDepTerm}
            </div>
            <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1 start-lg start-md start-sm start-xs">
                <select class="depositTerm" name="depositTerm" size="1">
                    <option value="3">${threeMonths}</option>
                    <option value="6">${sixMonths}</option>
                    <option value="12">${twelveMonths}</option>
                </select>
            </div>
        </div>
        <div class="row center-lg center-md center-sm center-xs acc_line dep_line">
            <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3 end-lg end-md end-sm end-xs">
                ${typeDepSum}
            </div>
            <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">
                <input class="depSum" name="depSum" type="text"/>
            </div>
            <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1 center-lg center-md center-sm center-xs">
                ${currency}
            </div>
        </div>
        <c:if test="${depSumError != null}">
            <div class="row center-lg center-md center-sm center-xs acc_line dep_line dep_e">
                    ${depSumError}
            </div>
        </c:if>
        <div class="row center-lg center-md center-sm center-xs acc_line dep_line">
            <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3 end-lg end-md end-sm end-xs">
                ${depPercent}
            </div>
            <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">
                <input class="dep_perc" name="dep_perc" type="text" value="${dep_perc}" readonly>
            </div>
            <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1 center-lg center-md center-sm center-xs">
                %
            </div>
        </div>
        <div class="row center-lg center-md center-sm center-xs acc_line cred_line">
            <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3 end-lg end-md end-sm end-xs">
                ${typeCredSum}
            </div>
            <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">
                <input class="credSum" name="credSum" type="text"/>
            </div>
            <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1 center-lg center-md center-sm center-xs">
                ${currency}
            </div>
        </div>
        <c:if test="${credSumError != null}">
            <div class="row center-lg center-md center-sm center-xs acc_line cred_line cred_e">
                    ${credSumError}
            </div>
        </c:if>
        <div class="row center-lg center-md center-sm center-xs acc_line cred_line">
            <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2 end-lg end-md end-sm end-xs">
                ${chooseCredTerm}
            </div>
            <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1 start-lg start-md start-sm start-xs">
                <select class="creditTerm" name="creditTerm" size="1">
                    <option value="6">${sixMonths}</option>
                    <option value="12">${twelveMonths}</option>
                    <option value="18">${eighteenMonths}</option>
                    <option value="24">${twentyFourMonths}</option>
                </select>
            </div>
        </div>
        <div class="row center-lg center-md center-sm center-xs acc_line cred_line">
            <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3 end-lg end-md end-sm end-xs acc_line">
                ${credPercent}
            </div>
            <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">
                <input class="cred_perc" name="cred_perc" type="text" value="${cred_perc}" readonly>
            </div>
            <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1 center-lg center-md center-sm center-xs">
                %
            </div>
        </div>
        <div class="row center-lg center-md center-sm center-xs">
            <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2 center-lg center-md center-sm center-xs login_btn">
                <input type="submit" value="${sendOrder}">
            </div>
        </div>
    </form>


</section>

<%@ include file="footer.jsp" %>
