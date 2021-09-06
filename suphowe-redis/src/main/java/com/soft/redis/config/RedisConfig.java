package com.soft.redis.config;

import com.soft.redis.defines.RedisProperties;
import com.soft.redis.listener.RedisMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * Redis连接池配置
 * @author suphowe
 */
@Configuration
public class RedisConfig {

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    @Primary
    public RedisConnectionFactory taskConnectionFactory() {
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
        connectionFactory.setPort(redisProperties.getPort());
        connectionFactory.setHostName(redisProperties.getHost());
        connectionFactory.setDatabase(redisProperties.getDatabase());
        connectionFactory.setPassword(redisProperties.getPassword());
        return connectionFactory;
    }

    @Bean
    public RedisTemplate taskRedisTemplate() {
        RedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(taskConnectionFactory());
        return template;
    }

    @Bean
    public RedisConnectionFactory mainConnectionFactory() {
        // 推荐使用
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setPort(redisProperties.getPort());
        redisStandaloneConfiguration.setHostName(redisProperties.getHost());
        redisStandaloneConfiguration.setDatabase(redisProperties.getDatabase());
        redisStandaloneConfiguration.setPassword(RedisPassword.of(redisProperties.getPassword()));
        JedisConnectionFactory redisStandaloneConfigurationFactory = new JedisConnectionFactory(redisStandaloneConfiguration);

//        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
//        connectionFactory.setPort(redisPort);
//        connectionFactory.setHostName(redisHost);
//        connectionFactory.setDatabase(3);
//        connectionFactory.setPassword(redisPass);
//
        //todo something wrong
        /*
         * org.springframework.data.redis.RedisConnectionFailureException: Cannot get Jedis connection; nested exception is redis.clients.jedis.exceptions.JedisConnectionException: Could not get a resource from the pool
         */
//        JedisShardInfo jedisShardInfo = new JedisShardInfo(redisHost,redisPort );
//        jedisShardInfo.setConnectionTimeout(10000);
//        jedisShardInfo.setSoTimeout(10000);
//        jedisShardInfo.setPassword(redisPass);
//        JedisConnectionFactory jedisShardInfoFactory = new JedisConnectionFactory(jedisShardInfo);

        return redisStandaloneConfigurationFactory;
    }

    @Bean("mainRedisTemplate")
    public StringRedisTemplate mainRedisTemplate() {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(mainConnectionFactory());

//        RedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
//        RedisSerializer stringRedisSerializer = new StringRedisSerializer();
//        // key 的序列化采用 StringRedisSerializer
//        template.setKeySerializer(stringRedisSerializer);
//        template.setHashKeySerializer(stringRedisSerializer);
//        // value 值的序列化采用 GenericJackson2JsonRedisSerializer
//        template.setValueSerializer(genericJackson2JsonRedisSerializer);
//        template.setHashValueSerializer(genericJackson2JsonRedisSerializer);

        return template;
    }

    @Bean
    public RedisScript<Boolean> lockScript() {
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/redis-lock.lua")));
        redisScript.setResultType(Boolean.class);
        return redisScript;
    }

    @Bean
    public RedisScript<Boolean> unlockScript() {
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/redis-unlock.lua")));
        redisScript.setResultType(Boolean.class);
        return redisScript;
    }


    /**
     * key过期监听,指定数据库
     */
    @Bean
    RedisMessageListenerContainer keyExpirationListenerContainer(RedisMessageListener listener) {
        RedisMessageListenerContainer listenerContainer = new RedisMessageListenerContainer();
        listenerContainer.setConnectionFactory(taskConnectionFactory());
        listenerContainer.addMessageListener(listener, new PatternTopic("__keyevent@" + redisProperties.getDatabase() + "__:expired"));
        return listenerContainer;
    }
}
