package intern.freedesk.tokenservice.constants;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Constants {

    public static final String SUCCESS = "SUCCESS";
    public static final String FAILED = "FAILED";
    public static final String USER_NOT_FOUND = "USER NOT FOUND";


    public static final String JWT_SECRET = "${app.jwtSecret}";

    public static final String JWT_CRTPYO_SECRET = "${app.jwtCryptoSecret}";
}
