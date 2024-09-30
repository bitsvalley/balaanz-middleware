package com.bitsvalley.micro;

//import com.bitsvalley.micro.services.EmailSenderService;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BVMicroApplication {


	//comment
	public static void main(String[] args) {

		SpringApplication.run(BVMicroApplication.class, args);

	}



}
