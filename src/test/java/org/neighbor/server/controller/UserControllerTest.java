package org.neighbor.server.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neighbor.api.user.UserDto;
import org.neighbor.server.MainApp;
import org.neighbor.server.entity.UserEntity;
import org.neighbor.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT, classes = MainApp.class)
@Transactional
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @Autowired
    private UserRepository neighborUserRepository;
    public static final String USER_PHONE = "23089742139847";
    public static final String LOGIN_TESTER = "Tester";

    @Test
    @Rollback
    public void shouldInsertUser() {
        String pinCode = UUID.randomUUID().toString();
        Date createdOn = new Date();

        UserEntity user = new UserEntity();
        user.setLogin(LOGIN_TESTER);
        user.setCreatedOn(createdOn);
        user.setPinCode(pinCode);
        user.setUserPhone(USER_PHONE);
        UserEntity neighborUser = neighborUserRepository.save(user);
        UserDto userById = userController.getUserById("aaa:111:bbb:ccc:Tester").getBody();

        UserDto expectedUserDto = new UserDto();
        expectedUserDto.setLogin(LOGIN_TESTER);
        expectedUserDto.setCreatedOn(createdOn);
        expectedUserDto.setPinCode(pinCode);
        expectedUserDto.setUserPhone(USER_PHONE);
        Assert.assertEquals(expectedUserDto, userById);
    }
}
