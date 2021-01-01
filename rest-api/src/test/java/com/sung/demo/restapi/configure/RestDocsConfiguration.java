package com.sung.demo.restapi.configure;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import org.springframework.boot.test.autoconfigure.restdocs.RestDocsMockMvcConfigurationCustomizer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class RestDocsConfiguration {

  @Bean
  public RestDocsMockMvcConfigurationCustomizer configurationCustomizer() {
    return configurer -> configurer.operationPreprocessors().withRequestDefaults(prettyPrint())
        .withResponseDefaults(prettyPrint());
    // return new RestDocsMockMvcConfigurationCustomizer() {
    //
    // @Override
    // public void customize(MockMvcRestDocumentationConfigurer configurer) {
    // configurer.operationPreprocessors()
    // .withRequestDefaults(prettyPrint())
    // .withResponseDefaults(prettyPrint());
    // }
    // };
  }
}
