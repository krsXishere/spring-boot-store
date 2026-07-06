package cloud.ikis.store.services;

import cloud.ikis.store.dtos.AuthDto;
import cloud.ikis.store.dtos.ResponseDto;
import cloud.ikis.store.entities.User;
import cloud.ikis.store.repositories.UserRepository;
import cloud.ikis.store.security.jwt.JwtService;
import cloud.ikis.store.security.password.PasswordService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordService passwordService;
    private final UserRepository userRepository;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService, PasswordService passwordService, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordService = passwordService;
        this.userRepository = userRepository;
    }

    public ResponseDto.response signIn(AuthDto.SignInRequest signInRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.email(),
                        signInRequest.password()
                )
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        assert userDetails != null;
        return ResponseDto.response.success("success", jwtService.generateToken(userDetails));
    }

    public ResponseDto.response signUp(AuthDto.SignUpRequest signUpRequest) {
        String hashedPassword = passwordService.hash(signUpRequest.password());
        User user = new User(UUID.randomUUID(), signUpRequest.name(), signUpRequest.email(), hashedPassword, Instant.now(), Instant.now());
        userRepository.save(user);

        return ResponseDto.response.success("success", null);
    }
}
