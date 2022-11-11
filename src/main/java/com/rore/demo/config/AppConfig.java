package com.rore.demo.config;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        org.modelmapper.config.Configuration configuration = modelMapper.getConfiguration();
        configuration.setAmbiguityIgnored(true);
        configuration.setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    @Bean
    public Gson gson(){
        return new Gson();
    }

}
