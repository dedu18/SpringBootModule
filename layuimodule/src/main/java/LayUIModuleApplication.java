import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.dedu.*"})
@EnableEurekaClient
public class LayUIModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(LayUIModuleApplication.class, args);
    }
}
