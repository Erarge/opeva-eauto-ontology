package com.erarge.opevaontology.service.impl;

public class QueryBuilderDemo2 {

    public static String buildVoltageCurrentPowerQuery() {
        return "PREFIX :     <https://cloud.erarge.com.tr/ontologies/eauto#>\n" +
               "PREFIX sosa: <http://www.w3.org/ns/sosa/>\n" +
               "PREFIX xsd:  <http://www.w3.org/2001/XMLSchema#>\n\n" +
               "SELECT\n" +
               "  ?time\n" +
               "  ?voltage\n" +
               "  ?current\n" +
               "  (?voltage * ?current AS ?power_W)\n" +
               "WHERE {\n" +
               "  GRAPH <https://cloud.erarge.com.tr/ontologies/eauto> {\n" +
               "    ?obsV a sosa:Observation ;\n" +
               "          sosa:observedProperty :module_voltage ;\n" +
               "          sosa:hasFeatureOfInterest ?interest ;\n" +
               "          sosa:hasSimpleResult ?vRaw ;\n" +
               "          sosa:resultTime ?time ;\n" +
               "          sosa:madeBySensor ?sensor .\n\n" +
               "    ?obsA a sosa:Observation ;\n" +
               "          sosa:observedProperty :cell_current ;\n" +
               "          sosa:hasFeatureOfInterest :Battery_1 ;\n" +
               "          sosa:hasSimpleResult ?currRaw ;\n" +
               "          sosa:resultTime ?time ;\n" +
               "          sosa:madeBySensor ?sensor .\n\n" +
               "    BIND(xsd:double(?vRaw) AS ?voltage)\n" +
               "    BIND(REPLACE(STR(?currRaw), \" \", \"\") AS ?currNoSpace)\n" +
               "    FILTER(CONTAINS(?currNoSpace, \"/\"))\n" +
               "    BIND(STRBEFORE(?currNoSpace, \"/\") AS ?leftPart)\n" +
               "    BIND(xsd:double(?leftPart) AS ?current)\n" +
               "  }\n" +
               "}\n" +
               "ORDER BY ?time";
    }
}