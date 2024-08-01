package intern.freedesk.tokenservice.rest.service;

import intern.freedesk.tokenservice.model.CommonHeader;
import jakarta.servlet.http.HttpServletRequest;

public interface HeaderService {

    CommonHeader getHeader(HttpServletRequest request);
}
