package com.erarge.opevaontology.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erarge.opevaontology.dto.FilterOptionsResponse;
import com.erarge.opevaontology.dto.FilteredQueryRequest;
import com.erarge.opevaontology.dto.demo1.Demo1BalancingResponseDTO;
import com.erarge.opevaontology.dto.TimeIntervalQueryRequest;
import com.erarge.opevaontology.dto.demo2.Demo2PowerResponseDTO;
import com.erarge.opevaontology.dto.demo3.Demo3DatasetResponse;
import com.erarge.opevaontology.dto.demo3.Demo3FilterOptionsResponse;
import com.erarge.opevaontology.dto.demo5.KpisResponse;
import com.erarge.opevaontology.dto.demo5.RouteSizeDistributionDTO;
import com.erarge.opevaontology.dto.demo5.RouteTimeWindowDTO;
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

	@GetMapping("/api/demo1/balancing-series")
	public ResponseEntity<Demo1BalancingResponseDTO> getDemo1BalancingSeries() {
		return ResponseEntity.ok(service.getDemo1BalancingSeries());
	}

	@GetMapping("/api/demo2/power-series")
	public ResponseEntity<Demo2PowerResponseDTO> getDemo2PowerSeries() {
		return ResponseEntity.ok(service.getDemo2PowerSeries());
	}

	@GetMapping("/api/demo3/filter-options")
	public ResponseEntity<Demo3FilterOptionsResponse> getDemo3FilterOptions(
			@RequestParam(required = false) String battery) {
		return ResponseEntity.ok(service.getDemo3FilterOptions(battery));
	}

	@GetMapping("/api/demo3/eis")
	public ResponseEntity<Demo3DatasetResponse> getDemo3Dataset(
			@RequestParam(required = false) String battery,
			@RequestParam(required = false) Double soc) {
		return ResponseEntity.ok(service.getDemo3Dataset(battery, soc));
	}

	@GetMapping("/api/demo5/kpis")
	public ResponseEntity<KpisResponse> getKpis() {
		return ResponseEntity.ok(service.getKpis());
	}

	@GetMapping("/api/demo5/route-mix/size-distribution")
	public ResponseEntity<List<RouteSizeDistributionDTO>> getRouteMixSizeDistribution() {
		return ResponseEntity.ok(service.getRouteMixSizeDistribution());
	}

	@GetMapping("/api/demo5/route-mix/time-window")
	public ResponseEntity<List<RouteTimeWindowDTO>> getRouteMixTimeWindow() {
		return ResponseEntity.ok(service.getRouteMixTimeWindow());
	}
}
