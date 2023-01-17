package sia.tacocloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TacoCloudApplication {

    //https://github.com/habuma/spring-in-action-6-samples/tree/main/ch03/tacos-jdbctemplate/src/main/java/tacos/data
    public static void main(String[] args) {
        SpringApplication.run(TacoCloudApplication.class, args);
    }

}
