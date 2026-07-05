package cloud.ikis.store.services;

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

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User create(UserDto userDto) {
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

        return userRepository.save(user);
    }

    public User update(String id, UserDto userDto) {
        User user = findUserById(id);
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        if (userDto.getPassword() != null) {
            String hashedPassword = passwordService.hash(userDto.getPassword());
            user.setPassword(hashedPassword);
        }
        user.setUpdatedAt(Instant.now());

        return userRepository.save(user);
    }

    public User updatePatch(String id, UserDto userDto) {
        User user = findUserById(id);
        if (userDto.getName() != null) user.setName(userDto.getName());
        if (userDto.getEmail() != null) user.setEmail(userDto.getEmail());
        if (userDto.getPassword() != null) {
            String hashedPassword = passwordService.hash(userDto.getPassword());
            user.setPassword(hashedPassword);
        }
        user.setUpdatedAt(Instant.now());

        return userRepository.save(user);
    }

    public boolean softDelete(String id) {
        User user = findUserById(id);
        user.setDeletedAt(Instant.now());
        userRepository.save(user);

        return user.getDeletedAt() != null;
    }

    private User findUserById(String id) {
        UUID uuid = UUID.fromString(id);
        return userRepository.findById(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }
}
