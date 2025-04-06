package triaje.tareaTriaje.configuration;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.core.Binding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;

@Configuration
public class RabbitMQConfig {
    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter converter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(converter);
        return template;
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("triaje-exchange");
    }

    @Bean
    public Queue colaLeve() {
        return new Queue("pacientes.leve", true); // durable = true
    }

    @Bean
    public Queue colaGrave() {
        return new Queue("pacientes.grave", true);
    }

    @Bean
    public Queue colaCritico() {
        return new Queue("pacientes.critico", true);
    }

    @Bean
    public Binding bindingLeve(Queue colaLeve, TopicExchange topicExchange) {
        return BindingBuilder.bind(colaLeve).to(topicExchange).with("pacientes.leve");
    }

    @Bean
    public Binding bindingGrave(Queue colaGrave, TopicExchange topicExchange) {
        return BindingBuilder.bind(colaGrave).to(topicExchange).with("pacientes.grave");
    }

    @Bean
    public Binding bindingCritico(Queue colaCritico, TopicExchange topicExchange) {
        return BindingBuilder.bind(colaCritico).to(topicExchange).with("pacientes.critico");
    }

}