package br.com.codeup.social.rest;

import br.com.codeup.social.domain.model.User;
import br.com.codeup.social.domain.repository.UserRepository;
import br.com.codeup.social.rest.dto.CreateUserRequest;
import br.com.codeup.social.rest.dto.ResponseError;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private UserRepository repository;
    private Validator validator;

    @Inject
    public UserResource(UserRepository repository, Validator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    @POST
    @Transactional
    public Response createUser(CreateUserRequest userRequest) {

        Set<ConstraintViolation<CreateUserRequest>> violations = this.validator.validate(userRequest);
        if(!violations.isEmpty()) {
            return Response.status(422).entity(ResponseError.createFormValidation(violations)).build();
        }

        User user = new User();
        user.setName(userRequest.getName());
        user.setAge(userRequest.getAge());
        user.persist();

        return Response.status(Response.Status.CREATED).entity(user).build();
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
            return Response.status(Response.Status.NO_CONTENT).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }
    @PUT
    @Path("{id}")
    @Transactional
    public Response updateUser(@PathParam("id") Long userId, CreateUserRequest userData) {

        Set<ConstraintViolation<CreateUserRequest>> violations = this.validator.validate(userData);
        if(!violations.isEmpty()) {
            return Response.status(422).entity(ResponseError.createFormValidation(violations)).build();
        }

        User user = User.findById(userId);
        if(user != null) {
            user.setName(userData.getName());
            user.setAge(userData.getAge());

            user.persist();

            return Response.status(Response.Status.NO_CONTENT).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }



}
