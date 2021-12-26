package vn.quynv.springframework;

import io.r2dbc.spi.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
public class R2dbcTest {
    final String DB_URL = "r2dbc:h2:file:////Users/quynv/Research/spring-framework/shared_folder/react_db";

    void setupR2Dbc() {
        ConnectionFactory factories = ConnectionFactories.get(DB_URL);
        log.info(factories.getMetadata().getName());
    }

    @Test
    void test_Setup() {
//       ConnectionFactoryOptions options = ConnectionFactoryOptions.builder().option(ConnectionFactoryOptions.DRIVER,"h2")
//                .option(ConnectionFactoryOptions.PROTOCOL,"file")
//                .option(ConnectionFactoryOptions.HOST,"//Users/quynv/Research/spring-framework/shared-folder/database;AUTO_SERVER=TRUE")
//               .option(ConnectionFactoryOptions.USER,"sa")
//               .option(ConnectionFactoryOptions.PASSWORD,"sa")
//               .build();


        ConnectionFactoryOptions options = ConnectionFactoryOptions.parse(DB_URL);
        System.out.println(options.toString());
//
        ConnectionFactory factory = ConnectionFactories.get(options);
        log.info(factory.getMetadata().getName());
        Mono.from(factory.create())
                .flatMapMany(connection -> connection.createStatement("select *From r2_user where id >2")
                        .execute()
                ).flatMap(result -> result.map(
                (row, rowMetadata) -> {

                    return R2User.builder().name(row.get("name", String.class))
                            .build();
                }

        ))
                .log()
                .doOnNext(System.out::println)
                .subscribe();

        log.info("GO HERE!!");
    }

    private void printMetadata(Connection connection) {
        log.info("Connection - Metadata: ");
        log.info("DB Name: {}", connection.getMetadata().getDatabaseProductName());
        log.info("DB Version: {}", connection.getMetadata().getDatabaseVersion());
    }

    @Test
    void open_Connection_And_Print_Out_Metadata() throws InterruptedException {
        open_PrintLog_And_Metadata().flatMap(connection -> {
            return connection.createStatement("select * from r2_user").execute();
        }).flatMap(result -> {
            return result.map((row, rowMetadata) -> {
                R2User user = R2User.builder()
                        .name(row.get("name", String.class))
                        .age(row.get("age", Integer.class))
                        .build();
                if (user.age > 11) throw new RuntimeException("Can process age >12");
                return user;
            });
        }).retry(1)
                .subscribe(
                        (user) -> log.info("Got: {}", user)
                        , (err) -> log.error("An error occured {}", err)
                );

    }

    @Test
    void insert_R2_User_Table() {
        open_PrintLog_And_Metadata().flatMap(connection ->
                connection.createStatement("insert into r2_user(name, age,class_name) values('Tri Kien',3,'Mau Giao')")
                        .execute()
        ).flatMap(result ->
                result.getRowsUpdated()
        ).subscribe((insertCount) -> {
            log.info("Total insert record: {}", insertCount);
        });
    }

    @Test void insert_R2_User_Table_By_Batch() {
        open_PrintLog_And_Metadata().flatMap(connection -> connection.createBatch()
                .add("insert into r2_user(name, age,class_name) values('Tri Kien',3,'Mau Giao')")
                .add("insert into r2_user(name, age,class_name) values('Tri Kien',3,'Mau Giao')")
                .add("insert into r2_user(name, age,class_name) values('Tri Kien',3,'Mau Giao')")
                .add("insert into r2_user(name, age,class_name) values('Tri Kien',3,'Mau Giao')")
                .execute()
        ).flatMap(result -> result.map((row, rowMetadata) -> {
                    log.info("GOING HER .....");
                    return row.get(0,Integer.class);
                })
        ).subscribe((insertCount) -> {
            log.info("Total insert record: {}", insertCount);
        });
    }

    @Test void  insert_with_parameters() {
        open_PrintLog_And_Metadata().flatMap(connection -> {
            Statement stmt = connection.createStatement("insert into r2_user(name, age,class_name) values(?,?,?)");
            stmt.bind(0,"Le Van Thanh");
            stmt.bind(1,20);
            stmt.bind(2,"Lop Dai Hoc");
            stmt.add();
            stmt.bind(0,"Le Van Thanh - 2");
            stmt.bind(1,21);
            stmt.bind(2,"Lop Dai Hoc - 2");
            stmt.add();
            return stmt.execute();

        }).flatMap(result -> {
            return result.getRowsUpdated();
        }).subscribe(updatedCount -> {
            log.info("Updated record {}", updatedCount);
        });
    }

    @Test void load_User_From_Files() {
        final String RESOURCE = "/Users/quynv/Research/spring-framework/shared-folder/r2_user.txt";
        open_PrintLog_And_Metadata().flatMap(connection -> {
            Statement stmt = connection.createStatement("insert into r2_user(name, age, class_name) values(?,?,?)");
            List<R2User> usersLoadedFromFile = loadFromFile(RESOURCE);
            usersLoadedFromFile.forEach(user-> {
                stmt.bind(0,user.name);
                stmt.bind(1,user.age);
                stmt.bind(2, user.class_name);
                stmt.add();
            });
            return stmt.execute();
        }).flatMap(result -> {
            return result.getRowsUpdated();
        }).subscribe(updatedCount -> {
            log.info("Updated record {}", updatedCount);
        });
    }

    @Test void select_Group_By_Command() {
        open_PrintLog_And_Metadata()
                .flatMap(this::execute_group_by_sql)
                .flatMap(result -> result.map((row, rowMetadata) -> {
                    Map<String,Long> mapResult = new HashMap<>();
//                    log.info("TOTAL as TYPE: {}",rowMetadata.getColumnMetadata("total").getJavaType().getName());
                    mapResult.put(
                            row.get("class_name",String.class),
                            row.get("total",Long.class));
                    return mapResult;
                }))
                .subscribe(rec -> log.info("Result loaded from database: {}",rec));
    }
    public Publisher<? extends Result> execute_group_by_sql(Connection conn) {
        return conn.createStatement("select class_name, count(*) as total from r2_user group by class_name order by class_name")
                .execute();
    }

    
    @Test
    void open_And_Query_R2Table() {
        open_PrintLog_And_Metadata()
                .subscribe(
                        connection -> {
                            Statement stmt = connection.createStatement("select *From r2_user where age >2");
                            stmt.execute();
                        },
                        error -> {
                            log.error("An error occured when processing data");
                        }
                );
    }

    Flux<Connection> open_PrintLog_And_Metadata() {
        return fluxConnection().log()
                .doOnComplete(() -> log.info("Completed"))
                .doOnNext(this::printMetadata);
    }
    /*Concat stream result with Void that retured from Connection.close method */
    @Test void query_and_close_connection_correctly() {
        open_PrintLog_And_Metadata()
                .flatMap(connection -> {
                    return Flux.from(connection.createStatement("select *from r2_user").execute())
                            .flatMap(result -> result.map((row, rowMetadata) -> {return row.get("name",String.class);}))
                            .concatWith(Mono.from(connection.close()).cast(String.class));
                }).subscribe(name -> log.info("Received name {}",name));
    }

    @Test void query_and_close_connection_correctly_Using_When_Clause() {

        Flux<String> fluxResult = Flux.usingWhen(
                connectionFactory().create()
                ,connection -> {
                    return Flux.from(connection.createStatement("select *from r2_user").execute())
                            .flatMap(result -> result.map((row, rowMetadata) -> {
                                return row.get("name",String.class);
                            }));

                },connection -> {
                    log.info("Init call able to close connection....");
                    return connection.close();
                }
        );

        fluxResult.subscribe(new Subscriber<String>() {
            Subscription subscription;
            @Override
            public void onSubscribe(Subscription subscription) {
                this.subscription = subscription;
                subscription.request(1);
            }

            @Override
            public void onNext(String s) {
                log.info("========> ON NEXT {} ", s);
                subscription.request(1);
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("And error: {}", throwable.getMessage());
            }

            @Override
            public void onComplete() {
                log.info("====>Fetching data from database completed!!");
            }
        });
    }

    @Test void query_and_close_connection_correctly_Using_USING_CLAUSE() {
        log.info("-----------------------------------------------------------------------");
        Flux<String> fluxResult2 = Flux.using(
                ()->Flux.from(connectionFactory().create()).blockLast()
                ,connection -> {
                    return Flux.from(connection.createStatement("select *from r2_user").execute())
                            .flatMap(result -> result.map((row, rowMetadata) -> {
                                return row.get("name",String.class);
                            }));
                },connection -> {
                    log.info("Init call able to close connection....");
                    Flux.from(connection.close()).blockLast();
                }
        );
        fluxResult2.subscribe((name)-> log.info("Received From Database {}",name));
    }

    public ConnectionFactory connectionFactory() {
        return ConnectionFactories.get(dbOptions());
    }

    public Flux<Connection> fluxConnection() {
        return Flux.from(connectionFactory().create());
    }

    public ConnectionFactoryOptions dbOptions() {
        return ConnectionFactoryOptions.builder().option(ConnectionFactoryOptions.USER, "sa")
                .option(ConnectionFactoryOptions.PASSWORD, "sa")
                .option(ConnectionFactoryOptions.DRIVER, "h2")
                .option(ConnectionFactoryOptions.PROTOCOL, "file")
                .option(ConnectionFactoryOptions.DATABASE, "/Users/quynv/Research/spring-framework/shared-folder/database;AUTO_SERVER=TRUE")
                .build();

    }

    @Builder
    @AllArgsConstructor
    @ToString
    public static class R2User {
        private String name;
        private int age;
        private String class_name;
    }

    public static void safeThread(long second) {
        try {
            Thread.sleep(second * 1000);
        } catch (Exception ex) {

        }
    }

    public List<R2User> loadFromFile(String path) {
        final List<R2User> returnList = new ArrayList<>();
        Flux.using(
                ()-> Files.lines(Paths.get(path))
                ,stringStream -> Flux.fromStream(stringStream)
                ,stringStream -> stringStream.close()
        ).map((line)-> {
            String fields[] = line.split(",");
                R2User user = new R2User(fields[0],Integer.parseInt(fields[1]),fields[2]);
                return user;
        }).subscribe(user -> returnList.add(user));
        return new ArrayList<>(returnList);
    }

    @Test void test_Load_File() {
        List<R2User> listUser = loadFromFile("/Users/quynv/Research/spring-framework/shared-folder/r2_user.txt");
        listUser.forEach(user-> {
            log.info("Loaded: {}", user);
        });
    }
}
