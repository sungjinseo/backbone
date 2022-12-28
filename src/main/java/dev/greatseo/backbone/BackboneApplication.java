package dev.greatseo.backbone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class BackboneApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(BackboneApplication.class);
        app.addListeners(new ApplicationPidFileWriter());   // pid 를 작성하는 역할을 하는 클래스 선언
        app.run(args);
    }

}
