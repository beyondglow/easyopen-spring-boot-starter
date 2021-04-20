package com.gitee.easyopen.spring.boot.autoconfigure;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.gitee.easyopen.ApiConfig;
import com.gitee.easyopen.ApiContext;
import com.gitee.easyopen.RedisAppSecretManager;
import com.gitee.easyopen.config.ConfigClient;
import com.gitee.easyopen.interceptor.ApiInterceptor;
import com.gitee.easyopen.limit.ApiLimitConfigLocalManager;
import com.gitee.easyopen.limit.ApiLimitManager;
import com.gitee.easyopen.session.RedisSessionManager;
import com.gitee.easyopen.support.ApiController;
import com.gitee.easyopen.util.CopyUtil;
import com.gitee.easyopen.util.RedisCache;


/**
 * @author tanghc
 */
@org.springframework.context.annotation.Configuration
@EnableConfigurationProperties(EasyopenProperties.class)
public class EasyopenAutoConfiguration {

    private final EasyopenProperties properties;

    // application.properties中的配置会注入到EasyopenProperties中
    public EasyopenAutoConfiguration(EasyopenProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    public ApiConfig apiConfig() {
        return new ApiConfig();
    }
    
    /**
     * 跨域过滤器
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "easyopen", name = "cors", havingValue = "true", matchIfMissing = true)
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }

    @Bean
    @ConditionalOnMissingBean
    public CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }

    @Bean
    @ConditionalOnMissingBean
    public PropertiesCopyAware defaultPropertiesCopyAware() {
        return new DefaultPropertiesCopyAware();
    }

    /**
     * 默认入口
     */
    @Controller
    @RequestMapping("api")
    public class EayopenIndexController extends ApiController {

        @Autowired
        private ApiConfig config;

        @Autowired
        private PropertiesCopyAware propertiesCopyAware;

        @Override
        protected void initApiConfig(ApiConfig apiConfig) {
            propertiesCopyAware.copy(properties, apiConfig);
            this.initInterceptor(properties, apiConfig);
            this.initOpenClient(properties, apiConfig);
            this.initRedisCache(properties, apiConfig);
        }

        private void initRedisCache(EasyopenProperties properties, ApiConfig apiConfig) {
        	RedisTemplate redisTemplate = (RedisTemplate)getApplicationContext().getBean("redisTemplate");
            if (redisTemplate == null) {
                throw new NullPointerException("redisTemplate不能为null，是否缺少spring-boot-starter-data-redis依赖");
            }
            RedisCache redisCahe = new RedisCache(redisTemplate);
            
            RedisAppSecretManager redisAppSecretManager = new RedisAppSecretManager();
            redisAppSecretManager.setRedisCache(redisCahe);
            
            ApiContext.getApiConfig().setAppSecretManager(redisAppSecretManager);
		}

		private void initInterceptor(EasyopenProperties properties, ApiConfig apiConfig) {
            if (!CollectionUtils.isEmpty(properties.getInterceptors())) {
                List<String> interceptors = properties.getInterceptors();
                ApiInterceptor[] apiInterceptor = new ApiInterceptor[interceptors.size()];
                for (int i = 0; i < interceptors.size(); i++) {
                    String interceptorClassName = interceptors.get(i);
                    try {
                        apiInterceptor[i] = (ApiInterceptor)Class.forName(interceptorClassName).newInstance();
                    } catch (Exception e) {
                        logger.error("Class.forName({}).newInstance() error", interceptorClassName, e);
                        throw new RuntimeException(e);
                    }
                }
                apiConfig.setInterceptors(apiInterceptor);
            }
        }

        private void initOpenClient(EasyopenProperties properties, ApiConfig apiConfig) {
            String ip = properties.getConfigServerIp();
            String port = properties.getConfigServerPort();
            if (StringUtils.hasText(ip) && StringUtils.hasText(port)) {
                // appName 应用名称
                // host    配置中心ip
                // port    配置中心端口
                String docUrl = properties.getDocUrl();
                ConfigClient configClient = new ConfigClient(properties.getAppName(), ip, Integer.valueOf(port), docUrl);
                // 如果要使用分布式业务限流，使用下面这句。默认是单机限流
                if (properties.getConfigDistributedLimit()) {
                    RedisTemplate redisTemplate = (RedisTemplate)getApplicationContext().getBean("redisTemplate");
                    if (redisTemplate == null) {
                        throw new NullPointerException("redisTemplate不能为null，是否缺少spring-boot-starter-data-redis依赖");
                    }
                    configClient.setLimitManager(new ApiLimitManager(redisTemplate, new ApiLimitConfigLocalManager()));
                }
                apiConfig.setConfigClient(configClient);
            }
        }

        @Override
        protected ApiConfig newApiConfig() {
            return config;
        }

    }

    private static class DefaultPropertiesCopyAware implements PropertiesCopyAware {
        @Override
        public void copy(EasyopenProperties source, ApiConfig target) {
            CopyUtil.copyPropertiesIgnoreNull(source, target);
        }
    }
}
