<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="title" value="Seller Dashboard"/>
<%@ include file="/WEB-INF/jsp/fragments/header.jspf" %>

<div style="display:flex;align-items:center;justify-content:space-between;flex-wrap:wrap;gap:12px;">
    <div>
        <h2 class="page-title">Seller Dashboard</h2>
        <p class="page-subtitle">Welcome, <c:out value="${sessionScope.user.name}"/></p>
    </div>
    <a href="${ctx}/seller/product/create" class="btn">+ Create Product</a>
</div>

<c:if test="${param.msg == 'created'}">
    <div class="alert alert-success">Product created successfully. It is now live in the marketplace.</div>
</c:if>

<div class="stat-grid" style="margin:20px 0;">
    <div class="stat-card">
        <div class="stat-value"><c:out value="${productCount}"/></div>
        <div class="stat-label">Products listed</div>
    </div>
</div>

<h3 class="page-title" style="font-size:1.1rem;margin:24px 0 14px;">Your Products</h3>
<c:choose>
    <c:when test="${empty products}">
        <div class="empty-state">
            <h3>No products yet</h3>
            <p>Create your first product to start selling on DhatchinaMart.</p>
            <a href="${ctx}/seller/product/create" class="btn" style="margin-top:14px;">Create Product</a>
        </div>
    </c:when>
    <c:otherwise>
        <div class="grid-products">
            <c:forEach var="p" items="${products}">
                <div class="card">
                    <img class="card-img" src="<c:out value='${p.imageUrl}'/>" alt="<c:out value='${p.name}'/>"
                         onerror="this.src='${ctx}/images/placeholder.png'">
                    <div class="card-body">
                        <span class="card-title"><c:out value="${p.name}"/></span>
                        <div class="card-meta">
                            <span><c:out value="${p.category}"/></span>
                            <span class="price">₹ <fmt:formatNumber value="${p.price}" type="number" minFractionDigits="2" maxFractionDigits="2"/></span>
                        </div>
                        <div class="card-meta">
                            <span>Stock: <c:out value="${p.stockQty}"/></span>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:otherwise>
</c:choose>

<%@ include file="/WEB-INF/jsp/fragments/footer.jspf" %>
