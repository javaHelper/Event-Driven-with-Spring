package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.function.context.FunctionCatalog;
import org.springframework.cloud.function.context.config.RoutingFunction;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.io.IOException;
import java.util.function.Function;

@SpringBootApplication
public class FunctionDemo {

    public static void main(String[] args) throws IOException, InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(FunctionDemo.class);
        FunctionCatalog catalog = context.getBean(FunctionCatalog.class);

        // 1.
        Function<String, String> uppercaseFunction = catalog.lookup("uppercase");
        System.out.println(uppercaseFunction.apply("spring"));

        // 2. We can also use below
        Function<String, String> function = context.getBean("uppercase1", Function.class);
        System.out.println(function.apply("hello"));

        // 3.
        Function<Flux<String>, Flux<String>> function3 = catalog.lookup("uppercase");
        System.out.println(function3.apply(Flux.just("apple")).blockFirst());
        System.out.println("----------");

        Function<Flux<String>, Flux<String>> function4 = catalog.lookup("reactiveUppercase|echo");
        System.out.println(function4.apply(Flux.just("achalpur")).blockFirst());

        System.out.println("----------");

        Function<String, String> uppercasePerson = catalog.lookup("echo|uppercasePerson");
        Person john = Person.builder().id(1).name("John").build();
        System.out.println(uppercasePerson.apply(new ObjectMapper().writeValueAsString(john)));
        System.out.println("----------------");


        Function<Message<String>, String> fn = catalog.lookup(RoutingFunction.FUNCTION_NAME);
        Message<String> message = MessageBuilder.withPayload("hi")
                .setHeader("spring.cloud.function.definition", "uppercase1")
                .build();
        System.out.println(fn.apply(message));

        System.out.println("----------------------");
        Message<String> msg = MessageBuilder.withPayload("victoria")
                .setHeader("spring.cloud.function.routing-expression", "'uppercase1'")
                .build();
        System.out.println(fn.apply(msg));


        System.out.println("----------");
        Function<Flux<Integer>, Tuple2<Flux<String>, Flux<String>>> router = catalog.lookup("router1");
        Tuple2<Flux<String>, Flux<String>> streams = router.apply(FunctionLib.generateFlux());

        streams.getT1().subscribe(System.out::println);
        streams.getT2().subscribe(System.out::println);

        Thread.sleep(1000);

        System.out.println("----------");
        Function<Flux<Integer>, Flux<Integer>> aggregator = catalog.lookup("aggregator");
        System.out.println(aggregator.apply(FunctionLib.generateFlux()).subscribe());
        Thread.sleep(1000);
    }
}