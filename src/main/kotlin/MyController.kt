package org.example

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.web.bind.annotation.*
import org.springframework.http.HttpHeaders
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken


@RestController
class MyController {

    // This method will handle GET requests to /hello
    @GetMapping("/hello")
    fun sayHello(): String {
        return "Hello from the API!"
    }

    @GetMapping("/helloauth")
    fun sayHelloAuth(): String {
        return "Hello from the auth API!"
    }


    @GetMapping("/")
    @ResponseBody
    fun home(@AuthenticationPrincipal oidcUser: OidcUser?): String {
        return if (oidcUser != null) {
            "Logged in as: ${oidcUser.fullName}, Email: ${oidcUser.email}"
        } else {
            "Welcome! Please log in."
        }
    }

//    @GetMapping("/login")
//    fun login():String{
//        return "redirect:/oauth2/authorization/keycloak"
//    }
//
//    @GetMapping("/login")
//    fun login(): ResponseEntity<Void> {
//        // Directly redirect to Spring Security's built-in OAuth2 authorization route
//        return ResponseEntity.status(HttpStatus.FOUND)
//            .header(HttpHeaders.LOCATION, "/oauth2/authorization/keycloak")
//            .build()
//    }

//    @GetMapping("login")
//    fun login():String{
//        return "login"
//    }


    @GetMapping("/userinfo")
    fun getUserInfo(): ResponseEntity<Map<String, Any>> {
        println(SecurityContextHolder.getContext().authentication)
        // Get the authentication object from the security context
        val authentication: Authentication? = SecurityContextHolder.getContext().authentication

        // Check if the user is authenticated
        if (authentication != null && authentication.isAuthenticated) {
            val oauth2Authentication = authentication as OAuth2AuthenticationToken
            val userDetails = oauth2Authentication.principal

            // Extract the user attributes
            val userAttributes = userDetails.attributes

            return ResponseEntity.ok(userAttributes)
        }

        // Return an unauthorized response if not authenticated
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
    }
}


