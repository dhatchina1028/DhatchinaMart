package com.dhatchina.dhatchinamart.service;

import com.dhatchina.dhatchinamart.dao.CartDAO;
import com.dhatchina.dhatchinamart.dao.OrderDAO;
import com.dhatchina.dhatchinamart.dao.ProductDAO;
import com.dhatchina.dhatchinamart.exception.NotFoundException;
import com.dhatchina.dhatchinamart.exception.ValidationException;
import com.dhatchina.dhatchinamart.model.CartItem;
import com.dhatchina.dhatchinamart.model.Order;
import com.dhatchina.dhatchinamart.model.OrderItem;
import com.dhatchina.dhatchinamart.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private static final String STATUS_PENDING = "PENDING";

    private final DataSource dataSource;
    private final OrderDAO orderDAO;
    private final CartDAO cartDAO;
    private final ProductDAO productDAO;

    public OrderService(DataSource dataSource, OrderDAO orderDAO, CartDAO cartDAO, ProductDAO productDAO) {
        this.dataSource = dataSource;
        this.orderDAO = orderDAO;
        this.cartDAO = cartDAO;
        this.productDAO = productDAO;
    }

    public Order placeOrder(long buyerId) {
        List<CartItem> cartItems = cartDAO.findByUserId(buyerId);
        if (cartItems.isEmpty()) {
            throw new ValidationException("Your cart is empty");
        }

        Order order = new Order();
        order.setBuyerId(buyerId);
        order.setStatus(STATUS_PENDING);

        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : cartItems) {
            Product product = productDAO.findById(item.getProductId())
                    .orElseThrow(() -> new ValidationException("A product in your cart is no longer available"));
            if (product.getStockQty() < item.getQuantity()) {
                throw new ValidationException(
                        "Only " + product.getStockQty() + " unit(s) of \"" + product.getName() + "\" are available in stock");
            }
            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        order.setTotalAmount(total);

        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try {
                long orderId = orderDAO.insert(conn, order);
                order.setId(orderId);
                for (CartItem item : cartItems) {
                    Product product = productDAO.findById(item.getProductId()).orElseThrow();
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrderId(orderId);
                    orderItem.setProductId(item.getProductId());
                    orderItem.setQuantity(item.getQuantity());
                    orderItem.setUnitPrice(product.getPrice());
                    orderDAO.insertItem(conn, orderItem);

                    if (!productDAO.decrementStock(conn, item.getProductId(), item.getQuantity())) {
                        throw new ValidationException(
                                "Insufficient stock for \"" + product.getName() + "\". Your cart has been kept");
                    }
                }
                cartDAO.clearForUser(conn, buyerId);
                conn.commit();
                log.info("Order placed: id={}, buyerId={}, total={}", orderId, buyerId, total);
                return order;
            } catch (Exception e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to place order", e);
        }
    }

    public List<Order> ordersForBuyer(long buyerId) {
        return orderDAO.findByBuyer(buyerId);
    }

    public Order getOrderForBuyer(long orderId, long buyerId) {
        Order order = orderDAO.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));
        if (order.getBuyerId() != buyerId) {
            throw new NotFoundException("Order not found");
        }
        return order;
    }

    public List<OrderItem> itemsForOrder(long orderId) {
        return orderDAO.findItemsByOrderId(orderId);
    }
}
