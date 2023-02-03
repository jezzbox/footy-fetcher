package org.example;

import java.net.http.HttpClient;
import com.azure.identity.*;
import com.azure.storage.blob.*;
import com.azure.storage.blob.models.*;
import java.io.*;
import java.sql.Blob;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws IOException {
        ConfigProperties props = new ConfigProperties();
        FootyFetcher footyFetcher = new FootyFetcher(props);
        APIEndpoint endpoint = new APIEndpoint(props);

        footyFetcher.fetchAsync(endpoint, props).join();
//        APIEndpoint leagues = new APIEndpoint("leagues");
//        APIEndpoint fixtures = new APIEndpoint("fixtures", "/fixtures", "?live=all&timezone=Europe/London");
//        APIEndpoint timezone = new APIEndpoint("timezone");
//        APIEndpoint countries = new APIEndpoint("countries", "/countries");
//        APIEndpoint seasons = new APIEndpoint("seasons", "/leagues/seasons");
//        APIEndpoint teams = new APIEndpoint("teams", "/teams", "?season=2022&league=39");
//        APIEndpoint teamStats = new APIEndpoint("statistics", "/teams/statistics", "?season=2022&league=39&team=39");
//        APIEndpoint venues = new APIEndpoint("venues", "/venues", "?country=England");
//        APIEndpoint standings = new APIEndpoint("standings", "/standings", "?season=2022");

//        var fetchLeagues = footyFetcher.fetchAsync(leagues);
//        var fetchTimezones = footyFetcher.fetchAsync(timezone);
//        var fetchFixtures = footyFetcher.fetchAsync(fixtures);
//        var fetchCountries = footyFetcher.fetchAsync(countries);
//        var fetchSeasons = footyFetcher.fetchAsync(seasons);
//        var fetchTeams = footyFetcher.fetchAsync(teams);
//        var fetchTeamStats = footyFetcher.fetchAsync(teamStats);
//        var fetchVenues = footyFetcher.fetchAsync(venues);
//        var fetchStandings = footyFetcher.fetchAsync(standings);
//
//        fetchLeagues.join();
//        fetchCountries.join();
//        fetchFixtures.join();
//        fetchTeams.join();
//        fetchSeasons.join();
//        fetchTeamStats.join();
//        fetchVenues.join();
//        fetchStandings.join();
    }
}