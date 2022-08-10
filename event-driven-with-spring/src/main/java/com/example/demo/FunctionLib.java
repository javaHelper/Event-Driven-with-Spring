package com.example.demo;

import org.springframework.messaging.Message;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class FunctionLib {

    public static Function<Message<String>, String> uppercase() {
        return v -> {
            System.out.println(">>> Uppercasing :" + v);
            return v.getPayload().toUpperCase();
        };
    }

    public static Function<Flux<String>, Flux<String>> reactiveUppercase() {
        System.out.println("calling reactive uppercase");
        return stringFlux -> stringFlux.map(String::toUpperCase);
    }

    public static Function<Flux<Integer>, Flux<Integer>> multiplier() {
        return integerFlux -> integerFlux.map(v -> v * v);
    }

    public static Function<Flux<Integer>, Flux<Integer>> aggregator() {
        return integerFlux -> integerFlux.window(Duration.ofMillis(5000))
                .flatMap(window -> window.filter(v -> v % 2 == 0).reduce(Integer::sum))
                .doOnNext(System.out::println);
    }

    public static Function<Flux<Integer>, Tuple2<Flux<String>, Flux<String>>> router() {
        return integerFlux -> {
            Flux<Integer> connectedFlux = integerFlux.publish().autoConnect(2);
            UnicastProcessor even = UnicastProcessor.create();
            UnicastProcessor odd = UnicastProcessor.create();

            Flux<Integer> evenFlux = connectedFlux.filter(number -> number % 2 == 0)
                    .doOnNext(number -> even.onNext("EVEN :" + number));

            Flux<Integer> oddFlux = connectedFlux.filter(number -> number % 2 != 0)
                    .doOnNext(number -> even.onNext("ODD :" + number));

            return Tuples.of(
                    Flux.from(even).doOnSubscribe(x -> evenFlux.subscribe()),
                    Flux.from(odd).doOnSubscribe(x -> oddFlux.subscribe())
            );
        };
    }

    public static Flux<Integer> generateFlux() {
        Flux<Integer> ints = Flux.fromStream(Stream.generate(new Supplier<Integer>() {
            AtomicInteger i = new AtomicInteger();

            @Override
            public Integer get() {
                try {
                    Thread.sleep(200);
                    return i.incrementAndGet();
                } catch (InterruptedException e) {
                    throw new RuntimeException("");
                }
            }
        })).subscribeOn(Schedulers.boundedElastic()).share();
        return ints;
    }

    public static Function<Person, String> uppercasePerson() {
        System.out.println("===> creating 'uppercasePerson' FUNCTION bean");
        return person -> Person.builder()
                .id(person.getId())
                .name(person.getName().toUpperCase())
                .build()
                .toString();
    }
}