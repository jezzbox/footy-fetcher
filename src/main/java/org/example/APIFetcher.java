package org.example;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.Key;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import com.azure.core.util.polling.SyncPoller;
import com.azure.identity.DefaultAzureCredentialBuilder;

import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import com.azure.security.keyvault.secrets.models.DeletedSecret;
import com.azure.security.keyvault.secrets.models.KeyVaultSecret;
import java.net.URI;

public class APIFetcher {
    final HttpClient client;
    final String baseUri;

    APIFetcher(String uri) {
        baseUri = uri;
        client = HttpClient.newHttpClient();
    }

    APIFetcher(HttpClient httpClient, String uri) {
        baseUri = uri;
        client = httpClient;
    }

    APIFetcher(ConfigProperties props) {
        this(props.apiFootballUrl);
    }

    private URI createUri(String endpoint) {
        return URI.create( this.baseUri + "/" + endpoint);
    }

    private HttpRequest request(String endpoint, int timeout, ConfigProperties props) {
        KeyVaultAuthenticator kvAuthenticator = new KeyVaultAuthenticator(props.azureKeyVault);
        String retrievedSecret = kvAuthenticator.getSecret(props.azureKeyVaultSecret);

        return HttpRequest.newBuilder()
                .uri(this.createUri(endpoint))
                .header("Content-Type", "application/json")
                .header("x-apisports-key", retrievedSecret)
                .header("X-RapidAPI-Host", this.baseUri)
                .timeout(Duration.ofSeconds(timeout))
                .GET()
                .build();
    }

    public CompletableFuture<HttpResponse<String>> getAsync(String endpoint, int timeout, ConfigProperties props) {
        var request = this.request(endpoint, timeout, props);
        return this.client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }

}
