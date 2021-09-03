# token 验证

## 目录结构
├── Readme.md                               //help  
├── bean                                    //对象  
│   └── User.java                           //用户  
├── service                                 //服务  
│   ├── ITokenService.java                  //token接口  
│   ├── IUserService.java                   //用户接口  
│   └── impl                                //实现类    
│       ├── TokenServiceImpl.java           //token实现服务类  
│       └── UserServiceImpl.java            //用户实现服务类  
├── util                                    //工具类  
│   ├── TokenUtil.java                      //token工具类   
│   └── AuthenticationInterceptor.java      //自定义拦截器(实现拦截还是放通的逻辑)  
├── InterceptorConfig.java                  //拦截器  
├── PassToken.java                          //跳过验证的注解接口  
└── UserLoginToken.java                     //需要用户登陆验证注解接口 

───TokenController.java                     //token测试controller  

## token引入

### pom.xml引入
```$xslt
        <!-- java-jwt token验证 -->
        <dependency>
            <groupId>com.auth0</groupId>
            <artifactId>java-jwt</artifactId>
            <version>3.4.0</version>
        </dependency>
```
