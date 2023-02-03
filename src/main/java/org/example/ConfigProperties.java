package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigProperties {
    final String azureKeyVault;
    final String azureKeyVaultSecret;
    final String azureStorageAccount;
    final String azureStorageContainer;
    final String apiFootballUrl;
    final String endpointName;
    final String endpointPath;
    final String endpointQueryString;

    ConfigProperties() throws IOException {
        InputStream input = Main.class.getClassLoader().getResourceAsStream("config.properties");

        Properties prop = new Properties();

        prop.load(input);

        azureKeyVault = prop.getProperty("azure.keyVaultName");
        azureKeyVaultSecret = prop.getProperty("azure.keyVault.secret");
        azureStorageAccount = prop.getProperty("azure.storageAccount");
        azureStorageContainer = prop.getProperty("azure.storageContainer");
        apiFootballUrl = prop.getProperty("apiFootball.url");
        endpointName = prop.getProperty("endpoint.name");
        endpointPath = prop.getProperty("endpoint.path");
        endpointQueryString = prop.getProperty("endpoint.queryString");
    }

}
