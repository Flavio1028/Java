package br.com.codeup.social.rest;

import br.com.codeup.social.domain.model.Post;
import br.com.codeup.social.domain.model.User;
import br.com.codeup.social.domain.repository.PostRepository;
import br.com.codeup.social.domain.repository.UserRepository;
import br.com.codeup.social.rest.dto.CreatePostRequest;
import br.com.codeup.social.rest.dto.PostResponse;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Path("/users/{userId}/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostResource {

    private UserRepository userRepository;

    private PostRepository postRepository;

    @Inject
    public PostResource( UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }
    @POST
    @Transactional
    public Response savePost(@PathParam("userId") Long userId, CreatePostRequest request) {

        User user = userRepository.findById(userId);

        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Post post = new Post();
        post.setText(request.getText());
        post.setUser(user);

        this.postRepository.persist(post);

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    public Response listPosts(@PathParam("userId") Long userId) {

        User user = userRepository.findById(userId);

        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        PanacheQuery<Post> query = this.postRepository
                .find("user", Sort.by("date_time", Sort.Direction.Descending) ,user);

        var PostResponseList = query.stream().map(PostResponse::fromEntity).collect(Collectors.toList());

        return Response.ok(PostResponseList).build();
    }

}
