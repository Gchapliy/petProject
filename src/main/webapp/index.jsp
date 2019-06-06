<%@ page contentType="text/html;charset=UTF-8" %>

<%@ include file="templates/header.jsp" %>

<%--MAIN PAGE--%>
<section id="main_page">
    <div class="banner_text">welcome to banksystem</div>
</section>

<%--SERVICES--%>
<section id="services">
    <div class="row center-lg center-md end-sm center-xs service_title">
        our services
    </div>
    <div class="row center-lg center-md end-sm center-xs">
        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 service_account">
            <a href="/standard">
                <div class="row center-lg center-md end-sm center-xs">
                    <h2 class="service_account_title">open standard account</h2>
                </div>
                <div class="row center-lg center-md end-sm center-xs">
                    round-the-clock service
                </div>
                <div class="row center-lg center-md end-sm center-xs">
                    round-the-clock support
                </div>
                <div class="row center-lg center-md end-sm center-xs">
                    internal transfers and calculations 24/7
                </div>
                <div class="row center-lg center-md end-sm center-xs">
                    simple and convenient internet bank
                </div>
            </a>
        </div>
        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 service_account">
            <a href="/deposit">
                <div class="row center-lg center-md end-sm center-xs">
                    <h2 class="service_account_title">open deposit account</h2>
                </div>
                <div class="row center-lg center-md end-sm center-xs">
                    term of deposit 3 - 12 months
                </div>
                <div class="row center-lg center-md end-sm center-xs">
                    deposit rate 10 - 20%
                </div>
                <div class="row center-lg center-md end-sm center-xs">
                    Monthly interest payment
                </div>
                <div class="row center-lg center-md end-sm center-xs">
                    Early closing of deposit
                </div>
            </a>
        </div>
        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 service_account">
            <a href="/credit">
                <div class="row center-lg center-md end-sm center-xs">
                    <h2 class="service_account_title">open credit account</h2>
                </div>
                <div class="row center-lg center-md end-sm center-xs">
                    from 5000 to 500000
                </div>
                <div class="row center-lg center-md end-sm center-xs">
                    interest rate 55%
                </div>
                <div class="row center-lg center-md end-sm center-xs">
                    maximum term 2 years
                </div>
                <div class="row center-lg center-md end-sm center-xs">
                    No monthly commission
                </div>
            </a>
        </div>
    </div>
</section>

<%--JOIN US--%>
<section id="join_us">
    <div class="row center-lg center-md end-sm center-xs">
        <a href="/register" class="join">
            join us
        </a>
    </div>
</section>

<%@ include file="templates/footer.jsp" %>