package babfriend.api.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;


    public Optional<String> getValue(String key) {
        String result = redisTemplate.opsForValue().get(key);
        return Optional.ofNullable(result);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public void setValue(String key, String value) {
        redisTemplate.opsForValue()
                .set(key, value);
    }


}
