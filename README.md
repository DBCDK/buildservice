# Buildservice Rest Connector
Jar library with connector classes to ease rest calls to Buildservice.

## Usage
Add to pom.xml:
```` xml
<dependency>
  <groupId>dk.dbc</groupId>
  <artifactId>build-rest-connector</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
````

Just inject the connector:
````java
@Inject
UpdateServiceBuildConnector connector;
````

You must have the following environment variables in your deployment:

    BUILD_SERVICE_URL

## Example use

````java
BuildRequestDTO buildRequestDTO // .. Fill in request data object
BuildResponseDTO response = connector.buildRecord(buildRequestDTO);
        
if (response.getBuildStatusEnumDTO() != BuildStatusEnumDTO.OK) {
    // Handle build fail status
}
else {
    // Use response.getBibliographicRecordDTO() data to whatever
}
```` 