package com.stc.petlove;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.core.context.SecurityContext;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@EnableAsync
@SpringBootApplication
public class PetLoveApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetLoveApplication.class, args);
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("🚀 Server ready at http://localhost:8080");
        System.out.println("🚀 Api doc ready at http://localhost:8080/swagger-ui/index.html");
    }


}
