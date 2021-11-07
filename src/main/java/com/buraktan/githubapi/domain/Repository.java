package com.buraktan.githubapi.domain;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class Repository {

    private Long id;
    private String repositoryName;
    private Long forkCount;
    private String htmlURL;
    private String description;

    private String contributionURL;

    @Override
    public String toString() {
        return
                id +
                        "," + repositoryName +
                        "," + forkCount +
                        "," + htmlURL +
                        "," + description +
                        "," + contributionURL;
    }
}
