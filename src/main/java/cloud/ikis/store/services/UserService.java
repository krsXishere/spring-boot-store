package cloud.ikis.store.services;

import cloud.ikis.store.dtos.ResponseDto;
import cloud.ikis.store.dtos.UserDto;
import cloud.ikis.store.entities.User;
import cloud.ikis.store.repositories.UserRepository;
import cloud.ikis.store.security.password.PasswordService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordService passwordService;

    public UserService(UserRepository userRepository, PasswordService passwordService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
    }

    public ResponseDto.response findAll() {
        return ResponseDto.response.success("success", userRepository.findAll());
    }

    public ResponseDto.response create(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exist");
        }

        String hashedPassword = passwordService.hash(userDto.getPassword());
        User user = new User(UUID.randomUUID(),
                userDto.getName(),
                userDto.getEmail(),
                hashedPassword,
                Instant.now(),
                Instant.now());

        return ResponseDto.response.success("success", userRepository.save(user));
    }

    public ResponseDto.response update(String id, UserDto userDto) {
        User user = findUserById(id);
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        if (userDto.getPassword() != null) {
            String hashedPassword = passwordService.hash(userDto.getPassword());
            user.setPassword(hashedPassword);
        }
        user.setUpdatedAt(Instant.now());

        return ResponseDto.response.success("success", userRepository.save(user));
    }

    public ResponseDto.response updatePatch(String id, UserDto userDto) {
        User user = findUserById(id);
        if (userDto.getName() != null) user.setName(userDto.getName());
        if (userDto.getEmail() != null) user.setEmail(userDto.getEmail());
        if (userDto.getPassword() != null) {
            String hashedPassword = passwordService.hash(userDto.getPassword());
            user.setPassword(hashedPassword);
        }
        user.setUpdatedAt(Instant.now());

        return ResponseDto.response.success("success", userRepository.save(user));
    }

    public ResponseDto.response softDelete(String id) {
        User user = findUserById(id);
        user.setDeletedAt(Instant.now());
        userRepository.save(user);

        return ResponseDto.response.success("success", null);
    }

    private User findUserById(String id) {
        UUID uuid = UUID.fromString(id);
        return userRepository.findById(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }
}
