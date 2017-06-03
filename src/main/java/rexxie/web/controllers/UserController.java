package rexxie.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rexxie.db.modules.jdbc.template.user.exceptions.UserIsAlreadyExistsException;
import rexxie.db.modules.jdbc.template.user.exceptions.UserNotFoundException;
import rexxie.db.modules.jdbc.template.user.models.User;
import rexxie.db.modules.jdbc.template.user.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUserById(@PathVariable("id") Integer id) throws UserNotFoundException {
        return ResponseEntity.ok().body(userService.getUserById(id));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUserList(
            @RequestParam(value = "limit", defaultValue = "1000", required = false) Integer limit,
            @RequestParam(value = "offset", defaultValue = "0", required = false) Integer offset
    ) {
        return ResponseEntity.ok().body(userService.getUserList(limit, offset));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestBody User inputUser
    ) throws UserIsAlreadyExistsException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(inputUser));
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
