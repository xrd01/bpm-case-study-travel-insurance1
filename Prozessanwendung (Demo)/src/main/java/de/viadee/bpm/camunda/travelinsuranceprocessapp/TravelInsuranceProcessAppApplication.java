package de.viadee.bpm.camunda.travelinsuranceprocessapp;

import io.camunda.zeebe.spring.client.annotation.Deployment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "de.viadee.bpm.camunda.travelinsuranceprocessapp.*")
@Deployment(resources = { "classpath*:/process/**/*.bpmn", "classpath*:/process/**/*.dmn" })
public class TravelInsuranceProcessAppApplication {
	public static final String TRAVEL_INSURANCE_PROCESS_ID = "TravelInsuranceProcess";

	public static void main(String[] args) {
		SpringApplication.run(TravelInsuranceProcessAppApplication.class, args);
	}

}
