package com.erarge.opevaontology.service.impl;

public class QueryBuilderDemo2 {

    public static String buildVoltageCurrentEfficiencyHeatmapQuery() {
        return "PREFIX :     <https://cloud.erarge.com.tr/ontologies/eauto#>\n" +
               "PREFIX sosa: <http://www.w3.org/ns/sosa/>\n" +
               "PREFIX bif:  <bif:>\n" +
               "PREFIX xsd:  <http://www.w3.org/2001/XMLSchema#>\n\n" +
               "SELECT\n" +
               "  ?battery\n" +
               "  (?Vbin * ?dV                 AS ?V_bin_start)\n" +
               "  ((?Vbin + 1) * ?dV           AS ?V_bin_end)\n" +
               "  (?Abin * ?dA                 AS ?A_bin_start)\n" +
               "  ((?Abin + 1) * ?dA           AS ?A_bin_end)\n" +
               "  ((?Vbin + 0.5) * ?dV         AS ?V_bin_center)\n" +
               "  ((?Abin + 0.5) * ?dA         AS ?A_bin_center)\n" +
               "  (AVG(?eff)                   AS ?avg_efficiency)\n" +
               "  (COUNT(*)                    AS ?n_samples)\n" +
               "WHERE {\n" +
               "  GRAPH <https://cloud.erarge.com.tr/ontologies/eauto> {\n" +
               "    BIND(5.0  AS ?dV)\n" +
               "    BIND(1.0  AS ?dA)\n\n" +
               "    ?obsE a sosa:Observation ;\n" +
               "          sosa:hasFeatureOfInterest ?b ;\n" +
               "          sosa:observedProperty :op_efficiency ;\n" +
               "          sosa:hasSimpleResult ?eRaw ;\n" +
               "          sosa:resultTime ?time .\n\n" +
               "    ?obsV a sosa:Observation ;\n" +
               "          sosa:hasFeatureOfInterest ?b ;\n" +
               "          sosa:observedProperty :op_v_pri_v ;\n" +
               "          sosa:hasSimpleResult ?vRaw ;\n" +
               "          sosa:resultTime ?time .\n\n" +
               "    ?obsA a sosa:Observation ;\n" +
               "          sosa:hasFeatureOfInterest ?b ;\n" +
               "          sosa:observedProperty :op_a_pri_a ;\n" +
               "          sosa:hasSimpleResult ?aRaw ;\n" +
               "          sosa:resultTime ?time .\n\n" +
               "    FILTER(isNumeric(?eRaw) && isNumeric(?vRaw) && isNumeric(?aRaw))\n" +
               "    BIND(xsd:double(?eRaw) AS ?eff)\n" +
               "    BIND(xsd:double(?vRaw) AS ?vPri)\n" +
               "    BIND(xsd:double(?aRaw) AS ?aPri)\n\n" +
               "    FILTER(?vPri > 0.0)\n\n" +
               "    BIND(bif:floor(?vPri / ?dV) AS ?Vbin)\n" +
               "    BIND(bif:floor(?aPri / ?dA) AS ?Abin)\n\n" +
               "    BIND(REPLACE(STR(?b), \"^.*(#|/)\", \"\") AS ?battery)\n" +
               "  }\n" +
               "}\n" +
               "GROUP BY ?battery ?Vbin ?Abin ?dV ?dA\n" +
               "ORDER BY ?battery ?Vbin ?Abin";
    }
}
