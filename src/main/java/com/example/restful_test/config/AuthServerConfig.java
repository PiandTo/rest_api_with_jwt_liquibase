//package com.example.restful_test.config;
//
//import com.example.restful_test.security.CustomUserDetailsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//
//@Configuration
//@EnableAuthorizationServer
//public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {
//    @Autowired
//    private AuthenticationManager customUserDetailsService;
//
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        endpoints.authenticationManager(customUserDetailsService);
//    }
//}
