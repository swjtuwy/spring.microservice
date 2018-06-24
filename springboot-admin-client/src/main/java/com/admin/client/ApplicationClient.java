package com.admin.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.support.SpringBootServletInitializer;

//public class ApplicationClient extends SpringBootServletInitializer implements EmbeddedServletContainerCustomizer {
public class ApplicationClient {
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(ApplicationClient.class);
//    }


    public static void main(String[] args) throws Exception {
        SpringApplication.run(ApplicationClient.class, args);
    }

//    public void customize(ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {
//        //	configurableEmbeddedServletContainer.setPort(9090);
//    }

}
