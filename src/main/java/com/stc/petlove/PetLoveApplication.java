package com.stc.petlove;

import com.stc.petlove.entities.TaiKhoan;
import com.stc.petlove.repositories.TaiKhoanRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Collections;

@Slf4j
@EnableAsync
@SpringBootApplication
public class PetLoveApplication implements CommandLineRunner {
    @Autowired
    TaiKhoanRepository taiKhoanRepository;

    public static void main(String[] args) {
        SpringApplication.run(PetLoveApplication.class, args);
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("🚀 Server ready at http://localhost:8080");
        System.out.println("🚀 Api doc ready at http://localhost:8080/swagger-ui/index.html");
    }


    @Override
    public void run(String... args) throws Exception {
        if (taiKhoanRepository.count() == 0) {
            TaiKhoan user = new TaiKhoan("20161332", "admin@hcmute.edu.vn", "123456", "12312312", Collections.singletonList("ROLE_ADMIN"));
            taiKhoanRepository.save(user);
            user = new TaiKhoan("20161332", "user@hcmute.edu.vn", "123456", "12312312", Collections.singletonList("ROLE_USER"));
            taiKhoanRepository.save(user);
        }
    }
}
