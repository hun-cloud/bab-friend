package babfriend.api.common.exception;

public class TokenIsNotSameException extends CustomException{

    public TokenIsNotSameException() {
        super(ErrorCode.TOKEN_IS_NOT_SAME);
    }
}
