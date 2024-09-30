package org.example

import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.web.bind.annotation.*
import jakarta.servlet.http.Cookie;
import org.springframework.security.core.context.SecurityContextHolder
import java.net.URI

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

    @GetMapping("/login")
    fun login(): ResponseEntity<Void> {
//        val redirectUri =
//            "http://localhost:8080/realms/handleliste/protocol/openid-connect/auth?response_type=code&client_id=my-client&scope=openid%20profile%20email&state=EV949aBHKUeaReZ5WGerJ5K4iWSlCi3C4VTXTkeENpY%3D&redirect_uri=http://localhost:8081/helloauth"
//        return ResponseEntity.status(HttpStatus.FOUND).location(URI(redirectUri)).build()

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
    }

}


