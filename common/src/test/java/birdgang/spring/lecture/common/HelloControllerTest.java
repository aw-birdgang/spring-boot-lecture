package birdgang.spring.lecture.common;

import birdgang.spring.lecture.common.controller.HelloController;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class HelloControllerTests {
    @Test
    void simpleHelloController() {
        HelloController helloController = new HelloController(name -> name);
        ResponseEntity<String> response = helloController.hello("Test");
        assertThat(response.getBody()).isEqualTo("Test");
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void helloWithEmptyName() {
        HelloController helloController = new HelloController(name -> name);
        ResponseEntity<String> response = helloController.hello(""); // 빈 문자열은 validation에서 처리됨
        assertThat(response.getBody()).isEqualTo("");
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }
}
