package cloud.ikis.store.dtos;

public class AuthDto {
    public record SignInRequest(String email, String password) {
    }

    public record SignUpRequest(String name, String email, String password) {
    }
}
