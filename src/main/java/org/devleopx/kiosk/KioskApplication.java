package org.devleopx.kiosk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;


@ServletComponentScan
@SpringBootApplication
public class KioskApplication {
	public static void main(String[] args) {
		SpringApplication.run(KioskApplication.class, args);
	}

}
