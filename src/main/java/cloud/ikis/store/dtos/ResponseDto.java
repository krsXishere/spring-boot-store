package cloud.ikis.store.dtos;

public class ResponseDto {
    public record response<T>(String message, T data) {
        public static <T> response<T> success(String message, T data) {
            return new response<>(message, data);
        }
    }
}
