<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="title" value="Welcome"/>
<%@ include file="/WEB-INF/jsp/fragments/header.jspf" %>

<div class="hero">
    <h1>Everything you need. From sellers you trust.</h1>
    <p>DhatchinaMart is a multi-seller marketplace where verified sellers list products and buyers shop with
        confidence. Browse, add to cart, and check out in minutes.</p>
    <c:choose>
        <c:when test="${sessionScope.user != null}">
            <a href="${ctx}/products" class="btn">Start Shopping</a>
        </c:when>
        <c:otherwise>
            <a href="${ctx}/register" class="btn">Create Account</a>
            <a href="${ctx}/login" class="btn btn-secondary" style="margin-left:10px;color:#fff;border-color:#fff;">Login</a>
        </c:otherwise>
    </c:choose>
</div>

<h2 class="page-title">Shop by category</h2>
<p class="page-subtitle">Find exactly what you are looking for</p>
<div class="category-chips">
    <a href="${ctx}/products" class="category-chip">All Products</a>
    <a href="${ctx}/products?category=Electronics" class="category-chip">Electronics</a>
    <a href="${ctx}/products?category=Books" class="category-chip">Books</a>
    <a href="${ctx}/products?category=Clothing" class="category-chip">Clothing</a>
    <a href="${ctx}/products?category=Accessories" class="category-chip">Accessories</a>
    <a href="${ctx}/products?category=Home" class="category-chip">Home</a>
</div>

<div class="stat-grid">
    <div class="stat-card">
        <div class="stat-value">5+</div>
        <div class="stat-label">Product categories</div>
    </div>
    <div class="stat-card">
        <div class="stat-value">Multi-seller</div>
        <div class="stat-label">Verified sellers list their products</div>
    </div>
    <div class="stat-card">
        <div class="stat-value">100%</div>
        <div class="stat-label">Secure checkout &amp; order tracking</div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/fragments/footer.jspf" %>
