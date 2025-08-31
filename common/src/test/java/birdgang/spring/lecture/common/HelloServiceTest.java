package birdgang.spring.lecture.common;

import birdgang.spring.lecture.common.service.HelloDecorator;
import birdgang.spring.lecture.common.service.SimpleHelloService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HelloServiceTests {

    @Test
    void simpleHelloService() {
        SimpleHelloService helloService = new SimpleHelloService();
        String ret = helloService.sayHello("Test");
        assertThat(ret).isEqualTo("Hello Test");
    }

    @Test
    void helloDecorator() {
        HelloDecorator decorator = new HelloDecorator(name -> name);
        String ret = decorator.sayHello("Test");
        Assertions.assertThat(ret).isEqualTo("*Test*");
    }
}
