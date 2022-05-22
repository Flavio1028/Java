package br.com.codeup.social.rest.dto;

import lombok.Data;

import java.util.List;

@Data
public class FollowsUserResponse {

    private Integer followersCount;

    private List<FollowerResponse> content;

}
