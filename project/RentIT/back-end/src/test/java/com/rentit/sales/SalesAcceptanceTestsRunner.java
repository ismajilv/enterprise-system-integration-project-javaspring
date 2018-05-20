package com.rentit.sales;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin={"pretty","html:target/cucumber"},
        features="classpath:features/sales",
        glue="com.rentit.sales")
public class SalesAcceptanceTestsRunner {
}
