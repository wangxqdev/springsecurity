1. FilterChain
    SecurityContextPersistentFilter ↔ UsernamePasswordAuthenticationFilter ↔ BasicAuthenticationFilter ↔ ... ↔ ExceptionTranslationFilter ↔ FilterSecurityInterceptor ↔ RestAPI
2. Authentication
    UsernamePasswordAuthenticationFilter → Authentication(未认证) → AuthenticationManager → AuthenticationProvider → UserDetailsService → Authentication → SecurityContext → SecurityContextHolder → SecurityContextPersistentFilter 