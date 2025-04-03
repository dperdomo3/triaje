package triaje.tareaTriaje.configuration;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${exchange}")
    private String exchange;

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Queue queueGeneral() {
        return new Queue("pacientes.general");
    }

    @Bean
    public Queue queueGrave() {
        return new Queue("pacientes.grave");
    }

    @Bean
    public Queue queueCritico() {
        return new Queue("pacientes.critico");
    }

    @Bean
    public Binding bindingGeneral(Queue queueGeneral, TopicExchange exchange) {
        return BindingBuilder.bind(queueGeneral).to(exchange).with("pacientes.general");
    }

    @Bean
    public Binding bindingGrave(Queue queueGrave, TopicExchange exchange) {
        return BindingBuilder.bind(queueGrave).to(exchange).with("pacientes.grave");
    }

    @Bean
    public Binding bindingCritico(Queue queueCritico, TopicExchange exchange) {
        return BindingBuilder.bind(queueCritico).to(exchange).with("pacientes.critico");
    }
}
