package intern.freedesk.tokenservice.rest.service.impl;

import intern.freedesk.tokenservice.api.request.AuthUserRequest;
import intern.freedesk.tokenservice.api.request.BaseRequest;
import intern.freedesk.tokenservice.api.response.AuthUserResponse;
import intern.freedesk.tokenservice.api.response.BaseResponse;
import intern.freedesk.tokenservice.api.response.UserIdResponse;
import intern.freedesk.tokenservice.auth.JwtService;
import intern.freedesk.tokenservice.exceptions.NotFoundException;
import intern.freedesk.tokenservice.model.entity.User;
import intern.freedesk.tokenservice.repository.UserRepository;
import intern.freedesk.tokenservice.rest.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class TokenServiceImpl implements TokenService {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final TokenBlackListServiceImpl tokenBlackListService;


    public AuthUserResponse generateToken(AuthUserRequest request) throws NotFoundException {
        User localUser = userRepository.findByEmail(request.getEmail());

        if(localUser == null) {
            throw new NotFoundException("User not found");
        }

        //AuthUserResponse authUserResponse = new AuthUserResponse();

        //authUserResponse.setToken(jwtService.generateToken(localUser));


        return new AuthUserResponse(jwtService.generateToken(localUser));

//        return authUserResponse;
    }



    public String extractEmailFromToken(BaseRequest request) throws NotFoundException{
        if(tokenBlackListService.isBlackListed(request.getToken())) {
            throw new NotFoundException("Token is blacklisted");
        }

//        return jwtService.extractUsername(jwtService.decryptJwt(request.getToken().split(" ")[1]));
        return jwtService.extractUsername(jwtService.decryptJwt(request.getToken()));
    }


    public UserIdResponse getUserIdFromToken(BaseRequest baseRequest) throws NotFoundException {
        User user = userRepository.findByEmail(extractEmailFromToken(baseRequest));

        if (user == null) {
            throw new NotFoundException("USER NOT FOUND");
        }
        return UserIdResponse.builder()
                .userId(user.getUserId())
                .build();
    }


    public BaseResponse isTokenExpired (BaseRequest request) throws NotFoundException{
        if(jwtService.isTokenExpired(request.getToken())) {
            throw new NotFoundException("Token is expired");
        }

        return new BaseResponse();
    }


}
