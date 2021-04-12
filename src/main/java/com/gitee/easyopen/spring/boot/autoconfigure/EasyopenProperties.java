package com.gitee.easyopen.spring.boot.autoconfigure;

import com.gitee.easyopen.limit.LimitType;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author tanghc
 */
@ConfigurationProperties(prefix = EasyopenProperties.EASYOPEN_PREFIX)
public class EasyopenProperties {
    public static final String EASYOPEN_PREFIX = "easyopen";

    private static final String DEFAULT_APP_NAME = "app";

    private static String CONFIG_FOLDER = System.getProperty("user.dir") + File.separator + "local-config" + File.separator;

    public EasyopenProperties() {
        this.setAppName(DEFAULT_APP_NAME);
    }

    /**
     * app,secret键值对
     */
    private Map<String, String> appSecret;

    /**
     * 拦截器
     */
    private List<String> interceptors;

    /**
     * 消息模块
     */
    private List<String> isvModules;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 默认版本号,默认值""
     */
    private String defaultVersion;

    /**
     * 超时时间，默认值：3
     */
    private Integer timeoutSeconds;

    /**
     * 是否生成doc文档，默认值：false
     */
    private Boolean showDoc;

    /**
     * 文档页面密码，默认为null，如果不为null，文档页面一定开启。
     */
    private String docPassword;

    /**
     * 文档模板路径
     */
    private String docClassPath;

    /**
     * 文档下载模板路径
     */
    private String docPdfClassPath;
    private String docPdfCssClassPath;

    /**
     * 监控模板路径
     */
    private String monitorClassPath;

    /**
     * 登录页模板路径
     */
    private String loginClassPath;

    /**
     * 限流 模板路径
     */
    private String limitClassPath;
    /**
     * 进入限流页面密码
     */
    private String limitPassword;

    /**
     * 默认限流策略
     */
    private LimitType defaultLimitType;

    /**
     * 默认每秒可处理请求数
     */
    private Integer defaultLimitCount;

    /**
     * 默认令牌桶个数
     */
    private Integer defaultTokenBucketCount;


    /**
     * 本地限流缓存全路径
     */
    private String localLimitConfigFile;

    /**
     * 本地权限缓存全路径
     */
    private String localPermissionConfigFile;

    /**
     * 本地秘钥缓存全路径
     */
    private String localSecretConfigFile;


    /**
     * 忽略验证
     */
    private Boolean ignoreValidate;

    /**
     * 登录视图页面用于，mvc视图，如：loginView
     */
    private String oauth2LoginUri;

    /**
     * oauth2的accessToken过期时间,单位秒,默认2小时
     */
    private Long oauth2ExpireIn;

    /**
     * jwt过期时间,秒,默认2小时
     */
    private Integer jwtExpireIn;
    /**
     * RSA加密对应的私钥
     */
    private String privateKey;

    /**
     * 私钥文件存放的classpath地址
     */
    private String priKeyPath;

    /**
     * 是否开启监控
     */
    private Boolean showMonitor;
    /**
     * 进入监控页面密码
     */
    private String monitorPassword;
    /**
     * 存放监控错误信息队列长度。超出长度，新值替换旧值
     */
    private Integer monitorErrorQueueSize;
    /**
     * 处理线程池大小
     */
    private Integer monitorExecutorSize;

    /**
     * 配置中心ip
     */
    private String configServerIp;
    /**
     * 配置中心端口号
     */
    private String configServerPort;
    /**
     * 文档页面url
     */
    private String docUrl;
    /**
     * 是否启用分布式限流
     */
    private Boolean configDistributedLimit;

    /** markdown文档保存目录 */
    private String markdownDocDir;
    
    /**
     * 设置跨域，为true时开启跨域。默认true
     */
    private String cors;
    
    /**
     * 设置true，开启webflux。默认false
     */
    private String mono;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
        /** 本地限流缓存全路径 */
        localLimitConfigFile = System.getProperty("conflimit.file", CONFIG_FOLDER + appName + "-limit.json");
        /** 本地权限缓存全路径 */
        localPermissionConfigFile = System.getProperty("confperm.file", CONFIG_FOLDER + appName + "-permission.json");
        /** 本地秘钥缓存全路径 */
        localSecretConfigFile = System.getProperty("confsecret.file", CONFIG_FOLDER + appName + "-secret.json");
    }

    //=======================================


    public Map<String, String> getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(Map<String, String> appSecret) {
        this.appSecret = appSecret;
    }

    public List<String> getInterceptors() {
        return interceptors;
    }

    public void setInterceptors(List<String> interceptors) {
        this.interceptors = interceptors;
    }

    public List<String> getIsvModules() {
        return isvModules;
    }

    public void setIsvModules(List<String> isvModules) {
        this.isvModules = isvModules;
    }

    public String getDefaultVersion() {
        return defaultVersion;
    }

    public void setDefaultVersion(String defaultVersion) {
        this.defaultVersion = defaultVersion;
    }

    public Integer getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public void setTimeoutSeconds(Integer timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
    }

    public Boolean getShowDoc() {
        return showDoc;
    }

    public void setShowDoc(Boolean showDoc) {
        this.showDoc = showDoc;
    }

    public String getDocPassword() {
        return docPassword;
    }

    public void setDocPassword(String docPassword) {
        this.docPassword = docPassword;
    }

    public String getDocClassPath() {
        return docClassPath;
    }

    public void setDocClassPath(String docClassPath) {
        this.docClassPath = docClassPath;
    }

    public String getDocPdfClassPath() {
        return docPdfClassPath;
    }

    public void setDocPdfClassPath(String docPdfClassPath) {
        this.docPdfClassPath = docPdfClassPath;
    }

    public String getDocPdfCssClassPath() {
        return docPdfCssClassPath;
    }

    public void setDocPdfCssClassPath(String docPdfCssClassPath) {
        this.docPdfCssClassPath = docPdfCssClassPath;
    }

    public String getMonitorClassPath() {
        return monitorClassPath;
    }

    public void setMonitorClassPath(String monitorClassPath) {
        this.monitorClassPath = monitorClassPath;
    }

    public String getLoginClassPath() {
        return loginClassPath;
    }

    public void setLoginClassPath(String loginClassPath) {
        this.loginClassPath = loginClassPath;
    }

    public String getLimitClassPath() {
        return limitClassPath;
    }

    public void setLimitClassPath(String limitClassPath) {
        this.limitClassPath = limitClassPath;
    }

    public String getLimitPassword() {
        return limitPassword;
    }

    public void setLimitPassword(String limitPassword) {
        this.limitPassword = limitPassword;
    }

    public LimitType getDefaultLimitType() {
        return defaultLimitType;
    }

    public void setDefaultLimitType(LimitType defaultLimitType) {
        this.defaultLimitType = defaultLimitType;
    }

    public Integer getDefaultLimitCount() {
        return defaultLimitCount;
    }

    public void setDefaultLimitCount(Integer defaultLimitCount) {
        this.defaultLimitCount = defaultLimitCount;
    }

    public Integer getDefaultTokenBucketCount() {
        return defaultTokenBucketCount;
    }

    public void setDefaultTokenBucketCount(Integer defaultTokenBucketCount) {
        this.defaultTokenBucketCount = defaultTokenBucketCount;
    }

    public String getLocalLimitConfigFile() {
        return localLimitConfigFile;
    }

    public void setLocalLimitConfigFile(String localLimitConfigFile) {
        this.localLimitConfigFile = localLimitConfigFile;
    }

    public String getLocalPermissionConfigFile() {
        return localPermissionConfigFile;
    }

    public void setLocalPermissionConfigFile(String localPermissionConfigFile) {
        this.localPermissionConfigFile = localPermissionConfigFile;
    }

    public String getLocalSecretConfigFile() {
        return localSecretConfigFile;
    }

    public void setLocalSecretConfigFile(String localSecretConfigFile) {
        this.localSecretConfigFile = localSecretConfigFile;
    }

    public Boolean getIgnoreValidate() {
        return ignoreValidate;
    }

    public void setIgnoreValidate(Boolean ignoreValidate) {
        this.ignoreValidate = ignoreValidate;
    }

    public String getOauth2LoginUri() {
        return oauth2LoginUri;
    }

    public void setOauth2LoginUri(String oauth2LoginUri) {
        this.oauth2LoginUri = oauth2LoginUri;
    }

    public Long getOauth2ExpireIn() {
        return oauth2ExpireIn;
    }

    public void setOauth2ExpireIn(Long oauth2ExpireIn) {
        this.oauth2ExpireIn = oauth2ExpireIn;
    }

    public Integer getJwtExpireIn() {
        return jwtExpireIn;
    }

    public void setJwtExpireIn(Integer jwtExpireIn) {
        this.jwtExpireIn = jwtExpireIn;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPriKeyPath() {
        return priKeyPath;
    }

    public void setPriKeyPath(String priKeyPath) {
        this.priKeyPath = priKeyPath;
    }

    public Boolean getShowMonitor() {
        return showMonitor;
    }

    public void setShowMonitor(Boolean showMonitor) {
        this.showMonitor = showMonitor;
    }

    public String getMonitorPassword() {
        return monitorPassword;
    }

    public void setMonitorPassword(String monitorPassword) {
        this.monitorPassword = monitorPassword;
    }

    public Integer getMonitorErrorQueueSize() {
        return monitorErrorQueueSize;
    }

    public void setMonitorErrorQueueSize(Integer monitorErrorQueueSize) {
        this.monitorErrorQueueSize = monitorErrorQueueSize;
    }

    public Integer getMonitorExecutorSize() {
        return monitorExecutorSize;
    }

    public void setMonitorExecutorSize(Integer monitorExecutorSize) {
        this.monitorExecutorSize = monitorExecutorSize;
    }

    public String getConfigServerIp() {
        return configServerIp;
    }

    public void setConfigServerIp(String configServerIp) {
        this.configServerIp = configServerIp;
    }

    public String getConfigServerPort() {
        return configServerPort;
    }

    public void setConfigServerPort(String configServerPort) {
        this.configServerPort = configServerPort;
    }

    public String getDocUrl() {
        return docUrl;
    }

    public void setDocUrl(String docUrl) {
        this.docUrl = docUrl;
    }

    public Boolean getConfigDistributedLimit() {
        return configDistributedLimit;
    }

    public void setConfigDistributedLimit(Boolean configDistributedLimit) {
        this.configDistributedLimit = configDistributedLimit;
    }

    public String getMarkdownDocDir() {
        return markdownDocDir;
    }

    public void setMarkdownDocDir(String markdownDocDir) {
        this.markdownDocDir = markdownDocDir;
    }

    public String getCors() {
        return cors;
    }

    public void setCors(String cors) {
        this.cors = cors;
    }

    public String getMono() {
        return mono;
    }

    public void setMono(String mono) {
        this.mono = mono;
    }
}
