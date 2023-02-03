package org.example;

import java.net.URI;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class APIEndpoint {
    public final String name;
    public final String path;
    public final String queryString;

    APIEndpoint(String name, String path, String queryString)  {
        this.name = name;
        this.path = path;
        this.queryString = queryString;
    }

    APIEndpoint(String name, String path) {
        this(name, path, "");
    }
    APIEndpoint(String name) {
        this(name, "/" + name, "");
    }

    APIEndpoint(ConfigProperties props) {
        this(props.endpointName, props.endpointPath, props.endpointQueryString);
    }

    public String fullPath() {
        return this.path + this.queryString;
    }

    public String tempLocation() {
        return "./data/" + path.replace('/', '_') + ".json";
    }

    public String blobName(Instant now) {
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("yyyyMMddHHmmss")
                .withZone(ZoneId.of("UTC"));
        String dateTime = formatter.format(now);

        return this.name + "/" + dateTime  + ".json";
    }

}
