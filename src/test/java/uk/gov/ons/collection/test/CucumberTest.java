package uk.gov.ons.collection.test;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import uk.gov.ons.collection.PersistenceLayer.PersistenceLayerApp;

@ActiveProfiles("mocking")
@RunWith(Cucumber.class)
@CucumberOptions(format = "pretty", features = "src/test/resources/")
public class CucumberTest {

}