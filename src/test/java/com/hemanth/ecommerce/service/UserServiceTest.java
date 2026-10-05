package com.hemanth.ecommerce.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hemanth.ecommerce.entity.User;
import com.hemanth.ecommerce.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testCreateUser() {

        User user = new User(
                null,
                "Hemanth",
                "hemanth@gmail.com",
                "123456"
        );

        User savedUser = new User(
                1L,
                "Hemanth",
                "hemanth@gmail.com",
                "123456"
        );

        when(userRepository.save(user))
                .thenReturn(savedUser);

        User result = userService.createUser(user);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Hemanth", result.getName());
        assertEquals("hemanth@gmail.com", result.getEmail());

        verify(userRepository).save(user);
    }

    @Test
    void testGetUserById() {

        User user = new User(
                1L,
                "Hemanth",
                "hemanth@gmail.com",
                "123456"
        );

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Hemanth", result.getName());
        assertEquals("hemanth@gmail.com", result.getEmail());

        verify(userRepository).findById(1L);
    }

    @Test
    void testGetAllUsers() {

        User user1 = new User(
                1L,
                "Hemanth",
                "hemanth@gmail.com",
                "123456"
        );

        User user2 = new User(
                2L,
                "Rahul",
                "rahul@gmail.com",
                "654321"
        );

        when(userRepository.findAll())
                .thenReturn(List.of(user1, user2));

        List<User> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals("Hemanth", result.get(0).getName());
        assertEquals("Rahul", result.get(1).getName());

        verify(userRepository).findAll();
    }

    @Test
    void testUpdateUser() {

        User existingUser = new User(
                1L,
                "Hemanth",
                "old@gmail.com",
                "123456"
        );

        User updatedUser = new User(
                null,
                "Hemanth Reddy",
                "new@gmail.com",
                "654321"
        );

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(existingUser));

        when(userRepository.save(existingUser))
                .thenReturn(existingUser);

        User result = userService.updateUser(1L, updatedUser);

        assertNotNull(result);
        assertEquals("Hemanth Reddy", result.getName());
        assertEquals("new@gmail.com", result.getEmail());
        assertEquals("654321", result.getPassword());

        verify(userRepository).findById(1L);
        verify(userRepository).save(existingUser);
    }

    @Test
    void testDeleteUser() {

        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository).deleteById(1L);
    }
}