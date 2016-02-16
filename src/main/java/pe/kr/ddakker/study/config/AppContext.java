package pe.kr.ddakker.study.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
//@EnableJpaRepositories(basePackages = {"io.vertx.examples.spring.repository"})
@PropertySource(value = { "classpath:properites/global-properties.xml" })
//@ComponentScan("io.vertx.examples.spring.service")
public class AppContext {
	/*
	*/
}
