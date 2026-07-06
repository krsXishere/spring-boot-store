package cloud.ikis.store.handlers;

import cloud.ikis.store.dtos.ResponseDto;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseDto.response<String> handleResponseStatus(ResponseStatusException e) {
        return new ResponseDto.response<>(e.getMessage(), null);
    }
}
