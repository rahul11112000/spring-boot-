package com.example.digitalwallet.configration;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import jakarta.servlet.DispatcherType;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
 
@Configuration
public class RestConfig {

   @Value("${jwt.public.key}")
   RSAPublicKey key;

   @Value("${jwt.private.key}")
   RSAPrivateKey priv;

   @SuppressWarnings("removal")
   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      // @formatter:off
      http
            .authorizeHttpRequests((authorize) -> authorize
               .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
               .requestMatchers("/admin/**").hasAuthority("ADMIN")
               .requestMatchers("/client/**").hasAuthority("CLIENT")
               .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
               .requestMatchers("/signin").permitAll()
               .requestMatchers("/").permitAll()
               .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
               .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
            )
            .csrf((csrf) -> csrf.ignoringRequestMatchers("/token"))
            .csrf((csrf) -> csrf.disable())
            .httpBasic(Customizer.withDefaults())
            .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
            .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling((exceptions) -> exceptions
                     .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                     .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
            );
      // @formatter:on
      return http.build();
   }

   @Bean
   JwtDecoder jwtDecoder() {
      return NimbusJwtDecoder.withPublicKey(this.key).build();
   }

   @Bean
   JwtEncoder jwtEncoder() {
      JWK jwk = new RSAKey.Builder(this.key).privateKey(this.priv).build();
      JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
      return new NimbusJwtEncoder(jwks);
   }

   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }


   private JwtAuthenticationConverter jwtAuthenticationConverter() {
      JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

      // Configure JwtGrantedAuthoritiesConverter without a prefix
      converter.setJwtGrantedAuthoritiesConverter(jwt -> {
         JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
         authoritiesConverter.setAuthoritiesClaimName("scope"); // Use "role" claim in JWT
         authoritiesConverter.setAuthorityPrefix(""); // No prefix added
         return authoritiesConverter.convert(jwt);
      });

      return converter;
   }

}

