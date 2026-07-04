package cloud.ikis.store.services;

import cloud.ikis.store.dtos.UserDto;
import cloud.ikis.store.models.User;
import cloud.ikis.store.repositories.UserRepository;
import cloud.ikis.store.security.password.PasswordService;
import org.springframework.stereotype.Service;

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
            throw new IllegalArgumentException("Email already exist");
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
        String hashedPassword = passwordService.hash(userDto.getPassword());
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("user not found"));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(hashedPassword);

        return userRepository.save(user);
    }

    public User updatePatch(String id, UserDto userDto) {
        String hashedPassword = passwordService.hash(userDto.getPassword());
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("user not found"));
        if (userDto.getName() != null) user.setName(userDto.getName());
        if (userDto.getEmail() != null) user.setEmail(userDto.getEmail());
        if (userDto.getPassword() != null) user.setPassword(hashedPassword);

        return userRepository.save(user);
    }
}
