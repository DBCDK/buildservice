/*
 * Copyright Dansk Bibliotekscenter a/s. Licensed under GPLv3
 * See license text in LICENSE.txt or at https://opensource.dbc.dk/licenses/gpl-3.0/
 */

package dk.dbc.updateservice;

import dk.dbc.httpclient.HttpClient;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.ws.rs.client.Client;

@ApplicationScoped
public class UpdateServiceBuildConnectorFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateServiceBuildConnectorFactory.class);

    public static UpdateServiceBuildConnector create(String buildServiceBaseUrl) {
        final Client client = HttpClient.newClient(new ClientConfig())
                .register(new JacksonFeature());
        LOGGER.info("Creating UpdateServiceBuildConnector for: '{}' ", buildServiceBaseUrl);
        return new UpdateServiceBuildConnector(client, buildServiceBaseUrl);
    }

    public static UpdateServiceBuildConnector create(String buildServiceBaseUrl, UpdateServiceBuildConnector.TimingLogLevel level) {
        final Client client = HttpClient.newClient(new ClientConfig())
                .register(new JacksonFeature());
        LOGGER.info("Creating UpdateServiceBuildConnector for: '{}' ", buildServiceBaseUrl);
        return new UpdateServiceBuildConnector(client, buildServiceBaseUrl, level);
    }

    @Inject
    @ConfigProperty(name = "BUILD_SERVICE_URL")
    private String buildServiceUrl;

    @Inject
    @ConfigProperty(name = "BUILD_SERVICE_TIMING_LOG_LEVEL", defaultValue = "INFO")
    private UpdateServiceBuildConnector.TimingLogLevel level;

    UpdateServiceBuildConnector updateServiceBuildConnector;

    @PostConstruct
    public void initializeConnector() {
        updateServiceBuildConnector = UpdateServiceBuildConnectorFactory.create(buildServiceUrl, level);
    }

    @Produces
    public UpdateServiceBuildConnector getInstance() { return updateServiceBuildConnector; }

    @PreDestroy
    public void tearDownConnector() { updateServiceBuildConnector.close(); }

}
