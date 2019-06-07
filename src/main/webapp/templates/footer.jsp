<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--FOOTER--%>
<section id="footer">
    <div class="row center-lg center-md center-sm center-xs footer_elements">
        <div class="col-lg-4 col-md-3 col-sm-3 col-xs-3 footer_data">
            <div class="row center-lg center-md end-sm center-xs">
               <h2>${footer_menu}</h2>
            </div>
            <div class="row center-lg center-md end-sm center-xs">
                <a href="/">${home}</a>
            </div>
            <div class="row center-lg center-md end-sm center-xs">
                <a href="#services">${services}</a>
            </div>
        </div>
        <div class="col-lg-4 col-md-3 col-sm-3 col-xs-3 footer_data">
            <div class="row center-lg center-md end-sm center-xs">
                <h2>${footer_contact}</h2>
            </div>
            <div class="row center-lg center-md end-sm center-xs contacts">
                ${footer_address}
            </div>
            <div class="row center-lg center-md end-sm center-xs contacts">
                ${footer_phone}
            </div>
            <div class="row center-lg center-md end-sm center-xs contacts">
                ${footer_email}
            </div>
        </div>
    </div>
    <div class="row center-lg center-md center-sm center-xs copyright">
        <div class="row center-lg center-md center-sm center-xs copyright_text">
            &copy; Copyright reserved to&nbsp;<span class="colored_copyright">BankSystem</span>
        </div>
    </div>
</section>
</body>
</html>
