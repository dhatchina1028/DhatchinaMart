<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Something Went Wrong"/>
<%@ include file="/WEB-INF/jsp/fragments/header.jspf" %>

<div class="empty-state">
    <h3>Something went wrong</h3>
    <p>
        <c:choose>
            <c:when test="${not empty error}"><c:out value="${error}"/></c:when>
            <c:otherwise>An unexpected error occurred. Please try again.</c:otherwise>
        </c:choose>
    </p>
    <a href="${ctx}/" class="btn" style="margin-top:14px;">Go to Home</a>
</div>

<%@ include file="/WEB-INF/jsp/fragments/footer.jspf" %>
