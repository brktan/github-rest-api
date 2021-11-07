package com.buraktan.githubapi.client;

import com.buraktan.githubapi.domain.Contributors;
import com.buraktan.githubapi.domain.Followers;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class FollowersClient {

    public ArrayList<Followers> getFollowersArray(int mf, ArrayList<Contributors> contributorsArray) {


        ArrayList<Followers> followersArray = new ArrayList<>();

        try {

            String inline3 = "";


            for (int choose2 = 0; choose2 < mf; choose2++) {
                URL url3 = new URL(contributorsArray.get(choose2).getFollowersURL());

                HttpURLConnection conn3 = (HttpURLConnection) url3.openConnection();

                conn3.setRequestMethod("GET");
                conn3.setRequestProperty("Accept", "application/json");

                if (conn3.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "
                            + conn3.getResponseCode());
                }
                Scanner sc3 = new Scanner(url3.openStream());
                while (sc3.hasNext()) {
                    inline3 += sc3.nextLine();
                }
                sc3.reset();
                sc3.close();

                JSONParser parse3 = new JSONParser();
                JSONArray followersArrayList = (JSONArray) parse3.parse(inline3);
                inline3 = "";
                for (int i = 0; i < followersArrayList.size(); i++) {

                    JSONObject jsonObject3 = (JSONObject) followersArrayList.get(i);

                    String name = (String) jsonObject3.get("login");

                    Followers.builder()
                            .name(name)
                            .build();

                    followersArray.add(Followers.builder()
                            .name(name)
                            .build());

                    contributorsArray.get(contributorsArray.size() - 1).setFollowersList(followersArray);
                }


                conn3.disconnect();
            }
        } catch (IOException | ParseException e) {

            e.printStackTrace();
        }
        return followersArray;
    }
}
