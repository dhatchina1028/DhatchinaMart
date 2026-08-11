<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Register"/>
<%@ include file="/WEB-INF/jsp/fragments/header.jspf" %>

<div class="form-card">
    <h2>Create your account</h2>
    <c:if test="${not empty error}">
        <div class="alert alert-error"><c:out value="${error}"/></div>
    </c:if>
    <form action="${ctx}/register" method="post">
        <div class="form-group">
            <label for="name">Full Name</label>
            <input type="text" id="name" name="name" required maxlength="100"
                   value="<c:out value='${name}'/>">
        </div>
        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" id="email" name="email" required maxlength="255"
                   value="<c:out value='${email}'/>">
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required minlength="8">
            <div class="form-hint">Minimum 8 characters</div>
        </div>
        <div class="form-group">
            <label for="confirmPassword">Confirm Password</label>
            <input type="password" id="confirmPassword" name="confirmPassword" required minlength="8">
        </div>
        <div class="form-group">
            <label for="role">I want to</label>
            <select id="role" name="role">
                <option value="BUYER" ${role == 'SELLER' ? '' : 'selected'}>Shop for products (Buyer)</option>
                <option value="SELLER" ${role == 'SELLER' ? 'selected' : ''}>Sell my products (Seller)</option>
            </select>
        </div>
        <button type="submit" class="btn" style="width:100%;">Register</button>
    </form>
    <p style="margin-top:16px;font-size:0.9rem;color:var(--gray);">
        Already have an account? <a href="${ctx}/login">Login</a>
    </p>
</div>

<%@ include file="/WEB-INF/jsp/fragments/footer.jspf" %>
