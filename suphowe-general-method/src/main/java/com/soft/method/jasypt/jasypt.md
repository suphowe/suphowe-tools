## pom依赖
```
        <!-- 配置文件加密 -->
        <dependency>
            <groupId>com.github.ulisesbocchio</groupId>
            <artifactId>jasypt-spring-boot-starter</artifactId>
            <version>3.0.4</version>
        </dependency>
```
## 添加KEY
在配置文件application.properties 中添加
```
# 配置文件加密密钥,需要加密的数据固定写法  ENC(密文)
# 启动时可通过命令传入配置参数 java -jar XX.jar --jasypt.encryptor.password=EbfYkitulv73I2p0mXI50JMXoaxZTKJ7
jasypt.encryptor.password=EbfYkitulv73I2p0mXI50JMXoaxZTKJ7
```
## Controller中加密解密
```
# 加密
StringEncryptor.encrypt(info)
# 解密
StringEncryptor.decrypt(info)
```