package br.com.codeup.social.rest.dto;

import br.com.codeup.social.domain.model.Post;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {

    private String text;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime;

    public static PostResponse fromEntity(Post post) {
        var response = new PostResponse();
        response.setText(post.getText());
        response.setDateTime(post.getDateTime());
        return response;
    }

}
