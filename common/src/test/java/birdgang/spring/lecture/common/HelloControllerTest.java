package birdgang.spring.lecture.common;

import birdgang.spring.lecture.common.controller.HelloController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HelloControllerTests {
    @Test
    void simpleHelloController() {
        HelloController helloController = new HelloController(name -> name);
        String ret = helloController.hello("Test");
        assertThat(ret).isEqualTo("Test");
    }

    @Test
    void falseSimpleHelloController() {
        HelloController helloController = new HelloController(name -> name);
        Assertions.assertThatThrownBy(() -> {
            String ret = helloController.hello(null);
        }).isInstanceOf(IllegalArgumentException.class);

        Assertions.assertThatThrownBy(() -> {
            String ret = helloController.hello("");
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
