package rexxie.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import rexxie.db.modules.jdbc.template.user.exceptions.UserIsAlreadyExistsException;
import rexxie.db.modules.jdbc.template.user.exceptions.UserNotFoundException;
import rexxie.db.modules.jdbc.template.user.models.User;
import rexxie.db.modules.jdbc.template.user.services.UserService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper json;

    @MockBean
    private UserService userService;

    @Test
    public void getUserByIdWithSucceedResult() throws Exception {
        User mockUser = User.createBuilder()
                .setId(1)
                .setEmail("test@email.com")
                .build();

        when(userService.getUserById(mockUser.getId())).thenReturn(mockUser);
        ResponseEntity<User> responseUser = restTemplate.getForEntity("/users/{id}", User.class, mockUser.getId());

        assertThat(responseUser.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseUser.getBody(), is(mockUser));
    }

    @Test
    public void getUserByIdWithThrowsUserNotFoundException() throws Exception {
        User mockUser = User.createBuilder()
                .setId(1)
                .setEmail("test@email.com")
                .build();

        when(userService.getUserById(mockUser.getId())).thenThrow(new UserNotFoundException(mockUser.getId()));
        ResponseEntity<User> responseUser = restTemplate.getForEntity("/users/{id}", User.class, mockUser.getId());

        assertThat(responseUser.getStatusCode(), is(HttpStatus.NO_CONTENT));
    }

    @Test
    public void getUserListWithSucceedResult() throws Exception {
        Integer limit = 1000, offset = 0;
        List<User> mockUserList = Arrays.asList(
                User.createBuilder()
                        .setId(1)
                        .setEmail("test1@email.com")
                        .build(),
                User.createBuilder()
                        .setId(2)
                        .setEmail("test2@email.com")
                        .build()
        );

        when(userService.getUserList(limit, offset)).thenReturn(mockUserList);
        ResponseEntity<User[]> responseUser = restTemplate.getForEntity("/users", User[].class);

        assertThat(responseUser.getStatusCode(), is(HttpStatus.OK));
        assertThat(Arrays.asList(responseUser.getBody()), is(mockUserList));
    }

    @Test
    public void createUserWithThrowsUserIsAlreadyExistsException() throws Exception {

        User mockUser = User.createBuilder()
                .setEmail("test1@email.com")
                .build();

        User mockCreatedUser = User.createBuilder()
                .setId(1)
                .setEmail("test1@email.com")
                .build();

        when(userService.create(mockUser)).thenThrow(new UserIsAlreadyExistsException(mockUser));

        ResponseEntity<User> userResponseEntity = restTemplate.postForEntity("/users", mockUser, User.class);

        assertThat(userResponseEntity.getStatusCode(), is(HttpStatus.NOT_FOUND));
        assertThat(userResponseEntity.getBody(), not(mockCreatedUser));
    }

    @Test
    public void createUserWithSucceedResult() throws Exception {

        User mockUser = User.createBuilder()
                .setEmail("test1@email.com")
                .build();

        User mockCreatedUser = User.createBuilder()
                .setId(1)
                .setEmail("test1@email.com")
                .build();

        when(userService.create(mockUser)).thenReturn(mockCreatedUser);

        ResponseEntity<User> userResponseEntity = restTemplate.postForEntity("/users", mockUser, User.class);

        assertThat(userResponseEntity.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(userResponseEntity.getBody(), is(mockCreatedUser));
    }

}