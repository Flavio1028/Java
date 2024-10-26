package br.com.codeup.social.rest.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowerRequest {

    private Long followerId;

}