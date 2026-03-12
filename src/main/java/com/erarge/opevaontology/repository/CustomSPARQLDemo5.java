package com.erarge.opevaontology.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.RDFNode;

import com.erarge.opevaontology.connection.VirtuosoConnection;

import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

public class CustomSPARQLDemo5 {
    
    private static VirtGraph graph = new VirtGraph("https://cloud.erarge.com.tr/ontologies/opeva_demo5",
			VirtuosoConnection.getHost(),
			VirtuosoConnection.getUsername(),
			VirtuosoConnection.getPassword()
	);

    public static Object sparqlQueryExecution(String query) {
		List<Map<String, String>> queryResponse = new ArrayList<Map<String, String>>();
		String sparql = "PREFIX rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n" + 
				"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\r\n" + 
				"PREFIX :<https://cloud.erarge.com.tr/ontologies/opeva_demo5/>\r\n" +
				"PREFIX eauto:<https://cloud.erarge.com.tr/ontologies/eauto#>\r\n"
				+ "PREFIX sosa:<http://www.w3.org/ns/sosa/>\r\n"
				+ "PREFIX rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n"
				+ "PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#>\r\n"
				+ "PREFIX ssn:<http://www.w3.org/ns/ssn/>\r\n"
				+ "PREFIX qudt-1-1: <http://qudt.org/1.1/schema/qudt#>\r\n"
				+ "PREFIX qudt-unit-1-1: <http://qudt.org/1.1/vocab/unit#>\r\n" +
				query;
		System.out.println(sparql);
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(sparql, graph);
		
		ResultSet results = vqe.execSelect();
		
		while(results.hasNext()) {
			QuerySolution result = results.nextSolution();
			Map<String, String> entry = new HashMap<String, String>();
			for (String var : results.getResultVars()) {
				if ("graph".equals(var)) continue;
				if (!result.contains(var) || result.get(var) == null) continue;
		
				RDFNode node = result.get(var);
		
				if (node.isLiteral()) {
					Literal lit = node.asLiteral();
		
					// Plain lexical value (no ^^datatype, no @lang)
					// e.g. "20.0" instead of "20.0^^xsd:double"
					entry.put(var, lit.getLexicalForm());
		
					// Alternative: strongly typed value -> String
					// Object v = lit.getValue(); entry.put(var, String.valueOf(v));
		
				} else if (node.isURIResource()) {
					entry.put(var, node.asResource().getURI());
				} else if (node.isResource()) {
					// Blank nodes, etc.
					entry.put(var, node.asResource().toString());
				} else {
					entry.put(var, node.toString());
				}
			}
			queryResponse.add(entry);
		}
		
		return queryResponse;
	}
}
