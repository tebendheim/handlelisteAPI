package org.example

import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.logout.LogoutHandler
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.stereotype.Service
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity(debug = true)
class SecurityConfig(private val logoutHandler: KeycloakLogoutHandler) {


    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .oauth2Login{auth -> auth

            }

            .logout { logout ->
                logout
                    .logoutUrl("/logout") // Ensure you're testing with /test, not /logout
                    .addLogoutHandler(logoutHandler)
                    .logoutSuccessHandler(logoutHandler)
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessUrl("/hello")
                    .permitAll()  // Allow access to the /test logout endpoint for all users
            }

            .authorizeHttpRequests { authz ->
                authz
                    .requestMatchers("/error").permitAll()
                    .requestMatchers("/login").permitAll()
                    .requestMatchers("/hello/**").permitAll()
                    .requestMatchers("/helloauth").authenticated() // Use authenticated instead of fullyAuthenticated if needed
                    .anyRequest().authenticated()
            }

            .csrf { csrf ->
                csrf.disable() // Disable CSRF protection for testing logout
            }  // Consider re-enabling it with proper configuration later

        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): UrlBasedCorsConfigurationSource {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.addAllowedOrigin("http://localhost:3000") // Your frontend URL
        config.addAllowedHeader("*") // Allow all headers
        config.addAllowedMethod("*") // Allow all methods
        source.registerCorsConfiguration("/**", config)
        return source
    }
}


//
    @Service
    class KeycloakLogoutHandler : LogoutHandler, LogoutSuccessHandler {

        override fun logout(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication?) {
            // Invalidate session cookie
            val cookie = Cookie("JSESSIONID", null)
            cookie.maxAge = 0
            cookie.path = "/"  // Make sure the path matches the original cookie path
            response.addCookie(cookie)
        }

        override fun onLogoutSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication?) {

            println("\n\nKeycloakLogoutHandler: Logout success...")
            // Retrieve ID token from the authenticated user
            val oidcUser = authentication?.principal as? OidcUser
            val idToken = oidcUser?.idToken?.tokenValue

            // Keycloak logout URL with post logout redirect and id_token_hint
            // redirect delen her må være aktiv redirect link i admin konsoll i keycloak.
            val keycloakLogoutUrl = if (idToken != null) {
                "http://localhost:8080/realms/handleliste/protocol/openid-connect/logout" +
                        "?id_token_hint=$idToken" +
                        "&post_logout_redirect_uri=http://localhost:8081/hello" // Redirect after logout
            } else {
                "http://localhost:8080/realms/handleliste/protocol/openid-connect/logout" +
                        "?post_logout_redirect_uri=http://localhost:8081/hello" // Redirect after logout
            }

            // Redirect to Keycloak logout URL
            response.sendRedirect(keycloakLogoutUrl)
        }
    }






