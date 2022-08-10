package com.example.demo;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.util.function.Function;

public class DemoApp {
    public static void main(String[] args) throws InterruptedException {
        Function<Message<String>, String> uppercase = FunctionLib.uppercase();
        System.out.println(uppercase.apply(new GenericMessage<String>("hello")));
        System.out.println("-------------");

        Function<Flux<Integer>, Tuple2<Flux<String>, Flux<String>>> router = FunctionLib.router();
        Tuple2<Flux<String>, Flux<String>> streams = router.apply(FunctionLib.generateFlux());

        streams.getT1().subscribe(System.out::println);
        streams.getT2().subscribe(System.out::println);

        Thread.sleep(3000);
    }
}