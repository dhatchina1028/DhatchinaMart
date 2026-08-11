package com.dhatchina.dhatchinamart.service;

import com.dhatchina.dhatchinamart.dao.ProductDAO;
import com.dhatchina.dhatchinamart.exception.NotFoundException;
import com.dhatchina.dhatchinamart.model.Product;

import java.util.List;

public class ProductService {

    private final ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public List<Product> browse(String keyword, String category) {
        return productDAO.find(keyword, category);
    }

    public List<String> categories() {
        return productDAO.findCategories();
    }

    public Product getById(long id) {
        return productDAO.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));
    }

    public List<Product> productsBySeller(long sellerId) {
        return productDAO.findBySeller(sellerId);
    }

    public long countBySeller(long sellerId) {
        return productDAO.countBySeller(sellerId);
    }
}
