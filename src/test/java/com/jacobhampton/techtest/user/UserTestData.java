package com.jacobhampton.techtest.user;

import com.jacobhampton.techtest.user.dto.CreateUserRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import tools.jackson.databind.ObjectMapper;

@Configuration
public class UserTestData {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("classpath:user/create-user-0.json")
    private Resource createUserRequestJson;

    @Bean
    public CreateUserRequestDto createUserRequestDto() throws Exception {
        return objectMapper.readValue(createUserRequestJson.getInputStream(), CreateUserRequestDto.class);
    }

}
