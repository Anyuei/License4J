package com.license4j.license.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.license4j.license.entity.License;
import com.license4j.license.mapper.LicenseMapper;
import com.license4j.license.service.LicenseService;
import org.springframework.stereotype.Service;

@Service
public class LicenseServiceImpl extends ServiceImpl<LicenseMapper, License> implements LicenseService {

}
