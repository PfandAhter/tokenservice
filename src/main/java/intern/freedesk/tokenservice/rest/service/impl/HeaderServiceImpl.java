package intern.freedesk.tokenservice.rest.service.impl;

import intern.freedesk.tokenservice.model.CommonHeader;
import intern.freedesk.tokenservice.rest.service.HeaderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
public class HeaderServiceImpl implements HeaderService {

    @Override
    public CommonHeader getHeader(HttpServletRequest request) {
        CommonHeader commonHeader = new CommonHeader();
        commonHeader.setToken(request.getHeader(AUTHORIZATION));
        return commonHeader;
    }
}