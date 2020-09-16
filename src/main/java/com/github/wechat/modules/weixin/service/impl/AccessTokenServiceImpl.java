package com.github.wechat.modules.weixin.service.impl;

import com.github.wechat.modules.weixin.entity.AccessTokenDO;
import com.github.wechat.modules.weixin.service.AccessTokenService;
import org.springframework.stereotype.Service;

/**
 * @Author: zhang
 * @Date: 2019/12/12/012 11:36
 * @Description:
 */
@Service
public class AccessTokenServiceImpl implements AccessTokenService {

    @Override
    public AccessTokenDO getAccessTokenById(Integer id) {
        return null;
    }

    @Override
    public boolean saveOrUpdateAccessToken(AccessTokenDO accessToken) {
        return false;
    }

    @Override
    public boolean updateAccessToken(AccessTokenDO accessToken) {
        return false;
    }


}
