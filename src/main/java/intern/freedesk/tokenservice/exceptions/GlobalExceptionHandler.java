package intern.freedesk.tokenservice.exceptions;

import intern.freedesk.tokenservice.api.response.BaseResponse;
import intern.freedesk.tokenservice.constants.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor

public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BaseResponse> handleException (RuntimeException e) {
        log.error("Error: " + e);
        return ResponseEntity.internalServerError().body(createFailResponse(e.getMessage().split("HataMesaji")[1].substring(3,e.getMessage().split("HataMesaji")[1].length()-3)));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<BaseResponse> handleException (NotFoundException e) {
        log.error("Error: " + e);
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(createFailResponse(e.getMessage()));
    }





    private BaseResponse createFailResponse(String message) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setResultCode(Constants.FAILED);
        baseResponse.setMessage(message);
        return baseResponse;
    }

}
