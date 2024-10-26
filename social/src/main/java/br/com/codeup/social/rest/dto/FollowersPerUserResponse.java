package br.com.codeup.social.rest.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowersPerUserResponse {

    private Integer followersCount;
    private List<FollowerResponse> content;

}