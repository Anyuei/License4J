package com.license4j.license.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.license4j.license.entity.Manager;
import com.license4j.license.mapper.ManagerMapper;
import com.license4j.license.service.ManagerService;
import org.springframework.stereotype.Service;

/**
 * @author XiaoJiYun
 * @date 2023-07-05
 * @description
 */
@Service
public class ManagerServiceImpl extends ServiceImpl<ManagerMapper, Manager> implements ManagerService {

}
