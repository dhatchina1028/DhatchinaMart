package com.dhatchina.dhatchinamart.util;

import com.dhatchina.dhatchinamart.dao.CartDAO;
import com.dhatchina.dhatchinamart.dao.OrderDAO;
import com.dhatchina.dhatchinamart.dao.ProductDAO;
import com.dhatchina.dhatchinamart.dao.UserDAO;
import com.dhatchina.dhatchinamart.dao.impl.CartDAOImpl;
import com.dhatchina.dhatchinamart.dao.impl.OrderDAOImpl;
import com.dhatchina.dhatchinamart.dao.impl.ProductDAOImpl;
import com.dhatchina.dhatchinamart.dao.impl.UserDAOImpl;
import com.dhatchina.dhatchinamart.service.AdminService;
import com.dhatchina.dhatchinamart.service.AuthService;
import com.dhatchina.dhatchinamart.service.CartService;
import com.dhatchina.dhatchinamart.service.OrderService;
import com.dhatchina.dhatchinamart.service.ProductService;
import com.dhatchina.dhatchinamart.service.SellerService;

import javax.sql.DataSource;

public final class ServiceRegistry {

    private static AuthService authService;
    private static ProductService productService;
    private static CartService cartService;
    private static OrderService orderService;
    private static SellerService sellerService;
    private static AdminService adminService;

    private ServiceRegistry() {
    }

    public static synchronized void init() {
        if (authService != null) {
            return;
        }
        DataSource dataSource = DbUtil.getDataSource();
        UserDAO userDAO = new UserDAOImpl(dataSource);
        ProductDAO productDAO = new ProductDAOImpl(dataSource);
        CartDAO cartDAO = new CartDAOImpl(dataSource);
        OrderDAO orderDAO = new OrderDAOImpl(dataSource);

        authService = new AuthService(userDAO);
        productService = new ProductService(productDAO);
        cartService = new CartService(cartDAO, productDAO);
        orderService = new OrderService(dataSource, orderDAO, cartDAO, productDAO);
        sellerService = new SellerService(productDAO);
        adminService = new AdminService(userDAO, productDAO, orderDAO);
    }

    public static synchronized void reset() {
        authService = null;
        productService = null;
        cartService = null;
        orderService = null;
        sellerService = null;
        adminService = null;
    }

    public static AuthService getAuthService() {
        return authService;
    }

    public static ProductService getProductService() {
        return productService;
    }

    public static CartService getCartService() {
        return cartService;
    }

    public static OrderService getOrderService() {
        return orderService;
    }

    public static SellerService getSellerService() {
        return sellerService;
    }

    public static AdminService getAdminService() {
        return adminService;
    }
}
