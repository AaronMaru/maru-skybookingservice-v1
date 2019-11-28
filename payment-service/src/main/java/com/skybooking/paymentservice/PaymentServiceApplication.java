package com.skybooking.paymentservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class PaymentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }

}

@RestController
class FlightPaymentController {

    @Autowired
    Environment environment;

    @GetMapping("/flights")
    public Flight getFlight() {

        Flight flight = new Flight();
        flight.setMessage("Get Flight Available (Payment) " + environment.getProperty("local.server.port"));

        return flight;
    }
}

class Flight {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}