package babfriend.api.common.exception;

public class RedisValueNotFoundException extends CustomException {

    public RedisValueNotFoundException() {
        super(ErrorCode.REDIS_NOT_FOUND);
    }
}
