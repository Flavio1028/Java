package br.com.codeup.social.rest;

import br.com.codeup.social.domain.model.Follower;
import br.com.codeup.social.domain.model.User;
import br.com.codeup.social.domain.repository.FollowerRepository;
import br.com.codeup.social.domain.repository.UserRepository;
import br.com.codeup.social.rest.dto.FollowerRequest;
import br.com.codeup.social.rest.dto.FollowerResponse;
import br.com.codeup.social.rest.dto.FollowsUserResponse;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/users/{userId}/follower")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FollowerResource {

    private FollowerRepository followerRepository;

    private UserRepository userRepository;

    @Inject
    public FollowerResource(FollowerRepository followerRepository, UserRepository userRepository) {
        this.followerRepository = followerRepository;
        this.userRepository = userRepository;
    }

    @PUT
    @Transactional
    public Response followUser(@PathParam("userId") Long userId, FollowerRequest request) {

        if (userId.equals(request.getFollowerId())){
            return Response.status(Response.Status.CONFLICT).entity("You_can't_follow_yourself").build();
        }

        User user = userRepository.findById(userId);

        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        var follower = userRepository.findById(request.getFollowerId());

        boolean follows = this.followerRepository.follows(follower, user);

        if(!follows) {

            var entity = new Follower();
            entity.setUser(user);
            entity.setFollower(follower);

            this.followerRepository.persist(entity);

        }

        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    public Response listFollowers(@PathParam("userId") Long userId) {

        User user = userRepository.findById(userId);

        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        List<Follower> listUser = this.followerRepository.findByUser(userId);
        FollowsUserResponse obj = new FollowsUserResponse();
        obj.setFollowersCount(listUser.size());

        List<FollowerResponse> list = listUser.stream().map(FollowerResponse::new).collect(Collectors.toList());

        obj.setContent(list);

        return Response.ok(obj).build();

    }

    @DELETE
    @Transactional
    public Response unFollowUser(@PathParam("userId") Long userId, @QueryParam("followerId") Long followerId) {

        User user = userRepository.findById(userId);

        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        this.followerRepository.deleteByFollowerAndUser(userId, followerId);

        return Response.status(Response.Status.NO_CONTENT).build();
    }

}
