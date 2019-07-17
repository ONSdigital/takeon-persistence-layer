package uk.gov.ons.collection.PersistenceLayer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@SpringBootApplication
@RibbonClient(name="persistence-layer")
public class PersistenceLayerApp {
    public static void main(String[] args) {
        {
            SpringApplication.run(uk.gov.ons.collection.PersistenceLayer.PersistenceLayerApp.class, args);
        }
    }

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
