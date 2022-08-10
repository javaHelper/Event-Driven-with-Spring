package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.util.function.Consumer;
import java.util.function.Function;

@SpringBootApplication
public class EventDrivenWithSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventDrivenWithSpringApplication.class, args);
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    @Bean
    public Function<String, String> uppercase1(){
        return String::toUpperCase;
    }

    @Bean
    public Function<Message<String>, String> uppercase() {
        return FunctionLib.uppercase();
    }

    @Bean
    public Function<String, String> echo() {
        return message -> {
            System.out.println("Echo :" + message);
            return message;
        };
    }

    @Bean
    public Function<Flux<Integer>, Flux<Integer>> multiplier() {
        return FunctionLib.multiplier();
    }

    @Bean
    public Function<Flux<Integer>, Flux<Integer>> aggregator() {
        return FunctionLib.aggregator();
    }

    @Bean
    public Function<Flux<Integer>, Tuple2<Flux<String>, Flux<String>>> router1() {
        return FunctionLib.router();
    }

    @Bean
    public Consumer<String> evenLogger() {
        return s -> System.out.println("Even Logger :" + s);
    }

    @Bean
    public Consumer<String> oddLogger() {
        return s -> System.out.println("Odd Logger :" + s);
    }

    @Bean
    public Function<Flux<String>, Flux<String>> reactiveUppercase(){
        return FunctionLib.reactiveUppercase();
    }

    @Bean
    public Function<Person, String> uppercasePerson(){
        return FunctionLib.uppercasePerson();
    }
}