package com.license4j.license.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.license4j.license.entity.Manager;
import com.license4j.license.entity.Result;
import com.license4j.license.entity.request.LoginRequest;
import com.license4j.license.service.ManagerService;
import com.license4j.license.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "管理员模块")
@RestController
@RequestMapping("/manager")
@Slf4j
public class ManagerController {

    @Resource
    private ManagerService managerService;
    @ApiOperation(value = "管理员登录", notes = "管理员登录")
    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest loginRequest) {
        QueryWrapper<Manager> managerQueryWrapper = new QueryWrapper<>();
        managerQueryWrapper.eq("manager_name",loginRequest.getManagerName());
        Manager one = managerService.getOne(managerQueryWrapper);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (null==one||!passwordEncoder.matches(
                loginRequest.getPassword(),
                passwordEncoder.encode(loginRequest.getPassword()))
        ){
            throw new RuntimeException("管理员不存在或密码错误");
        }
        Manager manager = new Manager();
        BeanUtils.copyProperties(loginRequest,manager);
        String token= TokenUtil.sign(manager);
        return Result.ok(token);
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("ayp43520"));
    }
}
