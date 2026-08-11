package com.dhatchina.dhatchinamart.service;

import com.dhatchina.dhatchinamart.dao.ProductDAO;
import com.dhatchina.dhatchinamart.exception.NotFoundException;
import com.dhatchina.dhatchinamart.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductDAO productDAO;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productDAO);
    }

    @Test
    void getByIdReturnsProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Wireless Bluetooth Headphones");
        product.setPrice(new BigDecimal("1299.00"));
        when(productDAO.findById(1L)).thenReturn(Optional.of(product));

        Product found = productService.getById(1L);

        assertEquals(1L, found.getId());
        assertEquals("Wireless Bluetooth Headphones", found.getName());
    }

    @Test
    void getByIdForMissingProductThrows() {
        when(productDAO.findById(999L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> productService.getById(999L));
    }

    @Test
    void browseDelegatesSearchAndCategoryToDao() {
        when(productDAO.find("head", "Electronics")).thenReturn(List.of(new Product()));

        List<Product> products = productService.browse("head", "Electronics");

        assertEquals(1, products.size());
        verify(productDAO).find("head", "Electronics");
    }
}
