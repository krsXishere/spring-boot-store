package cloud.ikis.store.controllers;

import cloud.ikis.store.dtos.UserDto;
import cloud.ikis.store.entities.User;
import cloud.ikis.store.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    private final UserService userService;

    public UserController( UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @PostMapping
    public User createUser(@RequestBody UserDto userDto) {
        return userService.create(userDto);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable String id, @RequestBody UserDto userDto) {
        return userService.update(id, userDto);
    }

    @PatchMapping("/{id}")
    public User updatePatchUser(@PathVariable String id, @RequestBody UserDto userDto) {
        return userService.updatePatch(id, userDto);
    }

    @PatchMapping("delete/{id}")
    public HttpStatus softDeleteUser(@PathVariable String id) {
        if (userService.softDelete(id)) {
            return HttpStatus.OK;
        }

        return HttpStatus.BAD_REQUEST;
    }
}
