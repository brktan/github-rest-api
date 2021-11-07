package com.buraktan.githubapi.domain;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class Followers {

    private String name;

    @Override
    public String toString() {
        return name;
    }
}
