package com.bantads.account.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(RabbitMQConfiguration.class)
public class AccountConfiguration {

    @Bean
    public Queue createAccountQueue(@Value("${rabbitmq.create}") String createAccountRouting) {
        return new Queue(createAccountRouting, true);
    }

    @Bean
    public Queue updateAccountQueue(@Value("${rabbitmq.update}") String updateAccountRouting) {
        return new Queue(updateAccountRouting, true);
    }

    @Bean
    public Queue deleteAccountQueue(@Value("${rabbitmq.delete}") String deleteAccountRouting) {
        return new Queue(deleteAccountRouting, true);
    }

    @Bean
    public Queue patchAccountQueue(@Value("${rabbitmq.patch.consumer}") String patchAccountRouting) {
        return new Queue(patchAccountRouting, true);
    }

    @Bean
    Binding createAccountBinding(Queue createAccountQueue, DirectExchange exchange, @Value("${rabbitmq.create}") String createAccountRouting) {
        return BindingBuilder.bind(createAccountQueue).to(exchange).with(createAccountRouting);
    }

    @Bean
    Binding updateAccountBinding(Queue updateAccountQueue, DirectExchange exchange, @Value("${rabbitmq.update}") String updateAccountRouting) {
        return BindingBuilder.bind(updateAccountQueue).to(exchange).with(updateAccountRouting);
    }

    @Bean
    Binding deleteAccountBinding(Queue deleteAccountQueue, DirectExchange exchange, @Value("${rabbitmq.delete}") String deleteAccountRouting) {
        return BindingBuilder.bind(deleteAccountQueue).to(exchange).with(deleteAccountRouting);
    }

    @Bean
    Binding patchAccountBinding(Queue patchAccountQueue, DirectExchange exchange, @Value("${rabbitmq.patch.consumer}") String patchAccountRouting) {
        return BindingBuilder.bind(patchAccountQueue).to(exchange).with(patchAccountRouting);
    }
}
