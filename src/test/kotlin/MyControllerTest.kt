package org.example

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest // Start the full application context for tests
@AutoConfigureMockMvc // Enable MockMvc for testing
class MyControllerTest {


    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `should return Hello from the API`() {
        // Use MockMvc to simulate a GET request to /hello
        val result = mockMvc.get("/hello")
            .andExpect {
                status { isOk() } // Expect HTTP 200 OK
            }
            .andReturn()

        // Verify the response body
        val responseBody = result.response.contentAsString
        assertEquals("Hello from the API!", responseBody)
    }
}