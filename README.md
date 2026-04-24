# OPEVA Semantic Integration and Data Management Tool

A Spring Boot REST API and web-based visualization platform for querying and exploring the OPEVA Electrified Automotive (E-Auto) ontology via SPARQL. The platform integrates experimental results from across the OPEVA demonstrations into a shared knowledge graph, enabling semantic interoperability and KPI-driven data exploration.

This project is part of the [OPEVA (OPtimization of Electric Vehicle Autonomy)](https://opeva.eu/) European project, funded within the Key Digital Technologies Joint Undertaking (KDT JU) from the European Union's Horizon Europe Programme.

## Ontology

The OPEVA E-Auto ontology is publicly accessible at:
**[https://cloud.erarge.com.tr/ontologies/eauto](https://cloud.erarge.com.tr/ontologies/eauto)**

The ontology is grounded in [W3C SOSA/SSN](https://www.w3.org/TR/vocab-ssn/) for sensor observations and [QUDT](https://qudt.org/) for units of measurement. A separate routing ontology for Demo 5 is integrated at `https://cloud.erarge.com.tr/ontologies/opeva_demo5`.

## Demonstrations

The platform provides dedicated pages for eight OPEVA demonstrations:

| Demo | Topic |
|------|-------|
| Demo 1 | HiL Battery Balancing |
| Demo 2 | Battery Pack with Smart BMS |
| Demo 3 | Improved Sensors / EIS Monitoring |
| Demo 4 | Digital Twin for EV Performance |
| Demo 5 | Energy-Efficient Route Planning |
| Demo 6 & 8 | Inductive Charging Integration |
| Demo 7 | Functional Safety — Anomaly Detection |
| Demo 9 | Flexible Charging Scheduler |

Each demo page executes live SPARQL queries against the knowledge graph, displays KPI summaries and interactive charts, and allows downloading the result dataset as CSV or JSON.

## Technology Stack

- **Spring Boot 3.3.0** / **Java 20**
- **Apache Jena** — RDF and SPARQL processing
- **Virtuoso Triplestore** — RDF storage and SPARQL engine
- **Chart.js** — data visualisation
- **Leaflet** — geographic route maps (Demo 5)

## API

### SPARQL Endpoint

```
POST /sparql
Content-Type: text/plain
```

Executes a raw SPARQL SELECT query against the triple store. The following prefixes are injected automatically:

```sparql
PREFIX eauto: <https://cloud.erarge.com.tr/ontologies/eauto#>
PREFIX sosa:  <http://www.w3.org/ns/sosa/>
PREFIX ssn:   <http://www.w3.org/ns/ssn/>
PREFIX qudt:  <http://qudt.org/1.1/schema/qudt#>
PREFIX rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX xsd:   <http://www.w3.org/2001/XMLSchema#>
```

Returns a JSON array where each object represents a result row with SPARQL variable names as keys.

### Demo Endpoints

Each demonstration exposes one or more endpoints under `/api/demo{N}/...` returning structured JSON with KPI summaries and data series ready for charting.

## Setup

### Prerequisites

- Java 20+
- Maven 3.6+
- Access to a Virtuoso instance loaded with the OPEVA graphs

### Configuration

Copy the example properties file and fill in your Virtuoso connection details:

```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

### Run

```bash
mvn spring-boot:run
```

The application starts on `http://localhost:8080` by default. The home page provides an interactive hotspot diagram linking to all demonstration pages.

## Related Links

- [OPEVA Project Website](https://opeva.eu/)
- [E-Auto Ontology](https://cloud.erarge.com.tr/ontologies/eauto)
