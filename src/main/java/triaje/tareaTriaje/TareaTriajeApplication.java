package triaje.tareaTriaje;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit

public class TareaTriajeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TareaTriajeApplication.class, args);
	}

}
