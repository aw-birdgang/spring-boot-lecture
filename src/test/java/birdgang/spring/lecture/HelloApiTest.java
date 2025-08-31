package birdgang.spring.lecture;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HelloController.class)
class HelloApiTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HelloService helloService;

    @Test
    void helloApi() throws Exception {
        // http localhost:8080/hello?name=Spring
        // HTTPie
        when(helloService.sayHello("Spring")).thenReturn("*Hello Spring*");
        
        mockMvc.perform(get("/hello")
                .param("name", "Spring"))
                .andExpect(status().isOk())
                .andExpect(header().string("content-type", "text/plain;charset=UTF-8"))
                .andExpect(content().string("*Hello Spring*"));
    }

    @Test
    void falseHelloApi() throws Exception {
        // http localhost:8080/hello?name=
        // HTTPie
        mockMvc.perform(get("/hello")
                .param("name", ""))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid name parameter"));
    }
}
