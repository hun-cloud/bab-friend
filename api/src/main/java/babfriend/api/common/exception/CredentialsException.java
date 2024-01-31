package babfriend.api.common.exception;

public class CredentialsException extends CustomException{
    public CredentialsException() {
        super(ErrorCode.CREDENTIALS_IS_FAIL);
    }
}
