package com.github.wechat.modules.weixin.mapper;

import com.github.wechat.datasources.DataSourceNames;
import com.github.wechat.datasources.annotation.DataSource;
import com.github.wechat.modules.weixin.entity.WechatUserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @Author: zhang
 * @Date: 2019/12/12/012 11:48
 * @Description:
 */
@Repository
@Mapper
public interface WechatUserMapper {

    @DataSource(name = DataSourceNames.FIRST)
    @Select("SELECT * FROM zs_wechat_user WHERE openid = #{openid}")
    WechatUserDTO selectWechatUserByOpenId(String openid);


    @DataSource(name = DataSourceNames.FIRST)
    boolean insertWechatUser(WechatUserDTO wechatUserDTO);


    @DataSource(name = DataSourceNames.FIRST)
    boolean updateWechatUser(WechatUserDTO wechatUserDTO);

    /**
     * 根据用户id查询openid
     * @param userId
     * @return
     */
    @DataSource(name = DataSourceNames.FIRST)
    @Select("SELECT openid FROM zs_wechat_user WHERE id = #{userId}")
    String selectOpenIdByid(long userId);

}
