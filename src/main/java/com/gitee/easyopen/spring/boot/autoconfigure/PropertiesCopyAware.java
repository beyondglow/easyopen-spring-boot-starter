package com.gitee.easyopen.spring.boot.autoconfigure;

import com.gitee.easyopen.ApiConfig;

/**
 * @author tanghc
 */
public interface PropertiesCopyAware {

    /**
     * 拷贝EasyopenProperties中的属性到ApiConfig
     * @param source EasyopenProperties
     * @param target ApiConfig
     */
    void copy(EasyopenProperties source, ApiConfig target);

}
