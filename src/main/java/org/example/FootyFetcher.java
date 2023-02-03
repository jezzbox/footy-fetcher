package org.example;

import java.io.*;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

public class FootyFetcher {

    final APIFetcher apiFetcher;
    final BlobUploader blobUploader;

    final String date = new SimpleDateFormat("yyyyMMdd").format(new Date());

    FootyFetcher(ConfigProperties props) {
        apiFetcher = new APIFetcher(props);
        blobUploader = new BlobUploader(props);
    }

    public CompletableFuture<Void> fetchAsync(APIEndpoint endpoint, ConfigProperties props) {
        System.out.println("fetching endpoint: " + endpoint.name);

        var response = this.apiFetcher.getAsync(endpoint.fullPath(), 20, props);
        return response.thenApply(HttpResponse::body)
                .thenApply(resBody -> {
                    Instant now = Instant.now();

                    FileWriter writer = null;
                    try
                    {
                        writer = new FileWriter(endpoint.tempLocation(), false);
                        writer.write(resBody);
                        writer.close();
                    }
                    catch (IOException ex)
                    {
                        System.out.println(ex.getMessage());
                    }
                    return now;
                }).thenAccept(
                       dateTime -> blobUploader.uploadFile(endpoint.blobName(dateTime), endpoint.tempLocation())
                ).thenAccept(
                        r -> System.out.println("Finished: " + endpoint)
                );
    }
}
