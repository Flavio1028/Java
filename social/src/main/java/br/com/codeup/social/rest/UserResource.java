package br.com.codeup.social.rest;

import br.com.codeup.social.domain.model.User;
import br.com.codeup.social.domain.repository.UserRepository;
import br.com.codeup.social.rest.dto.CreateUserRequest;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private UserRepository repository;

    @Inject
    public UserResource(UserRepository repository) {
        this.repository = repository;
    }

    @POST
    @Transactional
    public Response createUser(CreateUserRequest userRequest) {

        User user = new User();

        user.setName(userRequest.getName());
        user.setAge(userRequest.getAge());

        user.persist();

        return Response.ok(user).build();
    }

    @GET
    public Response listAllUsers() {
        return Response.ok(this.repository.findAll().list()).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deleteUser(@PathParam("id") Long userId) {

        User user = User.findById(userId);

        if(user != null) {
            user.delete();
            return Response.ok().build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }
    @PUT
    @Path("{id}")
    @Transactional
    public Response updateUser(@PathParam("id") Long userId, CreateUserRequest userData) {

        User user = User.findById(userId);

        if(user != null) {
            user.setName(userData.getName());
            user.setAge(userData.getAge());

            user.persist();

            return Response.ok().build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }



}
