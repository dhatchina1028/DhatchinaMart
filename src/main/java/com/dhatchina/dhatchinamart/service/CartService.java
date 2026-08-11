package com.dhatchina.dhatchinamart.service;

import com.dhatchina.dhatchinamart.dao.CartDAO;
import com.dhatchina.dhatchinamart.dao.ProductDAO;
import com.dhatchina.dhatchinamart.dto.CartLine;
import com.dhatchina.dhatchinamart.dto.CartView;
import com.dhatchina.dhatchinamart.exception.NotFoundException;
import com.dhatchina.dhatchinamart.exception.ValidationException;
import com.dhatchina.dhatchinamart.model.CartItem;
import com.dhatchina.dhatchinamart.model.Product;

import java.util.List;
import java.util.Optional;

public class CartService {

    private final CartDAO cartDAO;
    private final ProductDAO productDAO;

    public CartService(CartDAO cartDAO, ProductDAO productDAO) {
        this.cartDAO = cartDAO;
        this.productDAO = productDAO;
    }

    public void addToCart(long userId, long productId, int quantity) {
        Product product = loadProduct(productId);
        Optional<CartItem> existing = cartDAO.findByUserAndProduct(userId, productId);
        int newQuantity = existing.map(CartItem::getQuantity).orElse(0) + quantity;
        ensureWithinStock(product, newQuantity);

        if (existing.isPresent()) {
            cartDAO.updateQuantity(userId, productId, newQuantity);
        } else {
            CartItem item = new CartItem();
            item.setUserId(userId);
            item.setProductId(productId);
            item.setQuantity(quantity);
            cartDAO.insert(item);
        }
    }

    public void updateQuantity(long userId, long productId, int quantity) {
        if (quantity < 1) {
            throw new ValidationException("Quantity must be at least 1");
        }
        if (cartDAO.findByUserAndProduct(userId, productId).isEmpty()) {
            throw new NotFoundException("Item is not in your cart");
        }
        ensureWithinStock(loadProduct(productId), quantity);
        cartDAO.updateQuantity(userId, productId, quantity);
    }

    public void removeItem(long userId, long productId) {
        cartDAO.delete(userId, productId);
    }

    public CartView getCart(long userId) {
        CartView view = new CartView();
        for (CartItem item : cartDAO.findByUserId(userId)) {
            productDAO.findById(item.getProductId())
                    .ifPresent(product -> view.addLine(new CartLine(product, item.getQuantity())));
        }
        return view;
    }

    public int countItems(long userId) {
        return cartDAO.countByUser(userId);
    }

    private Product loadProduct(long productId) {
        return productDAO.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));
    }

    private void ensureWithinStock(Product product, int quantity) {
        if (product.getStockQty() < quantity) {
            throw new ValidationException(
                    "Only " + product.getStockQty() + " unit(s) of \"" + product.getName() + "\" are available in stock");
        }
    }
}
