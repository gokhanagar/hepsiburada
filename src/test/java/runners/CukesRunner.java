package runners;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {
                "pretty",
                "io.qameta.allure.cucumber7.AllureCucumber7Jvm",
                "rerun:target/rerun.txt"
        },
        features = "src/test/resources/features",
        glue = "stepDefs",
        tags = "@Positiv"
)
public class CukesRunner {
}



