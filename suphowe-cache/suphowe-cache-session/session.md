# session 集成

session 存储 token验证

## 测试地址

> 测试 重启程序，Session 不失效的场景

1. 打开浏览器，访问首页：http://localhost:8086/page/index
2. 最开始未登录，所以会跳转到登录页：http://localhost:8086/page/login?redirect=true 然后点击登录按钮
3. 登录之后，跳转回首页，此时可以看到首页显示token信息。