package com.example.demo;

import com.example.demo.models.Money;
import com.example.demo.models.PlantInventoryEntry;
import com.example.demo.repositories.PlantInventoryEntryRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.math.BigDecimal;


@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {

		/*ctx is used to specify or declare the value that SpringApplication.run(DemoApplication.class, args); creates

		it usually creates a value that we most time do not care about but this time we care about it*/
		ConfigurableApplicationContext ctx = SpringApplication.run(DemoApplication.class, args);

		PlantInventoryEntryRepository repository = ctx.getBean(PlantInventoryEntryRepository.class); //Bean is used to define a service or component


		PlantInventoryEntry plant = new PlantInventoryEntry();

		plant.setName("Bike");
		plant.setDescription("Nice, shinny and white");
		plant.setPrice(Money.of(new BigDecimal(100.0)));

		repository.save(plant);

		System.out.println(repository.findAll());
	}
}
