package net.laoyeye.yyblog.service.impl;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;
import net.laoyeye.yyblog.common.YYBlogResult;
import net.laoyeye.yyblog.common.utils.IDUtils;
import net.laoyeye.yyblog.mapper.UserMapper;
import net.laoyeye.yyblog.model.UserDO;
import net.laoyeye.yyblog.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public YYBlogResult login(HttpServletRequest request) {
        try {
            // 第一步获取授权码
            // 第二步获取accesstoken
            AccessToken accessTokenObj = new Oauth().getAccessTokenByRequest(request);
            String accessToken = accessTokenObj.getAccessToken();
            OpenID openidObj = new OpenID(accessToken);
            // 数据查找openid是否关联,如果没有关联先跳转到关联账号页面,如果直接登录.
            String userOpenId = openidObj.getUserOpenID();
            UserDO user = userMapper.getUserByOpenId(userOpenId);
            if (StringUtils.isEmpty(user)) {
                //获取用户信息
                UserInfo qzoneUserInfo = new UserInfo(accessToken, userOpenId);
                UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
                user = new UserDO();
                user.setId(IDUtils.genId());
                user.setNickname(userInfoBean.getNickname());
                user.setAvatar(userInfoBean.getAvatar().getAvatarURL50());
                user.setOpenId(userOpenId);
                user.setCreateTime(new Date());
                user.setUpdateTime(new Date());
                user.setEnable(true);
                //保存用户信息
                userMapper.save(user);
            }
            UsernamePasswordToken token = new UsernamePasswordToken(userOpenId, userOpenId);
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            return YYBlogResult.ok();
        } catch (Exception e) {
            //LOG
            e.printStackTrace();
            return YYBlogResult.build(500, e.getMessage());
        }
    }

    @Override
    public Long wxLogin(UserDO user) {
        try {
            //查询wxOpenId是否存在
            UserDO userDo = userMapper.getUserByWxOpenId(user.getWxOpenId());
            if (StringUtils.isEmpty(userDo)) {
                user.setId(IDUtils.genId());
                user.setCreateTime(new Date());
                user.setUpdateTime(new Date());
                user.setEnable(true);
                //保存用户信息
                userMapper.save(user);
                return user.getId();
            } 
            user.setUpdateTime(new Date());
            userMapper.updateWxUser(user);
            return userDo.getId();
        } catch (Exception e) {
            //LOG
            e.printStackTrace();
            return null;
        }
    }

}
