package com.yu.project08.security;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Log
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true) // secured 어노테이션 사용 가능
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource; // remember-me를 위해

    @Autowired
    PrincipalDetailsService principalDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("security config...................");

        http.authorizeRequests().antMatchers("/guest/**").permitAll();
        http.authorizeRequests().antMatchers("/manager/**").hasRole("MANAGER");
        http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");

        http.formLogin().loginPage("/login");

        // Authorize에 실패했을 때 보여주는 안내창
        http.exceptionHandling().accessDeniedPage("/accessDenied");

        // 로그아웃(세션 무효화)
        http.logout().logoutUrl("/logout").invalidateHttpSession(true); // deleteCookies()를 사용할 수도 있음

        http.rememberMe()
                .key("yu")
                .userDetailsService(principalDetailsService) // 커스텀한 UserDetailService를 사용
                .tokenRepository(getJDBCRepository())
                .tokenValiditySeconds(60 * 60 * 24); // 24시간동안 remember-me 쿠키가 유지됨
    }

    // 데이터베이스를 이용한 remember-me를 구현하기 위해
    private PersistentTokenRepository getJDBCRepository(){
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setDataSource(dataSource);
        return repository;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(principalDetailsService).passwordEncoder(passwordEncoder()); // 패스워드 인코더 사용
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
//        log.info("build Auth global......................");
//
//        // 메모리 기반 인증 매니저
////        auth.inMemoryAuthentication()
////                .withUser("manager")
////                .password("1111")
////                .roles("MANAGER");
//
//        // DataSource와 SQL을 이용해서 데이터베이스와 연동한(JDBC) 인증 매니저 사용
//        String query1 = "SELECT uid username, upw password, true enabled FROM member WHERE uid= ?"; // enabled 컬럼은 해당 계정이 사용 가능한지.
//        String query2 = "SELECT member uid, role_name role FROM member_role WHERE member= ?";
//
//     auth.jdbcAuthentication()
//             .dataSource(dataSource)
//             .usersByUsernameQuery(query1)
//             .rolePrefix("ROLE_")
//             .authoritiesByUsernameQuery(query2);
//    }
}
