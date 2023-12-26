package runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "D:\\selProject\\data_workspace\\browser_automation\\src\\test\\java\\feature\\Web_automation.feature" , // Specify the path to your feature file
    glue = {"seleniumgluecode"},         // Specify the package containing your step definitions
    plugin = {"pretty", "html:target/cucumber-reports.html"} // Specify the output format and location
//    tags={"@web_automation"}
		)
public class preRunner {

}

