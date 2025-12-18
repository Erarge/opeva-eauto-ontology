# Opeva E-Auto Ontology API

A Spring Boot REST API for querying Virtuoso RDF store using SPARQL. This project provides a clean interface to execute SPARQL queries against the eAuto ontology.

This project is part of the [OPEVA (OPtimization of Electric Vehicle Autonomy)](https://opeva.eu/) European project, funded within the Key Digital Technologies Joint Undertaking (KDT JU) from the European Union's Horizon Europe Programme.

## Features

- REST API endpoint for SPARQL query execution
- Integration with Virtuoso RDF database
- Automatic prefix injection for common ontologies
- CORS enabled for cross-origin requests
- JSON response format

## Prerequisites

- Java 20 or higher
- Maven 3.6+
- Virtuoso database server

## Configuration

### Initial Setup

1. Copy the example properties file:
   ```bash
   cp src/main/resources/application.properties.example src/main/resources/application.properties
   ```

2. Edit `src/main/resources/application.properties` with your Virtuoso connection details:

```properties
spring.application.name=opevaontology
connection.ip=YOUR_VIRTUOSO_IP
connection.port=1111
connection.username=YOUR_USERNAME
connection.password=YOUR_PASSWORD
```

**Security Note:** 
- Credentials in `application.properties` are automatically censored when committing to git using git filters
- **Important**: You need to run the setup script once on your local machine: `.\scripts\setup-git-filters.ps1` (Windows) or `./scripts/setup-git-filters.sh` (Linux/Mac)
- See `scripts/setup-git-filters.md` for detailed instructions
- The `.example` file can be used as a template for new developers

## Building and Running

### Build the project
```bash
mvn clean package
```

### Run the application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080` by default (or the port configured in `application.properties` if `server.port` is set).

## API Documentation

### 1. SPARQL Endpoint

Execute raw SPARQL queries against the Virtuoso database.

**Endpoint:** `POST /sparql`

**Content-Type:** `text/plain`

**Request Body:** SPARQL query string (SELECT, ASK, CONSTRUCT, or DESCRIBE queries)

**Response:** JSON array of objects, where each object represents a query result row with variable names as keys.

### 2. Get Filter Options

Retrieve available filter ranges and options for the frontend.

**Endpoint:** `GET /api/filter-options`

**Response:**
```json
{
  "minTime": "2025-05-01T09:13:02",
  "maxTime": "2025-05-02T08:43:27",
  "distanceMmOptions": [70.0, 80.0, 90.0],
  "socPercentOptions": [20.0, 30.0, 40.0, 50.0]
}
```

### 3. Query by Time Interval

Query charging session data within a time interval.

**Endpoint:** `POST /api/query/time-interval`

**Content-Type:** `application/json`

**Request Body:**
```json
{
  "from": "2025-05-01T09:13:02",
  "to": "2025-05-02T08:43:27"
}
```

**Response:** JSON array of charging session data with observations.

### 4. Query with Filters

Query charging session data with time interval, coil distance, and SOC filters.

**Endpoint:** `POST /api/query/filtered`

**Content-Type:** `application/json`

**Request Body:**
```json
{
  "from": "2025-05-01T09:13:02",
  "to": "2025-05-02T08:43:27",
  "distanceMm": 80.0,
  "socPercent": 30.0
}
```

**Note:** `distanceMm` and `socPercent` are optional (can be `null`).

**Response:** JSON array of filtered charging session data with observations.

### Frontend Interface

A web interface is available at the root URL (`http://localhost:8080/`) that provides:

- Automatic loading of filter options on page load
- Form-based query interface with time pickers and dropdowns
- Results displayed in a sortable table
- Support for both query endpoints

**Access the frontend:** Simply navigate to `http://localhost:8080/` in your browser after starting the application.

#### Pre-configured Prefixes

The following prefixes are automatically prepended to your queries:

- `PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>`
- `PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>`
- `PREFIX eauto: <https://cloud.erarge.com.tr/ontologies/eauto#>`
- `PREFIX sosa: <http://www.w3.org/ns/sosa/>`
- `PREFIX ssn: <http://www.w3.org/ns/ssn/>`
- `PREFIX qudt-1-1: <http://qudt.org/1.1/schema/qudt#>`
- `PREFIX qudt-unit-1-1: <http://qudt.org/1.1/vocab/unit#>`

**Note:** You only need to provide the SELECT/WHERE portion of your query. The prefixes above are automatically added.

#### Example Requests

**Simple SELECT query:**
```bash
curl -X POST http://localhost:8080/sparql \
  -H "Content-Type: text/plain" \
  -d "SELECT ?s ?p ?o WHERE { ?s ?p ?o } LIMIT 10"
```

**Query with ontology classes:**
```bash
curl -X POST http://localhost:8080/sparql \
  -H "Content-Type: text/plain" \
  -d "SELECT ?s WHERE { ?s rdf:type eauto:SomeClass } LIMIT 5"
```

**Get filter options:**
```bash
curl -X GET http://localhost:8080/api/filter-options
```

**Query by time interval:**
```bash
curl -X POST http://localhost:8080/api/query/time-interval \
  -H "Content-Type: application/json" \
  -d '{"from":"2025-05-01T09:13:02","to":"2025-05-02T08:43:27"}'
```

**Query with filters:**
```bash
curl -X POST http://localhost:8080/api/query/filtered \
  -H "Content-Type: application/json" \
  -d '{"from":"2025-05-01T09:13:02","to":"2025-05-02T08:43:27","distanceMm":80.0,"socPercent":30.0}'
```

#### Example Response

```json
[
  {
    "s": "https://cloud.erarge.com.tr/ontologies/eauto#individual1",
    "p": "http://www.w3.org/1999/02/22-rdf-syntax-ns#type",
    "o": "https://cloud.erarge.com.tr/ontologies/eauto#SomeClass"
  },
  {
    "s": "https://cloud.erarge.com.tr/ontologies/eauto#individual2",
    "p": "http://www.w3.org/1999/02/22-rdf-syntax-ns#type",
    "o": "https://cloud.erarge.com.tr/ontologies/eauto#SomeClass"
  }
]
```

## Project Structure

```
src/main/java/com/erarge/opevaontology/
├── connection/
│   ├── VirtuosoConfiguration.java    # Virtuoso connection configuration
│   └── VirtuosoConnection.java       # Virtuoso connection management
├── controller/
│   └── APIController.java            # REST API controller with /sparql endpoint
├── repository/
│   └── CustomSPARQL.java             # SPARQL query execution logic
├── service/
│   ├── IService.java                 # Service interface
│   └── impl/
│       └── ServiceImpl.java          # Service implementation
├── PdmApplication.java               # Spring Boot application entry point
└── ServletInitializer.java           # Servlet initializer for WAR deployment
```

## Technology Stack

- **Spring Boot 3.3.0**
- **Java 20**
- **Apache Jena** - RDF and SPARQL processing
- **Virtuoso Jena Driver** - Virtuoso database connectivity

## Related Links

- [OPEVA Project Website](https://opeva.eu/) - OPtimization of Electric Vehicle Autonomy

## License

[Add your license information here]
