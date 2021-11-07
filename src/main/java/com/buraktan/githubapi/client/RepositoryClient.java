package com.buraktan.githubapi.client;

import com.buraktan.githubapi.domain.Repository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class RepositoryClient {

    private static final String BASE_URL = "https://api.github.com/orgs/";
    private static final String END_POINT = "/repos";

    public ArrayList<Repository> getRepositoryArray(String organization, int mf) {

        String inline = "";

        ArrayList<Repository> organizationArray = new ArrayList<>();

        try {

            URL url = new URL(BASE_URL + organization + END_POINT);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            Scanner sc = new Scanner(url.openStream());
            while (sc.hasNext()) {
                inline += sc.nextLine();
            }
            sc.close();

            JSONParser parse = new JSONParser();
            JSONArray organizationRepositoryArray = (JSONArray) parse.parse(inline);
            JSONObject jsonObject;

            Repository temp;

            for (int i = 0; i < mf; i++) {

                jsonObject = (JSONObject) organizationRepositoryArray.get(i);
                Long id = (Long) jsonObject.get("id");
                String organizationName = (String) jsonObject.get("name");

                Long forkCount = (Long) jsonObject.get("forks_count");
                String htmlURL = (String) jsonObject.get("html_url");
                String description = (String) jsonObject.get("description");
                String contributionURL = (String) jsonObject.get("contributors_url");

                organizationArray.add(Repository.builder()
                        .id(id)
                        .repositoryName(organizationName)
                        .forkCount(forkCount)
                        .htmlURL(htmlURL)
                        .contributionURL(contributionURL)
                        .description(description)
                        .build());

                for (int a = 0; a < organizationArray.size(); a++) {
                    for (int b = a + 1; b < organizationArray.size(); b++) {
                        if (organizationArray.get(a).getForkCount() < organizationArray.get(b).getForkCount()) {
                            temp = organizationArray.get(a);
                            organizationArray.set(a, organizationArray.get(b));
                            organizationArray.set(b, temp);
                        }
                    }
                }
            }
            conn.disconnect();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return organizationArray;
    }
}
