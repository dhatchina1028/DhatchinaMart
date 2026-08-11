package com.dhatchina.dhatchinamart.service;

import com.dhatchina.dhatchinamart.dao.OrderDAO;
import com.dhatchina.dhatchinamart.dao.ProductDAO;
import com.dhatchina.dhatchinamart.dao.UserDAO;
import com.dhatchina.dhatchinamart.dto.AdminStats;
import com.dhatchina.dhatchinamart.model.User;

public class AdminService {

    private final UserDAO userDAO;
    private final ProductDAO productDAO;
    private final OrderDAO orderDAO;

    public AdminService(UserDAO userDAO, ProductDAO productDAO, OrderDAO orderDAO) {
        this.userDAO = userDAO;
        this.productDAO = productDAO;
        this.orderDAO = orderDAO;
    }

    public AdminStats getDashboardStats() {
        AdminStats stats = new AdminStats();
        stats.setTotalUsers(userDAO.countAll());
        stats.setTotalBuyers(userDAO.countByRole(User.Role.BUYER));
        stats.setTotalSellers(userDAO.countByRole(User.Role.SELLER));
        stats.setTotalProducts(productDAO.countAll());
        stats.setTotalOrders(orderDAO.countAll());
        return stats;
    }
}
