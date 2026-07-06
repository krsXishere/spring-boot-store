package cloud.ikis.store.controllers;

import cloud.ikis.store.dtos.AuthDto;
import cloud.ikis.store.dtos.ResponseDto;
import cloud.ikis.store.services.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/sign-in")
    public ResponseDto.response signIn(@RequestBody AuthDto.SignInRequest signInRequest) {
        return authService.signIn(signInRequest);
    }

    @PostMapping("/sign-up")
    public ResponseDto.response signUp(@RequestBody AuthDto.SignUpRequest signUpRequest) {
        return authService.signUp(signUpRequest);
    }
}
