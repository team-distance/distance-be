package io.festival.distance.infra.swagger;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration  //스프링 실행시 설정파일 읽어드리기 위한 어노테이션
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket authApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .groupName("로그인이 필요한 API")
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("io.festival.distance"))
            .paths(Predicates.not(Predicates.or(
                PathSelectors.ant("/api/login/**"),
                PathSelectors.ant("/api/member/signup/**"),
                PathSelectors.ant("/api/admin/signup/**")
            )))

            .build();
    }

    @Bean
    public Docket nonAuthApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .groupName("로그인이 불필요한 API")
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("io.festival.distance"))
            .paths(Predicates.or(
                PathSelectors.ant("/api/login/**"),
                PathSelectors.ant("/api/member/signup/**"),
                PathSelectors.ant("/api/admin/signup/**")
            ))
            .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("9OORMTHON-UNIV-TEAM-05_DISTANCE")
            .version("1.0.0")
            .description("구름톤 유니브 5팀 distance API명세서입니다")
            .build();
    }
}
