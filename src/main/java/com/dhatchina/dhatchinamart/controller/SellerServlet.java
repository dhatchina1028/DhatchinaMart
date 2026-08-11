package com.dhatchina.dhatchinamart.controller;

import com.dhatchina.dhatchinamart.exception.AppException;
import com.dhatchina.dhatchinamart.model.Product;
import com.dhatchina.dhatchinamart.model.User;
import com.dhatchina.dhatchinamart.service.SellerService;
import com.dhatchina.dhatchinamart.util.SessionUtil;
import com.dhatchina.dhatchinamart.util.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/seller", "/seller/product/create"})
public class SellerServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(SellerServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if ("/seller/product/create".equals(request.getServletPath())) {
            request.setAttribute("categories", ServiceRegistry.getProductService().categories());
            request.getRequestDispatcher("/WEB-INF/jsp/create-product.jsp").forward(request, response);
        } else {
            handleDashboard(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if ("/seller/product/create".equals(request.getServletPath())) {
            handleCreateProduct(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void handleDashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = SessionUtil.getUser(request);
        SellerService sellerService = ServiceRegistry.getSellerService();
        List<Product> products = sellerService.productsForSeller(user.getId());
        request.setAttribute("products", products);
        request.setAttribute("productCount", products.size());
        request.getRequestDispatcher("/WEB-INF/jsp/seller-dashboard.jsp").forward(request, response);
    }

    private void handleCreateProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = SessionUtil.getUser(request);
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String price = request.getParameter("price");
        String stock = request.getParameter("stock");
        String category = request.getParameter("category");
        String imageUrl = request.getParameter("imageUrl");
        try {
            ServiceRegistry.getSellerService().createProduct(
                    user.getId(), name, description, price, stock, category, imageUrl);
            response.sendRedirect(request.getContextPath() + "/seller?msg=created");
        } catch (AppException e) {
            request.setAttribute("error", e.getMessage());
            request.setAttribute("name", name);
            request.setAttribute("description", description);
            request.setAttribute("price", price);
            request.setAttribute("stock", stock);
            request.setAttribute("category", category);
            request.setAttribute("imageUrl", imageUrl);
            request.setAttribute("categories", ServiceRegistry.getProductService().categories());
            request.getRequestDispatcher("/WEB-INF/jsp/create-product.jsp").forward(request, response);
        } catch (Exception e) {
            log.error("Product creation failed", e);
            request.setAttribute("error", "Something went wrong. Please try again.");
            request.getRequestDispatcher("/WEB-INF/jsp/create-product.jsp").forward(request, response);
        }
    }
}
