<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Admin Dashboard"/>
<%@ include file="/WEB-INF/jsp/fragments/header.jspf" %>

<h2 class="page-title">Admin Dashboard</h2>
<p class="page-subtitle">Platform overview and key statistics</p>

<div class="stat-grid">
    <div class="stat-card">
        <div class="stat-value"><c:out value="${stats.totalUsers}"/></div>
        <div class="stat-label">Total Users</div>
    </div>
    <div class="stat-card">
        <div class="stat-value"><c:out value="${stats.totalBuyers}"/></div>
        <div class="stat-label">Buyers</div>
    </div>
    <div class="stat-card">
        <div class="stat-value"><c:out value="${stats.totalSellers}"/></div>
        <div class="stat-label">Sellers</div>
    </div>
    <div class="stat-card">
        <div class="stat-value"><c:out value="${stats.totalProducts}"/></div>
        <div class="stat-label">Total Products</div>
    </div>
    <div class="stat-card">
        <div class="stat-value"><c:out value="${stats.totalOrders}"/></div>
        <div class="stat-label">Total Orders</div>
    </div>
</div>

<div style="margin-top:24px;background:#fff;border:1px solid var(--border);border-radius:var(--radius);padding:20px;">
    <p style="font-weight:700;margin-bottom:6px;">MVP scope</p>
    <p class="form-hint">
        This is the MVP admin dashboard. User management, product moderation and order management panels are planned
        for the final review.
    </p>
</div>

<%@ include file="/WEB-INF/jsp/fragments/footer.jspf" %>
