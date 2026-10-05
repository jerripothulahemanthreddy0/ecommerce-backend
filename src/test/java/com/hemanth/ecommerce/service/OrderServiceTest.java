package com.hemanth.ecommerce.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hemanth.ecommerce.entity.Order;
import com.hemanth.ecommerce.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void testCreateOrder() {

        Order order = new Order(
                null,
                85000,
                "PLACED"
        );

        Order savedOrder = new Order(
                1L,
                85000,
                "PLACED"
        );

        when(orderRepository.save(order))
                .thenReturn(savedOrder);

        Order result = orderService.createOrder(order);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(85000, result.getTotalAmount());
        assertEquals("PLACED", result.getStatus());

        verify(orderRepository).save(order);
    }

    @Test
    void testGetOrderById() {

        Order order = new Order(
                1L,
                85000,
                "PLACED"
        );

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        Order result = orderService.getOrderById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(85000, result.getTotalAmount());
        assertEquals("PLACED", result.getStatus());

        verify(orderRepository).findById(1L);
    }

    @Test
    void testGetAllOrders() {

        Order order1 = new Order(
                1L,
                85000,
                "PLACED"
        );

        Order order2 = new Order(
                2L,
                90000,
                "CONFIRMED"
        );

        when(orderRepository.findAll())
                .thenReturn(List.of(order1, order2));

        List<Order> result = orderService.getAllOrders();

        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals(85000, result.get(0).getTotalAmount());
        assertEquals("PLACED", result.get(0).getStatus());

        assertEquals(90000, result.get(1).getTotalAmount());
        assertEquals("CONFIRMED", result.get(1).getStatus());

        verify(orderRepository).findAll();
    }

    @Test
    void testUpdateOrder() {

        Order existingOrder = new Order(
                1L,
                85000,
                "PLACED"
        );

        Order updatedOrder = new Order(
                null,
                90000,
                "CONFIRMED"
        );

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(existingOrder));

        when(orderRepository.save(existingOrder))
                .thenReturn(existingOrder);

        Order result = orderService.updateOrder(1L, updatedOrder);

        assertNotNull(result);
        assertEquals(90000, result.getTotalAmount());
        assertEquals("CONFIRMED", result.getStatus());

        verify(orderRepository).findById(1L);
        verify(orderRepository).save(existingOrder);
    }

    @Test
    void testDeleteOrder() {

        doNothing().when(orderRepository).deleteById(1L);

        orderService.deleteOrder(1L);

        verify(orderRepository).deleteById(1L);
    }
}