package com.template;

import com.template.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class ThymleafTemplateApplication {

    @Autowired
    ResourceLoader resourceLoader;
    public static void main(String[] args) {
        SpringApplication.run(ThymleafTemplateApplication.class, args);
    }

    @Bean
    public ApplicationRunner run() {
        return args -> {
            Resource resource = resourceLoader.getResource("classpath:test.html");
            String stringToHtml = Files.readString(Path.of(resource.getURI()));
            TemplateEngine engine = new SpringTemplateEngine();

            StringTemplateResolver templateResolver = new StringTemplateResolver();
            templateResolver.setTemplateMode(TemplateMode.HTML);
            engine.setTemplateResolver(templateResolver);

            Context context = new Context();
            context.setVariable("members", getMembers());
            String html = engine.process(stringToHtml, context);

            System.out.println(html);
        };
    }

    private List<Member> getMembers() {
        return Arrays.asList(
            new Member(1L, "kevin", 20, "london"),
            new Member(2L, "lee", 30, "seoul"),
            new Member(3L, "mark", 40, "la"),
            new Member(4L, "eddy", 52, "newyork")
        );
    }
}
