package com.erarge.opevaontology.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erarge.opevaontology.dto.FilterOptionsResponse;
import com.erarge.opevaontology.dto.FilteredQueryRequest;
import com.erarge.opevaontology.dto.TimeIntervalQueryRequest;
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

	@PostMapping(value = "/sparql-demo5")
	public Object sparqlDemo5(@RequestBody final String query) {
		return service.sparqlDemo5(query);
	}
	
	@GetMapping(value = "/api/filter-options")
	public FilterOptionsResponse getFilterOptions() {
		return service.getFilterOptions();
	}
	
	@PostMapping(value = "/api/query/time-interval")
	public List<Map<String, String>> queryByTimeInterval(@RequestBody final TimeIntervalQueryRequest request) {
		return service.queryByTimeInterval(request);
	}
	
	@PostMapping(value = "/api/query/filtered")
	public List<Map<String, String>> queryFiltered(@RequestBody final FilteredQueryRequest request) {
		return service.queryFiltered(request);
	}
}
