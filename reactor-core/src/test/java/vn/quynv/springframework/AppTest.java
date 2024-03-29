package vn.quynv.springframework;


import static org.junit.jupiter.api.Assertions.assertTrue;

import io.r2dbc.spi.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import vn.quynv.springframework.domain.customer.Customer;
import vn.quynv.springframework.domain.customer.CustomerStatus;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Unit test for simple App.
 */
@Slf4j
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void create_Simple_Mono_And_Subscribe_On_It() {
        Mono.just("Hom").subscribe((str) -> log.info(str));
    }


    @Test
    public void create_Simple_Flux_And_Subscribe_On_It() {
        Flux.range(1, 10).map(
                number -> {
                    if (number == 1000) throw new RuntimeException("Bi loi roi");
                    return Customer.builder().name("Customer " + number)
                            .status(CustomerStatus.of(number % 2))
                            .build();
                }
        ).subscribe(customer -> {
                    log.info("Process customer {}", customer);
                }
                , error -> {
                    log.error("An error occurred when processing", error);
                }
                , () -> {
                    log.info("DONE!");
                }
        );
    }


    @Test
    public void create_Simple_Flux_And_Subscribe_On_It_And_Cancel_After_That() {
        Flux.range(1, 10).map(
                number -> {
                    if (number == 1000) throw new RuntimeException("Bi loi roi");
                    return Customer.builder().name("Customer " + number)
                            .status(CustomerStatus.of(number % 2))
                            .build();
                }
        ).subscribe(new BaseSubscriber<Customer>() {
                        @Override
                        protected void hookOnSubscribe(Subscription subscription) {
                            log.info("Start subscribe .......");
                            request(1);
                        }

                        @Override
                        protected void hookOnNext(Customer value) {
                            log.info("Processing value: {}", value);
                            if(value.getName().endsWith("6")) {
                                log.info("Cancelling ....");
                                cancel();
                            }
                            request(1);
                        }
                    }
        );

        Flux.range(1,10).doOnRequest((number)-> {
            log.info("Request number {}", number);
        }).subscribe(System.out::println);
    }

    @Test
    void create_flux_with_using_declare() throws Exception{
        List<Integer> resources = new ArrayList(Arrays.asList(1,2,3));

        Flux<Integer> flux = Flux.using(
                ()->resources
                ,(list) -> Flux.fromIterable(list)
                ,(list)-> {list.clear();
                });
        flux.subscribe(number -> log.info("Number is {}", number));
        Thread.sleep(3_000);

    }

    @Test
    void read_File_Using_Flux() {
        final String PATH = "/Users/quynv/Research/spring-framework/shared-folder/movie_2.txt";
        Path ipPath = Paths.get(PATH);
        Flux<String> fluxLine = Flux.using(
                () -> Files.lines(ipPath)
                ,(streamLine) -> Flux.fromStream(streamLine)
                ,(streamLine) -> streamLine.close()
        ).subscribeOn(Schedulers.newParallel("New-Thread",3));
        fluxLine.log().subscribe(
                (line) -> log.info("Thread {} : {}", Thread.currentThread().getName(),line)
                ,(error)-> log.error("Thread {} An error occured {}", Thread.currentThread().getName(),error)
                ,() -> log.info("Compeleted")
                ,(subscription) -> subscription.request(10)
        );

    }

    @Test
    void read_File_Using_Flux_Limit_Rate() {
        final String PATH = "/Users/quynv/Research/spring-framework/shared-folder/movie_2.txt";
        Path ipPath = Paths.get(PATH);
        Flux<String> fluxLine = Flux.using(
                () -> Files.lines(ipPath)
                ,(streamLine) -> Flux.fromStream(streamLine)
                ,(streamLine) -> streamLine.close()
        ).subscribeOn(Schedulers.newParallel("New-Thread",3));
        fluxLine.log().limitRate(3)
                .subscribe(
                (line) -> log.info("Thread {} : {}", Thread.currentThread().getName(),line)
                ,(error)-> log.error("Thread {} An error occurred {}", Thread.currentThread().getName(),error)
                ,() -> log.info("Completed")

        );

    }

    @Test
    void subscribe_On_Flux_With_Retry() {
        Flux.range(1,10)
                .doOnNext((i)-> log.info("Emitted {}", i))
                .map(i -> {
                    if(i==5) throw new RuntimeException("Can not process i >5");
                    return i;
                })
                .log()
                .retry(2) /*So lan retry, xu ly lai tu dau ...*/
                .subscribe(number->{
                            log.info("Processed {}", number);
                        }
                        ,(error)->{
                            log.error("An error occured {}", error);
                        });
    }

    @Test void create_Flux_And_Test_WINDOW_clause() throws InterruptedException {
        Flux.range(1,10).window(2)
                .doOnSubscribe(sub -> {
                    log.info("Split to batch 2 items");
                })
                .doOnComplete(()-> log.info("Completed All Batch"))
                .publishOn(Schedulers.newParallel("new_thread",3))
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe(fluxItem -> {
                    fluxItem
                            .doOnSubscribe(subscription -> {
                                log.info(" On Subscribe ----");
                            })
                     .doOnComplete(()->log.info("---------Completed!!------"))
                    .subscribe((number)->log.info("Processing ---> {}", number));
        });
        Thread.sleep(2_000);
    }

    @Test void using_Metrics() throws InterruptedException {
        Schedulers.enableMetrics();
        Flux.range(1,10)
                .name("integer-stream")
                .metrics()
                .doOnNext(event-> log.info("Received event: {}", event))
                .delayUntil(this::processEvent)
                .retry()
                .subscribe();

        Thread.sleep(20_000);
    }

    public Publisher<Integer>  processEvent(int number) {
        log.info("Processing event: {}", number);
        try {
            Thread.sleep(1_000);
        }catch(Exception ex){

        }
        log.info("Processed event: {}", number);

        return Flux.fromIterable(Arrays.asList(number));
    }

    @Test
    void create_Flux_Using_Create_Method() throws InterruptedException {
        StringCreator stringCreator = new StringCreator();
        stringCreator.createStringFlux().subscribe(System.out::println);
        new Thread(()->{
            for(int i=0; i<10; i++) {
                stringCreator.getConsumer().accept("Nguyen " + System.currentTimeMillis());
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
        Thread.sleep(12000);
    }

    class StringCreator {
        Consumer<String> consumer;
        public Consumer<String> getConsumer(){
            return this.consumer;
        }
        public Flux<String> createStringFlux() {
            return Flux.create(sink -> {
                this.consumer = item ->sink.next(item);
            });
        }
    }

    @Test
    void checkFormat() {
        Integer aNumber=new Integer(10);
        System.out.println(aNumber/3.0);
    }

}
