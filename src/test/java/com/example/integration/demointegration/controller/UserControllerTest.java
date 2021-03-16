package com.example.integration.demointegration.controller;

import com.example.integration.demointegration.DemoIntegrationApplication;
import com.example.integration.demointegration.dto.AddUserDTO;
import com.example.integration.demointegration.dto.RESTResponse;
import com.example.integration.demointegration.dto.UpdateUserDTO;
import com.example.integration.demointegration.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest(classes = DemoIntegrationApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    private static final String MAP_KEY_ID = "id";
    private static final String MAP_KEY_NAME = "name";

    private static final String NAME_FIRST = "first";
    private static final Integer ID_FIRST = 1;
    private static final String NAME_SECOND = "second";
    private static final Integer ID_SECOND = 2;
    private static final String NAME_THIRD = "third";
    private static final Integer ID_THIRD = 3;

    private static final String UPDATED_POSTFIX = "_updated";

    private static final String NAME_FIRST_UPDATED = NAME_FIRST + UPDATED_POSTFIX;
    private static final String NAME_SECOND_UPDATED = NAME_SECOND + UPDATED_POSTFIX;
    private static final String NAME_THIRD_UPDATED = NAME_THIRD + UPDATED_POSTFIX;

    private static final String HOST = "http://localhost:";
    private static final String URI_GET_ALL = "/get/all";
    private static final String URI_ADD = "/add";
    private static final String URI_UPDATE = "/update/";
    private static final String URI_DELETE = "/delete/";

    private static final Integer ONE_RAW_AFFECTED = 1;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @SqlGroup({
            @Sql(value = "classpath:User/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:User/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:User/drop.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    @Test
    public void testGetAllUsers() {
        assertEquals(getOriginalUserList(), getAllUsers());
    }

    @SqlGroup({
            @Sql(value = "classpath:User/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:User/drop.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    @Test
    public void testAddUser() {
        assertTrue(getAllUsers().isEmpty());

        Integer response = (Integer) this.testRestTemplate.postForEntity(HOST + this.port + URI_ADD,
                new AddUserDTO(NAME_FIRST), RESTResponse.class).getBody().getResponse();
        assertEquals(ONE_RAW_AFFECTED, response);

        response = (Integer) this.testRestTemplate.postForEntity(HOST + this.port + URI_ADD,
                new AddUserDTO(NAME_SECOND), RESTResponse.class).getBody().getResponse();
        assertEquals(ONE_RAW_AFFECTED, response);

        assertEquals(getFirstAndSecondUser(), getAllUsers());
    }

    @SqlGroup({
            @Sql(value = "classpath:User/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:User/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:User/drop.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    @Test
    public void testUpdateUser() {
        assertEquals(getOriginalUserList(), getAllUsers());
        this.testRestTemplate.put(HOST + port + URI_UPDATE + ID_FIRST, new UpdateUserDTO(NAME_FIRST_UPDATED));
        this.testRestTemplate.put(HOST + port + URI_UPDATE + ID_SECOND, new UpdateUserDTO(NAME_SECOND_UPDATED));
        this.testRestTemplate.put(HOST + port + URI_UPDATE + ID_THIRD, new UpdateUserDTO(NAME_THIRD_UPDATED));
        assertEquals(getUpdatedUserList(), getAllUsers());
    }

    @SqlGroup({
            @Sql(value = "classpath:User/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:User/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:User/drop.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    @Test
    public void testDeleteUser() {
        assertEquals(getOriginalUserList(), getAllUsers());

        this.testRestTemplate.delete(HOST + port + URI_DELETE + ID_FIRST);
        assertEquals(getSecondAndThirdUser(), getAllUsers());

        this.testRestTemplate.delete(HOST + port + URI_DELETE + ID_SECOND);
        assertEquals(getThirdUser(), getAllUsers());
    }

    private List<User> getAllUsers() {
        RESTResponse restResponse = this.testRestTemplate
                .getForObject(HOST + this.port + URI_GET_ALL, RESTResponse.class);
        return convertResponseToUserList(restResponse);
    }

    private List<User> getOriginalUserList() {
        return Arrays.asList(
                new User(ID_FIRST, NAME_FIRST),
                new User(ID_SECOND, NAME_SECOND),
                new User(ID_THIRD, NAME_THIRD)
        );
    }

    private List<User> getUpdatedUserList() {
        return Arrays.asList(
                new User(ID_FIRST, NAME_FIRST_UPDATED),
                new User(ID_SECOND, NAME_SECOND_UPDATED),
                new User(ID_THIRD, NAME_THIRD_UPDATED)
        );
    }

    private List<User> getSecondAndThirdUser() {
        return Arrays.asList(
                new User(ID_SECOND, NAME_SECOND),
                new User(ID_THIRD, NAME_THIRD)
        );
    }

    private List<User> getFirstAndSecondUser() {
        return Arrays.asList(
                new User(ID_FIRST, NAME_FIRST),
                new User(ID_SECOND, NAME_SECOND)
        );
    }

    private List<User> getThirdUser() {
        return Arrays.asList(new User(ID_THIRD, NAME_THIRD));
    }

    private List<User> convertResponseToUserList(RESTResponse restResponse) {
        List initialList = (List) restResponse.getResponse();
        List<User> resultList = new ArrayList<>(initialList.size());
        for (Object o : initialList) {
            Map<String, Object> map = (Map<String, Object>) o;
            resultList.add(new User(Integer.parseInt(map.get(MAP_KEY_ID).toString()), (String) map.get(MAP_KEY_NAME)));
        }
        return resultList;
    }
}