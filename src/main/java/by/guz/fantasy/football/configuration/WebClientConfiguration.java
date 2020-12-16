package by.guz.fantasy.football.configuration;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

@Configuration
public class WebClientConfiguration {

    private static final String BASE_URL = "https://v3.football.api-sports.io/";
    private static final String X_RAPIDAPI_HOST = "v3.football.api-sports.io";
    private static final String X_RAPIDAPI_KEY = "72d6ec44cdb773211f10d4c3e104fe6a";

    @Bean
    public WebClient defaultWebClient() {

        var tcpClient = TcpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 2_000)
                .doOnConnected(connection ->
                        connection.addHandlerLast(new ReadTimeoutHandler(2))
                                .addHandlerLast(new WriteTimeoutHandler(2)));

        return WebClient.builder()
                .baseUrl(BASE_URL)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .defaultHeader("x-rapidapi-host", X_RAPIDAPI_HOST)
                .defaultHeader("x-rapidapi-key", X_RAPIDAPI_KEY)
                .build();
    }
}
