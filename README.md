## 微信公众号开发

### 配置
在 application.properties 文件中配置以下开发信息：
```
# 微信appId、appsecret及token
app.WX_APPID=
app.WX_APPSECRET=
app.WX_TOKEN=
```

在公众号后台配置

```
服务器地址(URL)
http://域名/wx/index

令牌(Token)
自定义，必须与开发后端的token一直

消息加解密密钥(EncodingAESKey)
自动生成或者自定义
```

### 功能
* 签名验证
* 授权登录
* 自定义菜单
* 自动回复
* 生成二维码

