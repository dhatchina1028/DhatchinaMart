<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="title" value="Order #${order.id}"/>
<%@ include file="/WEB-INF/jsp/fragments/header.jspf" %>

<h2 class="page-title">Order Details</h2>
<p class="page-subtitle">
    Order <strong>#<c:out value="${order.id}"/></strong>
    &middot; <fmt:formatDate value="${order.createdAt}" pattern="dd MMM yyyy, HH:mm"/>
    &middot; Status: <span class="badge badge-pending"><c:out value="${order.status}"/></span>
</p>

<div class="table-wrap">
    <table>
        <thead>
        <tr>
            <th>Product</th>
            <th>Quantity</th>
            <th>Unit Price</th>
            <th>Subtotal</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${items}">
            <tr>
                <td><a href="${ctx}/product?id=${item.productId}"><c:out value="${item.productName}"/></a></td>
                <td><c:out value="${item.quantity}"/></td>
                <td>₹ <fmt:formatNumber value="${item.unitPrice}" type="number" minFractionDigits="2" maxFractionDigits="2"/></td>
                <td>₹ <fmt:formatNumber value="${item.subtotal}" type="number" minFractionDigits="2" maxFractionDigits="2"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<div class="cart-summary">
    <span class="cart-total">Total: ₹ <fmt:formatNumber value="${order.totalAmount}" type="number" minFractionDigits="2" maxFractionDigits="2"/></span>
</div>
<p style="margin-top:16px;"><a href="${ctx}/orders" class="btn btn-secondary">Back to Order History</a></p>

<%@ include file="/WEB-INF/jsp/fragments/footer.jspf" %>
