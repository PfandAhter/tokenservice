package intern.freedesk.tokenservice.api.response;


import intern.freedesk.tokenservice.constants.Constants;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class BaseResponse {

    private String resultCode = Constants.SUCCESS;

    private String message = Constants.SUCCESS;

}
