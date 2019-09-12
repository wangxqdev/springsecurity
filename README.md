1. FilterChain
    SecurityContextPersistentFilter ↔ UsernamePasswordAuthenticationFilter ↔ BasicAuthenticationFilter ↔ ... ↔ ExceptionTranslationFilter ↔ FilterSecurityInterceptor ↔ RestAPI
2. Authentication
    UsernamePasswordAuthenticationFilter → Authentication(未认证) → AuthenticationManager → Authentication Provider → UserDetailsService → Authentication(已认证 ) → SecurityContext → SecurityContextHolder → SecurityContextPersistentFilter
3. Properties
    request → app → default  