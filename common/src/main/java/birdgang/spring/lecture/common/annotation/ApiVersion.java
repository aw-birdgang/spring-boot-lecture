package birdgang.spring.lecture.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * API 버전을 지정하는 어노테이션
 * 클래스 레벨에서 사용하여 해당 컨트롤러의 API 버전을 지정
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiVersion {
    
    /**
     * API 버전 (예: "v1", "v2")
     */
    String value();
    
    /**
     * API 버전 설명
     */
    String description() default "";
    
    /**
     * 해당 버전이 deprecated인지 여부
     */
    boolean deprecated() default false;
}
