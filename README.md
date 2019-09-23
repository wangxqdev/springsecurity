1. FilterChain
    SecurityContextPersistentFilter ↔ UsernamePasswordAuthenticationFilter ↔ BasicAuthenticationFilter ↔ RememberMeAuthenticationFilter ↔ ... ↔ ExceptionTranslationFilter ↔ FilterSecurityInterceptor ↔ RestAPI
2. Authentication
    UsernamePasswordAuthenticationFilter → Authentication(未认证) → AuthenticationManager → Authentication Provider → UserDetailsService → Authentication(已认证 ) → SecurityContext → SecurityContextHolder → SecurityContextPersistentFilter
3. Properties Override
    request → app → default 
4.  RememberMe Function
    Browser → UsernamePasswordAuthenticationFilter → RememberMeService → Token → DB
                                                                               → Browser
            → RememberMeAuthenticationFilter → RememberMeService → Browser → Token → DB → UserDetailsService
5. OAuth2 Role
    a. 服务供应商(Provider)
        1. 认证服务器(Authorization Server)
        2. 资源服务器(Resource Server)
    b. 第三方应用(Client)
    c. 资源拥有者(Resource Owner)
6. Authentication Mode
    a. 授权码模式(authorization code)
    b. 简化模式(implicit)
    c. 密码模式(resource owner password credentials)
    d. 客户端模式(client credentials)
7. OAuth2 Flow(a mode)
    a. 访问客户端: Resource Owner → Client
    b. 将用户导向认证服务器: Client → Authorization Server
    c. 用户同意授权: Resource Owner →  Authorization Server
    d. 返回客户端并携带授权码: Authorization Server → Client
    e. 申请令牌: Client → Authorization Server
    f. 发放令牌: Authorization Server → Client
    g. 申请资源: Client → Resource Server
    h. 开放资源: Resource Server → Client
8. OAuth2 Flow(b mode)
    a. 访问客户端: Resource Owner → Client
    b. 将用户导向认证服务器: Client → Authorization Server
    c. 用户同意授权: Resource Owner →  Authorization Server
    d. 返回客户端并携带令牌: Authorization Server → Client
    e. 申请资源: Client → Resource Server
    f. 开放资源: Resource Server → Client
9. OAuth2 Flow(c/d mode)
    a. 访问客户端: Resource Owner → Client
    b. 请求授权: Client → Resource Owner
    c. 同意授权: Resource Owner → Client
    d. 申请令牌: Client → Authorization Server
    e. 发放令牌: Authorization Server → Client
    f. 申请资源: Client → Resource Server
    g. 开放资源: Resource Server → Client