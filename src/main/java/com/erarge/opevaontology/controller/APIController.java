package com.erarge.opevaontology.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erarge.opevaontology.service.IService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "")
public class APIController {
    @Autowired
    IService service;

    @PostMapping(value = "/sparql")
    public Object sparql(@RequestBody final String query) {
		return service.sparql(query);
	}
}
