package com.hemanth.ecommerce.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hemanth.ecommerce.entity.Product;
import com.hemanth.ecommerce.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void testGetProductById() {

        Product product = new Product(
                1L,
                "Laptop",
                "Gaming Laptop",
                75000,
                10
        );

        when(productRepository.findById(1L))
                .thenReturn(java.util.Optional.of(product));

        Product result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals("Laptop", result.getName());
        assertEquals(75000, result.getPrice());
    }

    @Test
    void testCreateProduct() {

        Product product = new Product(
                null,
                "Phone",
                "Smart Phone",
                30000,
                20
        );

        Product savedProduct = new Product(
                1L,
                "Phone",
                "Smart Phone",
                30000,
                20
        );

        when(productRepository.save(product))
                .thenReturn(savedProduct);

        Product result = productService.createProduct(product);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Phone", result.getName());
        assertEquals(30000, result.getPrice());
    }

    @Test
    void testGetAllProducts() {

        Product product1 = new Product(
                1L,
                "Laptop",
                "Gaming Laptop",
                75000,
                10
        );

        Product product2 = new Product(
                2L,
                "Phone",
                "Smart Phone",
                30000,
                20
        );

        when(productRepository.findAll())
                .thenReturn(java.util.List.of(product1, product2));

        java.util.List<Product> result = productService.getAllProducts();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Laptop", result.get(0).getName());
        assertEquals("Phone", result.get(1).getName());
    }

    @Test
    void testUpdateProduct() {

        Product existingProduct = new Product(
                1L,
                "Laptop",
                "Old Laptop",
                75000,
                10
        );

        Product updatedProduct = new Product(
                null,
                "Laptop Pro",
                "New Gaming Laptop",
                90000,
                15
        );

        when(productRepository.findById(1L))
                .thenReturn(java.util.Optional.of(existingProduct));

        when(productRepository.save(existingProduct))
                .thenReturn(existingProduct);

        Product result = productService.updateProduct(1L, updatedProduct);

        assertNotNull(result);
        assertEquals("Laptop Pro", result.getName());
        assertEquals("New Gaming Laptop", result.getDescription());
        assertEquals(90000, result.getPrice());
        assertEquals(15, result.getStock());
    }
}