package com.license4j.license.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author LiGezZ
 */
@Component
@Slf4j
public class LicenseInterceptor implements HandlerInterceptor {

    @Resource
    private LicenseHandler licenseHandler;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String servletPath = request.getServletPath();
        log.debug("进入拦截器,URL:{}", servletPath);
        // 查看是否授权成功
        boolean license = licenseHandler.loadLicense();
        if (!license&&!servletPath.startsWith("/noAuth")) {
            throw new RuntimeException("系统暂未授权");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
