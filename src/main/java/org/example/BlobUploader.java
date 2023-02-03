package org.example;

import com.azure.core.util.BinaryData;
import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;

public class BlobUploader {
    private static final DefaultAzureCredential defaultCredential = new DefaultAzureCredentialBuilder().build();
    private final String storageAccount;

    private final String container;

    public BlobUploader(String storageAccountName, String containerName) {
        storageAccount = storageAccountName;
        container= containerName;
    }

    public BlobUploader(ConfigProperties props) {
        this(props.azureStorageAccount, props.azureStorageContainer);
    }

    public String endpoint() {
        return "https://" + this.storageAccount + ".blob.core.windows.net";
    }
    public BlobServiceClient blobServiceClient() {
    return new BlobServiceClientBuilder()
                .endpoint(this.endpoint())
                .credential(defaultCredential)
                .buildClient();
    }

    public BlobContainerClient blobContainerClient() {
        return this.blobServiceClient().getBlobContainerClient(this.container);
    }
    public BlobClient getBlobClient (String blobName) {
        return this.blobContainerClient().getBlobClient(blobName);
    }

    public void uploadFile(String blobName, String filePath) {
        this.getBlobClient(blobName).uploadFromFile(filePath);
    }

    public void uploadString(String blobName, String data) {
        this.getBlobClient(blobName).upload(BinaryData.fromString(data));
    }
}
