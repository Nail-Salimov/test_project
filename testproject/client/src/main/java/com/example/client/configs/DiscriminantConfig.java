package com.example.client.configs;

import com.example.client.wsdl.DiscriminantClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class DiscriminantConfig {

    @Bean
    public Jaxb2Marshaller marshaller(){
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.example.client.wsdl");
        return marshaller;
    }

    @Bean
    public DiscriminantClient discriminantClient(Jaxb2Marshaller marshaller){
        DiscriminantClient client = new DiscriminantClient();
        client.setDefaultUri("http://localhost:8080/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
