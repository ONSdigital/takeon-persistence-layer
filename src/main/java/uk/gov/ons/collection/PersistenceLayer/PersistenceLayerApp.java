package uk.gov.ons.collection.PersistenceLayer;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@Log4j2
@SpringBootApplication
@EnableDiscoveryClient
@RibbonClient(name="persistence-layer")
public class PersistenceLayerApp {
    public static void main(String[] args) {
        {
            log.info("Before Creating Persistence Layer App");
            SpringApplication.run(uk.gov.ons.collection.PersistenceLayer.PersistenceLayerApp.class, args);
            log.info("After Creating Persistence Layer App");
        }
    }

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
