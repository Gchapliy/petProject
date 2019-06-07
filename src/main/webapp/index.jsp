<%@ page contentType="text/html;charset=UTF-8" %>

<%@ include file="templates/header.jsp" %>

<%--MAIN PAGE--%>
<section id="main_page">
    <div class="banner_text">${welcome}</div>
</section>

<%--SERVICES--%>
<section id="services">
    <div class="row center-lg center-md end-sm center-xs service_title">
        ${our_services}
    </div>
    <div class="row center-lg center-md end-sm center-xs">
        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 service_account">
            <a href="/standard">
                <div class="row center-lg center-md end-sm center-xs">
                    <h2 class="service_account_title">${standard_title}</h2>
                </div>
                <div class="row center-lg center-md end-sm center-xs">
                    ${standard_standard1}
                </div>
                <div class="row center-lg center-md end-sm center-xs">
                    ${standard_standard2}
                </div>
                <div class="row center-lg center-md end-sm center-xs">
                    ${standard_standard3}
                </div>
                <div class="row center-lg center-md end-sm center-xs">
                    ${standard_standard4}
                </div>
            </a>
        </div>
        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 service_account">
            <a href="/deposit">
                <div class="row center-lg center-md end-sm center-xs">
                    <h2 class="service_account_title">${deposit_title}</h2>
                </div>
                <div class="row center-lg center-md end-sm center-xs">
                    ${deposit_deposit1}
                </div>
                <div class="row center-lg center-md end-sm center-xs">
                    ${deposit_deposit2}
                </div>
                <div class="row center-lg center-md end-sm center-xs">
                    ${deposit_deposit3}
                </div>
                <div class="row center-lg center-md end-sm center-xs">
                    ${deposit_deposit4}
                </div>
            </a>
        </div>
        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 service_account">
            <a href="/credit">
                <div class="row center-lg center-md end-sm center-xs">
                    <h2 class="service_account_title">${credit_title}</h2>
                </div>
                <div class="row center-lg center-md end-sm center-xs">
                    ${credit_credit1}
                </div>
                <div class="row center-lg center-md end-sm center-xs">
                    ${credit_credit2}
                </div>
                <div class="row center-lg center-md end-sm center-xs">
                    ${credit_credit3}
                </div>
                <div class="row center-lg center-md end-sm center-xs">
                    ${credit_credit4}
                </div>
            </a>
        </div>
    </div>
</section>

<%--JOIN US--%>
<section id="join_us">
    <div class="row center-lg center-md end-sm center-xs">
        <a href="/register" class="join">
            ${join}
        </a>
    </div>
</section>

<%@ include file="templates/footer.jsp" %>