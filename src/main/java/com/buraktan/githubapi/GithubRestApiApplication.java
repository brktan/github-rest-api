package com.buraktan.githubapi;

import com.buraktan.githubapi.client.ContributorsClient;
import com.buraktan.githubapi.client.FollowersClient;
import com.buraktan.githubapi.client.RepositoryClient;
import com.buraktan.githubapi.domain.Contributors;
import com.buraktan.githubapi.domain.Repository;
import com.buraktan.githubapi.util.CSVWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
public class GithubRestApiApplication implements ApplicationRunner {

    @Value("${organization}")
    private String organization;

    @Value("${mostforked}")
    private String mostforked;

    @Value("${topcontributors}")
    private String topcontributors;

    private final static Logger LOGGER = Logger.getLogger(Logger.class.getName());

    private final RepositoryClient repositoryClient = new RepositoryClient();

    private final ContributorsClient contributorsClient = new ContributorsClient();

    private final FollowersClient followersClient = new FollowersClient();

    public static void main(String[] args) {
        SpringApplication.run(GithubRestApiApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        LOGGER.log(Level.INFO, "Organization Name: " + organization);
        LOGGER.log(Level.INFO, "Most Forked: " + mostforked);
        LOGGER.log(Level.INFO, "Top Contributors: " + topcontributors);

        int mf = Integer.parseInt(mostforked);
        int tc = Integer.parseInt(topcontributors);

        ArrayList<Repository> repositoryArray = repositoryClient.getRepositoryArray(organization, mf);

        ArrayList<Contributors> contributorsArray = contributorsClient.getContributorsArray(mf, tc, repositoryArray);

        followersClient.getFollowersArray(mf, contributorsArray);

        String[] header = {"Id", "RepositoryName", "ForkCount", "HTMLURL", "Description", "ContributorURL"};
        String[] header2 = {"Name", "ContributorsCount", "FollowersURL", "Followers"};

        CSVWriter csvWriter = new CSVWriter();

        csvWriter.writer(organization, Collections.singleton(repositoryArray), header);
        csvWriter.writer("users", Collections.singleton(contributorsArray), header2);
    }
}
