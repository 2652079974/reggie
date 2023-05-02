package org.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.example.common.CustomException;
import org.example.common.Result;
import org.example.common.TDO;
import org.example.common.TimelinessDataObject;
import org.example.pojo.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeoutException;

/**
 * @author Maplerain
 * @date 2023/4/20 18:34
 **/
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private Executor executor;

    @Autowired
    private UserService userService;

    /**
     * 发送短信验证码
     *
     * @param user
     * @param session
     * @return
     */
    @PostMapping("/sendMsg")
    public Result sendMsg(@RequestBody User user, HttpSession session) {
        String phone = user.getPhone();

        if (StringUtils.isNotEmpty(phone) && session.getAttribute(phone) == null) {
            // 没钱发短信，这里用随机数
            long code = new Random(System.currentTimeMillis()).nextLong() % ((100000 + 999999) + 1000000) % 999999;
            code = Math.abs(code);
            // 验证码具有时效性
            session.setAttribute(phone, new TimelinessDataObject(20L*1000,code));
            // timeoutRemoveCode(phone, session);
            log.info("短信验证码：{}", code);
            return Result.success("手机验证码短信发送成功！验证码 20 秒有效，请在有效时间完成验证！");
        }
        return Result.success("发送失败！");
    }

    @PostMapping("/login")
    public Result login(@RequestBody Map<String , Object> loginInfo,HttpSession session){
        String phonenumber = loginInfo.get("phone").toString();
        String code = loginInfo.get("code").toString();

        // 获取手机号对应的验证码时效性封装对象
        Object attribute = session.getAttribute(phonenumber);

        if (attribute == null) {
            return Result.error("未发送验证码！");
        }

        try {
            // 获取封装对象中的数据
            String keycode = String.valueOf(((TDO<Long>) attribute).getData());
            if(keycode.equals(code)){
                // 校验成功
                return verified(phonenumber,session);
            }
        } catch (Exception e) {
            if(e instanceof TimeoutException){
                session.removeAttribute(phonenumber);
                throw new CustomException("验证码过期！");
            }else{
                e.printStackTrace();
                throw new CustomException("未知错误！");
            }
        }
        return Result.error("登录失败");
    }

    /**
     * 手机号验证码校验成功后的处理
     *
     * @param phonenumber
     * @param session
     * @return
     */
    private Result verified(String phonenumber, HttpSession session){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone,phonenumber);

        User user = userService.getOne(queryWrapper);
        if (user==null) {
            user = new User();
            user.setPhone(phonenumber);
            userService.save(user);
        }

        session.setAttribute("user",user.getId());
        return Result.success(user);
    }

    /*
    /**
     * 定时器，如果验证码到期则立即清除 session 对象中记录的验证码，
     * 该方法采用堵塞线程，容易造成大量资源浪费，已弃用。
     *
     * @deprecated
     * @param phone
     * @param session
     * /
    private void timeoutRemoveCode(String phone, HttpSession session) {
        executor.execute(() -> {
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            session.removeAttribute(phone);
            log.info("用户 {} 验证码过期！", phone);
        });
    }
    */
}
