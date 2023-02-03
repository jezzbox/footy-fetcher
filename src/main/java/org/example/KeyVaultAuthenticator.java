package org.example;

import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import com.azure.security.keyvault.secrets.models.KeyVaultSecret;

public class KeyVaultAuthenticator {
    private final String keyVaultName;
    private final SecretClient client;

    KeyVaultAuthenticator(String keyVaultName, DefaultAzureCredential azureCredential) {
        this.keyVaultName = keyVaultName;
        this.client = this.buildClient(azureCredential);
    }
    KeyVaultAuthenticator(String keyVaultName) {
        this(keyVaultName, new DefaultAzureCredentialBuilder().build());
    }

    KeyVaultAuthenticator(DefaultAzureCredential azureCredential) {
        this(System.getenv("KEY_VAULT_NAME"), azureCredential);
    }
    KeyVaultAuthenticator() {
        this(System.getenv("KEY_VAULT_NAME"));
    }

    KeyVaultAuthenticator(ConfigProperties props) {
        this(props.azureKeyVault);
    }
    private SecretClient buildClient(DefaultAzureCredential azureCredential) {
        return new SecretClientBuilder()
                .vaultUrl(this.keyVaultUri())
                .credential(azureCredential)
                .buildClient();
    }
    private String keyVaultUri() {
        return "https://" + this.keyVaultName + ".vault.azure.net";
    }

    public String getSecret(String name) {
        return this.client.getSecret(name).getValue();
    }
}
