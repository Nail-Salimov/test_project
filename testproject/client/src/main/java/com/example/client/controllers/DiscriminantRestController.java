package com.example.client.controllers;

import com.example.client.wsdl.DiscriminantClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiscriminantRestController {

    private DiscriminantClient client;

    @Autowired
    public DiscriminantRestController(DiscriminantClient client) {
        this.client = client;
    }


    @RequestMapping(method = RequestMethod.GET, value = "/api/calc/")
    public ResponseEntity calculateDiscriminant(@RequestParam double a, @RequestParam double b, @RequestParam double c){

        return ResponseEntity.ok().body(client.getDiscriminant(a, b, c));
    }
}
