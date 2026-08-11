<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Not Found"/>
<%@ include file="/WEB-INF/jsp/fragments/header.jspf" %>

<div class="empty-state">
    <h3>404 - Page not found</h3>
    <p>The page or product you are looking for does not exist.</p>
    <a href="${ctx}/" class="btn" style="margin-top:14px;">Go to Home</a>
</div>

<%@ include file="/WEB-INF/jsp/fragments/footer.jspf" %>
