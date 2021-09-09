package com.soft.method.cros;

//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


/**
 * Spring Security 对于 CORS 的支持
 * @author suphowe
 */
//@Configuration
//@Order(Ordered.HIGHEST_PRECEDENCE)
public class SecurityConfig {
//public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .permitAll()
//                .and()
//                .httpBasic()
//                .and()
//                .cors()                     // .cor开启跨域支持
//                .and()
//                .csrf()
//                .disable();
//    }

//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowCredentials(true);
//        configuration.setAllowedOrigins(Arrays.asList("*"));
//        configuration.setAllowedMethods(Arrays.asList("*"));
//        configuration.setAllowedHeaders(Arrays.asList("*"));
//        configuration.setMaxAge(Duration.ofHours(1));
//        source.registerCorsConfiguration("/**",configuration);
//        return source;
//    }
}
