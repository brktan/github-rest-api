package com.buraktan.githubapi.domain;

import lombok.*;

import java.util.ArrayList;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class Contributors {

    private String name;
    private Long contributorsCount;
    private String followersURL;

    private ArrayList<Followers> followersList;

    @Override
    public String toString() {
        return
                name +
                        "," + contributorsCount +
                        "," + followersURL +
                        "," + followersList;
    }
}
