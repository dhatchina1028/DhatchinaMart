<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Login"/>
<%@ include file="/WEB-INF/jsp/fragments/header.jspf" %>

<div class="form-card">
    <h2>Login to DhatchinaMart</h2>
    <c:if test="${param.registered == '1'}">
        <div class="alert alert-success">Registration successful. Please login.</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-error"><c:out value="${error}"/></div>
    </c:if>
    <form action="${ctx}/login" method="post">
        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" id="email" name="email" required autofocus
                   value="<c:out value='${param.email}'/>">
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required>
        </div>
        <button type="submit" class="btn" style="width:100%;">Login</button>
    </form>
    <p style="margin-top:16px;font-size:0.9rem;color:var(--gray);">
        New here? <a href="${ctx}/register">Create an account</a>
    </p>
    <p style="margin-top:10px;font-size:0.82rem;color:var(--gray);">
        Demo: buyer@dhatchinamart.com / Buyer@123
    </p>
</div>

<%@ include file="/WEB-INF/jsp/fragments/footer.jspf" %>
