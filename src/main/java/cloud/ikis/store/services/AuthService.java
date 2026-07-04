package cloud.ikis.store.services;

import cloud.ikis.store.dtos.AuthDto;
import cloud.ikis.store.security.jwt.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AuthDto.SignInResponse signIn(AuthDto.SignInRequest signInRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.email(),
                        signInRequest.password()
                )
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        assert userDetails != null;
        return new AuthDto.SignInResponse(jwtService.generateToken(userDetails));
    }
}
