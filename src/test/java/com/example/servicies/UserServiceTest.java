package com.example.servicies;

import com.example.DTOs.UserInDTO;
import com.example.VaadinCrudApplicationTests;
import org.apache.catalina.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by adria on 3/27/2017.
 */
public class UserServiceTest extends VaadinCrudApplicationTests {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Autowired
    private UserService userService;

    @Test
    public void create() throws Exception {
        UserInDTO userInDTO = new UserInDTO();

        userInDTO.setName("Adrian");
        userInDTO.setDescription("usuario #1");

        userService.create(userInDTO);
    }

    @Test
    public void update() throws Exception {
    }

    @Test
    public void softDelete() throws Exception {
    }

    @Test
    public void delete() throws Exception {
    }

    @Test
    public void listAll() throws Exception {
    }

    @Test
    public void search() throws Exception {
    }

    @Test
    public void findById() throws Exception {
    }

    @Test
    public void findByUuid() throws Exception {
    }

    @Test
    public void findByName() throws Exception {
    }

}