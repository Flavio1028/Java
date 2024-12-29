package br.com.codeup.social.rest;

import br.com.codeup.social.domain.model.Post;
import br.com.codeup.social.domain.model.User;
import br.com.codeup.social.domain.repository.FollowerRepository;
import br.com.codeup.social.domain.repository.PostRepository;
import br.com.codeup.social.domain.repository.UserRepository;
import br.com.codeup.social.exceptions.ForbiddenFollowsExcepetion;
import br.com.codeup.social.exceptions.ParametersException;
import br.com.codeup.social.rest.dto.CreatePostRequest;
import br.com.codeup.social.rest.dto.PaginationResponse;
import br.com.codeup.social.rest.dto.PostResponse;
import io.quarkus.panache.common.Sort;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users/{userId}/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostResource {
    private final UserRepository userRepository;
    private final PostRepository repository;
    private final FollowerRepository followerRepository;

    @Inject
    public PostResource(
            UserRepository userRepository,
            PostRepository repository,
            FollowerRepository followerRepository) {
        this.userRepository = userRepository;
        this.repository = repository;
        this.followerRepository = followerRepository;
    }

    @POST
    @Transactional
    public Response savePost(
            @PathParam("userId") Long userId, CreatePostRequest request) {

        User user = userRepository.findById(userId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Post post = new Post();
        post.setText(request.getText());
        post.setUser(user);

        repository.persist(post);

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    public Response listPosts(
            @PathParam("userId") Long userId,
            @HeaderParam("followerId") Long followerId,
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize) {

        User user = userRepository.findById(userId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (followerId == null) {
            throw new ParametersException("You forgot the header followerId");
        }

        User follower = userRepository.findById(followerId);

        if (follower == null) {
            throw new ParametersException("Inexistent followerId");
        }

        boolean follows = followerRepository.follows(follower, user);
        if (!follows) {
            throw new ForbiddenFollowsExcepetion("You can't see these posts");
        }

        var query = repository.find(
                        "user", Sort.by("dateTime", Sort.Direction.Descending), user)
                .page(page, pageSize);
        long totalItems = query.count();

        PaginationResponse response = PaginationResponse.builder()
                .items(query.list().stream().map(PostResponse::fromEntity).toList())
                .totalItems(totalItems)
                .pageNumber(page)
                .pageSize(pageSize)
                .totalPages((long) Math.ceil((double) totalItems / pageSize))
                .build();

        return Response.ok(response).build();
    }

}
