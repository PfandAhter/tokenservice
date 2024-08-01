package intern.freedesk.tokenservice.rest.controller;

import intern.freedesk.tokenservice.api.TokenServiceApi;
import intern.freedesk.tokenservice.api.request.AuthUserRequest;
import intern.freedesk.tokenservice.api.request.BaseRequest;
import intern.freedesk.tokenservice.api.response.AuthUserResponse;
import intern.freedesk.tokenservice.api.response.BaseResponse;
import intern.freedesk.tokenservice.api.response.UserIdResponse;
import intern.freedesk.tokenservice.exceptions.NotFoundException;
import intern.freedesk.tokenservice.rest.service.impl.TokenBlackListServiceImpl;
import intern.freedesk.tokenservice.rest.service.impl.TokenServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor

public class TokenServiceController implements TokenServiceApi {

    private final TokenServiceImpl tokenService;

    private final TokenBlackListServiceImpl blackListService;

    @Override
    public ResponseEntity<AuthUserResponse> generateToken(AuthUserRequest authUserRequest,HttpServletRequest request) throws NotFoundException {
        return ResponseEntity.ok(tokenService.generateToken(authUserRequest));
    }

    @Override
    public ResponseEntity<String> extractEmailFromToken(BaseRequest baseRequest) throws NotFoundException {
        return ResponseEntity.ok(tokenService.extractEmailFromToken(baseRequest));
    }

    @Override
    public ResponseEntity<BaseResponse> logoutUser(BaseRequest baseRequest) {
        return ResponseEntity.ok(blackListService.addToBlackList(baseRequest.getToken()));
    }

    @Override
    public ResponseEntity<UserIdResponse> extractUserIdFromToken(BaseRequest baseRequest) throws NotFoundException {
        return ResponseEntity.ok(tokenService.getUserIdFromToken(baseRequest));
    }

    @Override
    public ResponseEntity<BaseResponse> isTokenExpired(BaseRequest baseRequest) throws NotFoundException {
        return ResponseEntity.ok(tokenService.isTokenExpired(baseRequest));
    }

}
