package uk.co.ameth.aws.example;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import uk.co.ameth.aws.example.service.ExampleServiceA;
import uk.co.ameth.aws.example.service.ExampleServiceB;

@Configuration
@ComponentScan("uk.co.ameth")
@PropertySource("classpath:secure.properties")
public class ApplicationConfiguration {

    @Bean
    public ExampleServiceA exampleServiceA() {
        return new ExampleServiceA();
    }
    @Bean
    public ExampleServiceB exampleServiceB() {
        return new ExampleServiceB();
    }

//    @Bean
//    public Controller controller() { return new Controller();}
}
