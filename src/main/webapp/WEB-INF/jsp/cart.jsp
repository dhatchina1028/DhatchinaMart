<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="title" value="Cart"/>
<%@ include file="/WEB-INF/jsp/fragments/header.jspf" %>

<h2 class="page-title">Your Cart</h2>
<p class="page-subtitle">Review items, adjust quantities and proceed to checkout</p>

<c:if test="${param.msg == 'stock'}">
    <div class="alert alert-error">Some items could not be ordered at the requested quantity. Please review your cart.</div>
</c:if>
<c:if test="${param.msg == 'empty'}">
    <div class="alert alert-error">Your cart is empty. Add some products first.</div>
</c:if>
<c:if test="${param.msg == 'error'}">
    <div class="alert alert-error">Something went wrong during checkout. Please try again.</div>
</c:if>

<c:choose>
    <c:when test="${cart.isEmpty()}">
        <div class="empty-state">
            <h3>Your cart is empty</h3>
            <p>Browse the marketplace and add products to your cart.</p>
            <a href="${ctx}/products" class="btn" style="margin-top:14px;">Browse Products</a>
        </div>
    </c:when>
    <c:otherwise>
        <script>window.CONTEXT_PATH = "${ctx}";</script>
        <script src="${ctx}/js/cart.js"></script>
        <div class="table-wrap">
            <table>
                <thead>
                <tr>
                    <th>Product</th>
                    <th>Unit Price</th>
                    <th>Quantity</th>
                    <th>Subtotal</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="line" items="${cart.lines}">
                    <tr data-product-id="${line.product.id}" data-price="${line.product.price}" data-stock="${line.product.stockQty}">
                        <td>
                            <a href="${ctx}/product?id=${line.product.id}" style="font-weight:600;">
                                <c:out value="${line.product.name}"/>
                            </a>
                        </td>
                        <td>₹ <fmt:formatNumber value="${line.product.price}" type="number" minFractionDigits="2" maxFractionDigits="2"/></td>
                        <td>
                            <button type="button" class="qty-btn qty-minus"
                                    <c:if test="${line.quantity <= 1}">disabled</c:if>>-</button>
                            <input class="qty-input" type="number" value="${line.quantity}" readonly>
                            <button type="button" class="qty-btn qty-plus"
                                    <c:if test="${line.quantity >= line.product.stockQty}">disabled</c:if>>+</button>
                        </td>
                        <td class="subtotal">₹ <fmt:formatNumber value="${line.subtotal}" type="number" minFractionDigits="2" maxFractionDigits="2"/></td>
                        <td>
                            <button type="button" class="btn btn-sm btn-danger remove-item" data-product-id="${line.product.id}">
                                Remove
                            </button>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="cart-summary">
            <span class="form-hint">Total items: <c:out value="${cart.count}"/></span>
            <span class="cart-total">Total: <span id="cart-total">₹ <fmt:formatNumber value="${cart.total}" type="number" minFractionDigits="2" maxFractionDigits="2"/></span></span>
            <a href="${ctx}/checkout" class="btn">Proceed to Checkout</a>
        </div>
    </c:otherwise>
</c:choose>

<%@ include file="/WEB-INF/jsp/fragments/footer.jspf" %>
