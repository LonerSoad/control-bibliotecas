package com.lonner.biblioteca.configuration;

import com.lonner.biblioteca.entities.Usuario;
import com.lonner.biblioteca.enums.ExistsCheck;
import com.lonner.biblioteca.exceptions.BusinessException;
import com.lonner.biblioteca.repositories.UsuarioRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Log4j2
@RequiredArgsConstructor
public class WebSecurityConfiguration  extends WebSecurityConfigurerAdapter {

     private final Cipher cipherData;
     private final UsuarioRepository usuarioRepository;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService((String nombreUsuario) -> {
            log.info("Iniciando sesión de usuario {}  ",nombreUsuario);
            Usuario usuario = usuarioRepository.buscarPorNombreUsuario(nombreUsuario, ExistsCheck.EXCEPTION_IF_NOT_EXISTS);
            if (usuario == null) {
                throw new BusinessException("Las credenciales son incorrectas");
            }
            List<GrantedAuthority> auths = new ArrayList<>();
            User user = new User(nombreUsuario, usuario.getPassword(), auths);
            return user;
        }).passwordEncoder(passwordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().disable();
        http.cors().disable();
        http.authorizeRequests()
                .antMatchers("/api/enviar-token","/api/enviar-usuario","/login",
                        "/api/cambiar-contrasena","/pagina_error.xhtml","/api/external-login","/api/sincronizar-usuario"

                ).permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()));
    }


    public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

        private AuthenticationManager authenticationManager;

        public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
            this.authenticationManager = authenticationManager;
        }

        @Override
        public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
            try {
                String nombreUsuario = request.getParameter("nombreUsuario");
                String contrasena = request.getParameter("contrasena");
                return this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(nombreUsuario, contrasena, new ArrayList<>()));
            } catch (AuthenticationException ex) {
                throw new BusinessException("Error en la autenticación",ex);
            }
        }

        @Override
        protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
            JwtBuilder builder = Jwts.builder();
            builder.setSubject(((User) authResult.getPrincipal()).getUsername());
            builder.setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME));
            builder.signWith(cipherData.getAlgorithm(),cipherData.getPrivateKey());
            //response.addCookie(new Cookie(SecurityConstants.COOKIE_NAME, SecurityConstants.TOKEN_PREFIX + builder.compact()));
            response.addHeader(SecurityConstants.AUTHORIZATION_HEADER_NAME, builder.compact());
        }
    }

    public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

        public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
            super(authenticationManager);
        }

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

            String jwt = request.getHeader(SecurityConstants.AUTHORIZATION_HEADER_NAME);
            if (Objects.isNull(jwt)) {
                chain.doFilter(request, response);
                return;
            }
            try {
                UsernamePasswordAuthenticationToken authentication = getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                chain.doFilter(request, response);
            } catch (Exception exception) {
                throw new BusinessException("Error al validar el jwt",exception);
            }
        }


        private UsernamePasswordAuthenticationToken getAuthentication(String jwt) {
            if (Objects.nonNull(jwt)) {
                // parse the token.
                Jws<Claims> parseClaimsJws = Jwts.parser()
                        .setSigningKey(cipherData.getPrivateKey())
                        .parseClaimsJws(jwt);
                if(SignatureAlgorithm.forName(parseClaimsJws.getHeader().getAlgorithm()) != SignatureAlgorithm.RS512){
                    throw new BusinessException("Token Corrupto");
                }
                Claims body = parseClaimsJws.getBody();
                String user = body.getSubject();
                if (user != null) {
                    return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
                }
            }
            return null;
        }

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }

}
