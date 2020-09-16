package com.github.wechat.modules.weixin.RedisDao;


import com.github.wechat.modules.weixin.entity.AccessTokenDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;


@Repository
public class AccessTokenRedisDao {

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String key = "wx_access_token";


    private ValueOperations getValueOperation() {
        return redisTemplate.opsForValue();
    }

    public AccessTokenDO getAccessToken() {
        return (AccessTokenDO) getValueOperation().get(key);
    }

    public void addAccessToken(AccessTokenDO accessToken) {
        getValueOperation().set(key, accessToken);
    }

}
