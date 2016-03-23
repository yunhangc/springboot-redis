package afm.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * Created by rchen on 3/22/16.
 */
@ConfigurationProperties(prefix = "spring.redis")
@EnableCaching
public class CachingConfig extends CachingConfigurerSupport {

    public static final String ACCOUNTS_CACHE = "accounts";

    private String host;

    private Integer port;

    private String password;

    List<String> allCacheNames = Arrays.asList(ACCOUNTS_CACHE);

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(host);
        factory.setPort(port);
        factory.setPassword(password);
        return factory;
    }

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
        template.setConnectionFactory(factory);
        return template;
    }

    @Bean
    public RedisCacheManager cacheManager(RedisTemplate<Object, Object> redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        if(!allCacheNames.isEmpty()) {
            cacheManager.setCacheNames(allCacheNames);
        }
        cacheManager.setUsePrefix(true);
        return cacheManager;
    }

    @Bean
    public KeyGenerator accountKeyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                //first parameter is caching object
                //second paramter is the name of the method, we like the caching key has nothing to do with method name
                //third parameter is the list of parameters in the method being called
                return target.getClass().toString() + "accountId:" + params[0].toString();
            }
        };
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
