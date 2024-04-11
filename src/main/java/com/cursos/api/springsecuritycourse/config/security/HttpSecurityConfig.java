package com.cursos.api.springsecuritycourse.config.security;

import com.cursos.api.springsecuritycourse.config.security.filter.JwtAuthenticationFilter;
import com.cursos.api.springsecuritycourse.persistence.util.roleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity(prePostEnabled = true)
public class HttpSecurityConfig {
    @Autowired
    private AuthenticationProvider daoAuthProvider;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;
    @Autowired
    private AuthorizationManager<RequestAuthorizationContext> authorizationManager;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        SecurityFilterChain filterChain = http
                .csrf(csrfConfig -> csrfConfig.disable())
                .sessionManagement(sessMagConfig -> sessMagConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(daoAuthProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authReqConfig -> {
                    authReqConfig.anyRequest().access(authorizationManager);
                })
                .exceptionHandling(exceptionConfig -> {
                    exceptionConfig.authenticationEntryPoint(authenticationEntryPoint);
                    exceptionConfig.accessDeniedHandler(accessDeniedHandler);
                })
                .build();
        return filterChain;
    }
    private static void buildRequestMatchers(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authReqConfig) {
        /*
        Autorizacion de endpoints de products
        */
        authReqConfig.requestMatchers(HttpMethod.GET, "/products")
                .hasAnyRole(roleEnum.ADMINISTRATOR.name(), roleEnum.ASSISTANT_ADMINISTRATOR.name());
//        authReqConfig.requestMatchers(HttpMethod.GET, "/products/{productId}")
        authReqConfig.requestMatchers(RegexRequestMatcher.regexMatcher(HttpMethod.GET, "/products/[0-9]*"))
                .hasAnyRole(roleEnum.ADMINISTRATOR.name(), roleEnum.ASSISTANT_ADMINISTRATOR.name());
        authReqConfig.requestMatchers(HttpMethod.POST, "/products")
                .hasRole(roleEnum.ADMINISTRATOR.name());
        authReqConfig.requestMatchers(HttpMethod.PUT, "/products/{productId}")
                .hasAnyRole(roleEnum.ADMINISTRATOR.name(), roleEnum.ASSISTANT_ADMINISTRATOR.name());
        authReqConfig.requestMatchers(HttpMethod.PUT, "/products/{productId}/disabled")
                .hasRole(roleEnum.ADMINISTRATOR.name());

        /*
        Autorizacion de endpoints de categories
        */
        authReqConfig.requestMatchers(HttpMethod.GET, "/categories")
                .hasAnyRole(roleEnum.ADMINISTRATOR.name(), roleEnum.ASSISTANT_ADMINISTRATOR.name());
        authReqConfig.requestMatchers(HttpMethod.GET, "/categories/{categoryId}")
                .hasAnyRole(roleEnum.ADMINISTRATOR.name(), roleEnum.ASSISTANT_ADMINISTRATOR.name());
        authReqConfig.requestMatchers(HttpMethod.POST, "/categories")
                .hasRole(roleEnum.ADMINISTRATOR.name());
        authReqConfig.requestMatchers(HttpMethod.PUT, "/categories/{categoryId}")
                .hasAnyRole(roleEnum.ADMINISTRATOR.name(), roleEnum.ASSISTANT_ADMINISTRATOR.name());
        authReqConfig.requestMatchers(HttpMethod.PUT, "/categories/{categoryId}/disabled")
                .hasRole(roleEnum.ADMINISTRATOR.name());
        authReqConfig.requestMatchers(HttpMethod.GET, "/auth/profile")
                .hasAnyRole(roleEnum.ADMINISTRATOR.name(), roleEnum.ASSISTANT_ADMINISTRATOR.name(),
                        roleEnum.CUSTOMER.name());



                    /*
                    Autorizacion de endpoints publicos
                    */
        authReqConfig.requestMatchers(HttpMethod.POST, "/customers").permitAll();
        authReqConfig.requestMatchers(HttpMethod.POST, "/auth/authenticate").permitAll();
        authReqConfig.requestMatchers(HttpMethod.GET, "/auth/validate-token").permitAll();

        authReqConfig.anyRequest().authenticated();
    }
    private static void buildRequestMatchersV2(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authReqConfig) {


        /*
        Autorizacion de endpoints publicos
        */
        authReqConfig.requestMatchers(HttpMethod.POST, "/customers").permitAll();
        authReqConfig.requestMatchers(HttpMethod.POST, "/auth/authenticate").permitAll();
        authReqConfig.requestMatchers(HttpMethod.GET, "/auth/validate-token").permitAll();

        authReqConfig.anyRequest().authenticated();
    }
}
