package com.github.wechat.modules.weixin.service;

import com.github.wechat.modules.weixin.entity.AccessTokenDO;

/**
 * @Author: zhang
 * @Date: 2019/12/12/012 11:35
 * @Description:
 */
public interface AccessTokenService {

    AccessTokenDO getAccessTokenById(Integer id);

    boolean saveOrUpdateAccessToken(AccessTokenDO accessToken);

    boolean updateAccessToken(AccessTokenDO accessToken);

}
