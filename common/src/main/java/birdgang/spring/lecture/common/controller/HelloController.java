package birdgang.spring.lecture.common.controller;

import birdgang.spring.lecture.common.service.HelloService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Hello API", description = "인사말을 제공하는 API")
public class HelloController {
    private final HelloService helloService;

    @Autowired
    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/hello")
    @Operation(summary = "인사말 조회", description = "이름을 받아서 인사말을 반환합니다.")
    public ResponseEntity<String> hello(
        @Parameter(description = "인사말을 받을 사람의 이름", example = "Spring")
        @RequestParam @NotBlank(message = "이름은 필수입니다") String name
    ) {
        String greeting = helloService.sayHello(name);
        return ResponseEntity.ok(greeting);
    }
}
