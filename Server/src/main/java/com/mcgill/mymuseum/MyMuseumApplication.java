package com.mcgill.mymuseum;

import com.mcgill.mymuseum.service.AccountService;
import com.mcgill.mymuseum.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class MyMuseumApplication {

    @PostConstruct
    void start() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) {
        SpringApplication.run(MyMuseumApplication.class, args);
    }

}


@Component
class CommandLineAppStartupRunner implements CommandLineRunner {
    @Autowired
    RoomService roomService;
    @Autowired
    AccountService accountService;

    @Override
    public void run(String...args) throws Exception {
        System.out.println("Hello");
        roomService.setupRooms();
        accountService.createPresident();
    }
}