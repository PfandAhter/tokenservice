package intern.freedesk.tokenservice.api;

import intern.freedesk.tokenservice.api.request.AuthUserRequest;
import intern.freedesk.tokenservice.api.request.BaseRequest;
import intern.freedesk.tokenservice.api.response.AuthUserResponse;
import intern.freedesk.tokenservice.api.response.BaseResponse;
import intern.freedesk.tokenservice.api.response.UserIdResponse;
import intern.freedesk.tokenservice.exceptions.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/token-service")
public interface TokenServiceApi {

    @PostMapping("/token/generate")
    ResponseEntity<AuthUserResponse> generateToken(@Valid @RequestBody AuthUserRequest authUserRequest, HttpServletRequest request) throws NotFoundException;

    @PostMapping("/token/extract/email") //getmapping ?
    ResponseEntity<String> extractEmailFromToken (@Valid @RequestBody BaseRequest baseRequest) throws NotFoundException;

    @PostMapping("/token/logout")
    ResponseEntity<BaseResponse> logoutUser (@Valid @RequestBody BaseRequest baseRequest);

    @PostMapping("/token/extract/userId")
    ResponseEntity<UserIdResponse> extractUserIdFromToken(@RequestBody BaseRequest baseRequest) throws NotFoundException;

    @PostMapping("/token/isExpired")
    ResponseEntity<BaseResponse> isTokenExpired (@RequestBody BaseRequest baseRequest) throws NotFoundException;
}
