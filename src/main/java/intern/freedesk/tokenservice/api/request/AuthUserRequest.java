package intern.freedesk.tokenservice.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AuthUserRequest {

    private String email;

    private String password;

}
