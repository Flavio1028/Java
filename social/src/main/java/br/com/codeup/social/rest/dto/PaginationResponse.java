package br.com.codeup.social.rest.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PaginationResponse {

    private List<?> items;

    private long totalItems;

    private long pageNumber;

    private int pageSize;

    private long totalPages;

}
