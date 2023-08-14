package com.license4j.license.entity;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("stmf_manager")
public class Manager {

    private Long id;

    private String managerName;

    private String password;

    public String getToken(Manager manager) {
        return JWT.create().withAudience(String.valueOf(manager.getId()))
                .sign(Algorithm.HMAC256(manager.getPassword()));
    }
}