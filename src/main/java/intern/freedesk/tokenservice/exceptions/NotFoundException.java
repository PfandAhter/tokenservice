package intern.freedesk.tokenservice.exceptions;

import lombok.Getter;

public class NotFoundException extends Exception{
    @Getter

    private String message;

    public NotFoundException(){
        super();
        this.message = null;
    }

    public NotFoundException(String message){
        super();
        this.message = message;
    }

}
