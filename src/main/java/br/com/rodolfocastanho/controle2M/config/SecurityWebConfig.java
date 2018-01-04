package br.com.rodolfocastanho.controle2M.config;

import br.com.rodolfocastanho.controle2M.security.Controle2MUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityWebConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Controle2MUserDetailsService controle2MUserDetailsService;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
          .antMatchers("/resources/**", "/erro", "/vendor/**", "/images/**", "/javascript/**", "/stylesheet/**").permitAll()
          .antMatchers("/javax.faces/**", "/org/primefaces/**", "/META-INF/**").permitAll()

          .antMatchers("/relatorio/**").permitAll()//hasRole("ADMIN")
          .antMatchers("/ordem/novo").permitAll()//hasRole("ADMIN")
          .antMatchers("/usuario/**").permitAll()//hasRole("ADMIN")

                //.antMatchers("/usuario/**").permitAll()

           /*
          .antMatchers("/ordem").hasRole("OPER")
          .antMatchers("/ordem/aprovisionamento/**").hasRole("OPER")
          .antMatchers("/ordem/comissionamento/**").hasRole("OPER")
          */

          .anyRequest().authenticated()
          .and()
          //.httpBasic();
          .formLogin()
            .loginPage("/login").permitAll()
            .and()
            .rememberMe()
        ;

    }

    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder
                .userDetailsService(controle2MUserDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
        /*
        builder
          .inMemoryAuthentication()
              .withUser("garrincha").password("123").roles("USER")
              .and()
              .withUser("zico").password("123").roles("USER");
       */
    }

}
