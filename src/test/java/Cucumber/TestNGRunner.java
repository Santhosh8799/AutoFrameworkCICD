package Cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features="src/test/java/Cucumber",glue="Cucumber.StepDefinition",
monochrome=true,tags="@Error",plugin= {"html:CucumberReport/Report.html"})
public class TestNGRunner extends AbstractTestNGCucumberTests{

}
