package edu.paper.guider.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 @Api: украсить весь класс и описать роль контроллера
 @ApiOperation: описать метод класса или интерфейс
 @ApiParam: описание одного параметра
 @ApiModel: использовать объекты для получения параметров
 @ApiProperty: при получении параметров с объектом, опишите поле объекта
 @ApiResponse: 1 описание ответа HTTP
 @ApiResponses: общее описание ответа HTTP.
 @ApiIgnore: используйте эту аннотацию, чтобы игнорировать этот API
 @ApiError: информация, возвращаемая при возникновении ошибки
 @ApiImplicitParam: параметр запроса
 @ApiImplicitParams: несколько параметров запроса
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    /**
     * Создать приложение API
     * apiInfo () добавляет информацию, связанную с API
     * Верните экземпляр ApiSelectorBuilder с помощью функции select (), чтобы контролировать, какие интерфейсы отображаются в Swagger для отображения,
     * В этом примере для определения каталога, в котором будет создан API, используется указанный путь к отсканированному пакету.
     *
     * @return
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis (RequestHandlerSelectors.basePackage ("edu.paper.guider.controller")) // Пакет сканирования Swagger
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * Создайте основную информацию API (основная информация будет отображаться на странице документа)
     * Адрес для посещения: http: // фактический адрес проекта / swagger-ui.html
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //Заголовок страницы
                .title ("Spring Boot в сочетании с Swagger2 для создания RESTful API")
                // основатель
                //Описание
                .termsOfServiceUrl("http://blog.csdn.net/canfengli")
                //номер версии
                .version("1.0")
                .build();
    }
}