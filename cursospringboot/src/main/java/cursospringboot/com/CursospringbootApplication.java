package cursospringboot.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackages = "cursospringboot.model")
@ComponentScan(basePackages= {"cursospringboot.*"})
@EnableJpaRepositories(basePackages = {"cursospringboot.repository"})
@EnableTransactionManagement
public class CursospringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(CursospringbootApplication.class, args);
	}

}
