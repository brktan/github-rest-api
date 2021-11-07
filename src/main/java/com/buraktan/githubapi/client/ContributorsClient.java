package com.buraktan.githubapi.client;

import com.buraktan.githubapi.domain.Contributors;
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

public class ContributorsClient {


    public ArrayList<Contributors> getContributorsArray(int mf, int tc, ArrayList<Repository> organizationArray) {


        String inline2 = "";

        ArrayList<Contributors> contributorsArray = new ArrayList<>();
        try {

            for (int choose = 0; choose < mf; choose++) {
                URL url2 = new URL(organizationArray.get(choose).getContributionURL());

                HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
                conn2.setRequestMethod("GET");
                conn2.setRequestProperty("Accept", "application/json");

                if (conn2.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "
                            + conn2.getResponseCode());
                }

                Scanner sc2 = new Scanner(url2.openStream());


                while (sc2.hasNext()) {
                    inline2 += sc2.nextLine();
                }
                sc2.reset();
                sc2.close();

                JSONParser parse2 = new JSONParser();
                JSONArray contributorsArrayList = (JSONArray) parse2.parse(inline2);
                inline2 = "";
                for (int i = 0; i < tc; i++) {

                    JSONObject jsonObject2 = (JSONObject) contributorsArrayList.get(i);

                    String name = (String) jsonObject2.get("login");
                    Long contributorsCount = (Long) jsonObject2.get("contributions");
                    String followersURL = (String) jsonObject2.get("followers_url");

                    Contributors.builder()
                            .name(name)
                            .contributorsCount(contributorsCount)
                            .followersURL(followersURL)
                            .build();

                    contributorsArray.add(Contributors.builder()
                            .name(name)
                            .contributorsCount(contributorsCount)
                            .followersURL(followersURL)
                            .build());


                    Contributors temp2;
                    for (int a = 0; a < contributorsArray.size(); a++) {
                        for (int b = a + 1; b < contributorsArray.size(); b++) {
                            if (contributorsArray.get(a).getContributorsCount() < contributorsArray.get(b).getContributorsCount()) {
                                temp2 = contributorsArray.get(a);
                                contributorsArray.set(a, contributorsArray.get(b));
                                contributorsArray.set(b, temp2);
                            }
                        }
                    }
                }
                conn2.disconnect();
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return contributorsArray;
    }
}
