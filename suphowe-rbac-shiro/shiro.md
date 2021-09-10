# 常见的shiro异常：
## AuthenticationException 认证异常
Shiro在登录认证过程中，认证失败需要抛出的异常。 AuthenticationException包含以下子类：

### CredentitalsException 凭证异常
IncorrectCredentialsException 不正确的凭证  
ExpiredCredentialsException 凭证过期  

### AccountException 账号异常
ConcurrentAccessException: 并发访问异常（多个用户同时登录时抛出）  
UnknownAccountException: 未知的账号  
ExcessiveAttemptsException: 认证次数超过限制  
DisabledAccountException: 禁用的账号  
LockedAccountException: 账号被锁定  
UnsupportedTokenException: 使用了不支持的Token  

## AuthorizationException 授权异常
Shiro在登录认证过程中，授权失败需要抛出的异常。 AuthorizationException包含以下子类：

### UnauthorizedException
抛出以指示请求的操作或对请求的资源的访问是不允许的。

### UnanthenticatedException
当尚未完成成功认证时，尝试执行授权操作时引发异常。

# web 测试
login：http://localhost:8092/login?userName=suphowe&password=123456
index: http://localhost:8092/index
logout：http://localhost:8092/logout
error: http://localhost:8092/error

# shrio 注解
| 注解        | 描述    |
| :--------   | :-----   |
| @RequiresAuthenthentication        | 表示当前Subject已经通过login进行身份验证;即 Subject.isAuthenticated()返回 true      |
| @RequiresUser        | 表示当前Subject已经身份验证或者通过记住我登录的      |
| @RequiresGuest        | 表示当前Subject没有身份验证或者通过记住我登录过，即是游客身份      |
| @RequiresRoles(value = {"admin","user"},logical = Logical.AND)       | 表示当前Subject需要角色admin和user      |
| @RequiresPermissions(value = {"user:delete","user:b"},logical = Logical.OR)       | 表示当前Subject需要权限user:delete或者user:b      |

