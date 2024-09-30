package org.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import io.github.cdimascio.dotenv.Dotenv
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration


// spring boot needs this annotation
@SpringBootApplication
class Application {

//    The @JvmStatic annotation is needed so that Kotlin’s
//    companion object behaves like a static method in Java, which is
//    necessary for Spring Boot to find the main method in the Application class.
    companion object {

        // setting as static method (needed for spring boot
        @JvmStatic
        fun main(args: Array<String>) {

            // setting the .env file to the system
            val dotenv = Dotenv.load()
            val clientSecret = dotenv["KEYCLOAK_CLIENT_SECRET"]
            System.setProperty("spring.security.oauth2.client.registration.keycloak.client-secret", clientSecret!!)

            // print statement
            println("Hello from Application!")

            // running application (spring boot)
            runApplication<Application>(*args)
        }
    }
}


//    Why not use a top-level main() function instead?
//    Kotlin allows you to define a main() function at the top level, outside any class, like this:
//
//    kotlin
//    Copy code
//    fun main(args: Array<String>) {
//        println("Hello World")
//        runApplication<Application>(*args)
//    }
//    However, Spring Boot expects the main() method to be in the class annotated with @SpringBootApplication, which is why putting the main() inside the Application class (with the companion object) is a more Spring-compliant approach.
