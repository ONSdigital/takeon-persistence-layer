package uk.gov.ons.collection.PersistenceLayer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@RibbonClient(name="persistence-layer")
public class PersistenceLayerApp {
    private static final Logger logger = LogManager.getLogger(PersistenceLayerApp.class);
    public static void main(String[] args) {
        {
            logger.info("Before Creating Persistence Layer App");
            SpringApplication.run(uk.gov.ons.collection.PersistenceLayer.PersistenceLayerApp.class, args);
            logger.info("After Creating Persistence Layer App");
        }
    }

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
