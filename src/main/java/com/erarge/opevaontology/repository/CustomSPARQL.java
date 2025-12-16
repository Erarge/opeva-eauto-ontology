package com.erarge.opevaontology.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

import com.erarge.opevaontology.connection.VirtuosoConnection;

import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

public class CustomSPARQL {
    
    private static VirtGraph graph = new VirtGraph("https://cloud.erarge.com.tr/ontologies/eauto",
			VirtuosoConnection.getHost(),
			VirtuosoConnection.getUsername(),
			VirtuosoConnection.getPassword()
	);

    public static Object sparqlQueryExecution(String query) {
		List<Map<String, String>> queryResponse = new ArrayList<Map<String, String>>();
		String sparql = "PREFIX rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n" + 
				"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\r\n" + 
				"PREFIX eauto:<https://cloud.erarge.com.tr/ontologies/eauto#>\r\n"
				+ "PREFIX sosa:<http://www.w3.org/ns/sosa/>\r\n"
				+ "PREFIX ssn:<http://www.w3.org/ns/ssn/>\r\n"
				+ "PREFIX qudt-1-1: <http://qudt.org/1.1/schema/qudt#>\r\n"
				+ "PREFIX qudt-unit-1-1: <http://qudt.org/1.1/vocab/unit#>\r\n" +
				query;
		
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(sparql, graph);
		ResultSet results = vqe.execSelect();
		
		while(results.hasNext()) {
			QuerySolution result = results.nextSolution();
			Map<String, String> entry = new HashMap<String, String>();
			for(String var : results.getResultVars()) {
				if(!var.equals("graph"))
					entry.put(var, result.get(var).toString());
			}
			queryResponse.add(entry);
		}
		
		return queryResponse;
	}
}
