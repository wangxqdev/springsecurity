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