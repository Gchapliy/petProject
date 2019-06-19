<%@ page contentType="text/html;charset=UTF-8" %>

<%@ include file="templates/header.jsp" %>

<%--MAIN PAGE--%>
<section id="main_page">
    <div class="banner_text">${welcome}</div>
</section>

<%--SERVICES--%>
<%@ include file="templates/services.jsp" %>

<%--JOIN US--%>
<section id="join_us">
    <c:if test="${sessionScope.loginedUser == null}">
        <div class="row center-lg center-md end-sm center-xs">
            <a href="/register" class="join">
                    ${join}
            </a>
        </div>
    </c:if>
</section>

<%@ include file="templates/footer.jsp" %>