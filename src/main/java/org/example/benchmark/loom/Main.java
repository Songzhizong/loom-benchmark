package org.example.benchmark.loom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import org.example.benchmark.loom.data.AlertRule;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * @author 宋志宗 on 2023/9/21
 */
public class Main {
    private static final ExecutorService EXECUTOR = Executors.newVirtualThreadPerTaskExecutor();
    private static final ObjectMapper MAPPER = new ObjectMapper();


    public static void main(String[] args) {
        long currentTimeMillis = System.currentTimeMillis();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        SimpleModule javaTimeModule = new JavaTimeModule()
                .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter))
                .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter))
                .addSerializer(LocalDate.class, new LocalDateSerializer(dateFormatter))
                .addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormatter))
                .addSerializer(LocalTime.class, new LocalTimeSerializer(timeFormatter))
                .addDeserializer(LocalTime.class, new LocalTimeDeserializer(timeFormatter));
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(javaTimeModule)
                .findAndRegisterModules();

        Vertx vertx = Vertx.vertx();
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        registerRoute(router);
        HttpServer httpServer = vertx.createHttpServer();
        httpServer.requestHandler(router).listen(7070, "0.0.0.0").onComplete(r -> {
            long l = System.currentTimeMillis() - currentTimeMillis;
            System.out.println("Startup success, cost: " + l + "ms");
        });
        Runtime.getRuntime().addShutdownHook(Thread.ofVirtual().unstarted(() -> {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            httpServer.close().onComplete(r -> countDownLatch.countDown());
            try {
                countDownLatch.await();
                System.out.println("Shutdown success");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }));
    }

    private static void registerRoute(@Nonnull Router router) {
        router.post("/benchmark/:count").handler(Main::benchmark);
    }

    private static void benchmark(@Nonnull RoutingContext ctx) {
        EXECUTOR.execute(() -> {
            String json;
            try {
                String body = ctx.body().asString();
                AlertRule alertRule = MAPPER.readValue(body, AlertRule.class);
                String count = ctx.pathParam("count");
                int c = Integer.parseInt(count);
                List<Callable<Boolean>> callables = IntStream.range(0, c)
                        .mapToObj(i -> (Callable<Boolean>) () -> {
                            int nextInt = ThreadLocalRandom.current().nextInt(50, 100);
                            TimeUnit.MILLISECONDS.sleep(nextInt);
                            return true;
                        }).toList();
                EXECUTOR.invokeAll(callables).forEach(f -> {
                    try {
                        f.get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                });
                json = MAPPER.writeValueAsString(alertRule);
            } catch (JsonProcessingException | InterruptedException e) {
                e.printStackTrace();
                json = "{}";
            }
            ctx.response().putHeader("Content-Type", "application/json;charset=utf-8").end(json);
        });
    }

}
