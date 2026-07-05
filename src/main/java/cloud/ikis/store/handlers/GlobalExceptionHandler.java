package cloud.ikis.store.handlers;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public Map<String, String> handleException(ResponseStatusException e) {
        return Map.of("error", e.getMessage());
    }

//    @ExceptionHandler(ResponseStatusException.class)
//    public Map<String, String> handleUserNotFound(ResponseStatusException e) {
//        return Map.of("error", e.getMessage());
//    }
}
