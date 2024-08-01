package intern.freedesk.tokenservice.rest.service.impl;

import intern.freedesk.tokenservice.api.response.BaseResponse;
import intern.freedesk.tokenservice.rest.service.TokenBlackListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j

public class TokenBlackListServiceImpl implements TokenBlackListService {

    private final Set<String> blacklist = new HashSet<>();

    public BaseResponse addToBlackList(String token) {
        blacklist.add(token);
        return new BaseResponse();
    }

    public boolean isBlackListed(String token) {
        return blacklist.contains(token);
    }

}
