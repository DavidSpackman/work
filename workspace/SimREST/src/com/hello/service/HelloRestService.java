package com.hello.service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import com.dassault_systemes.platform.restServices.RestService;

public class HelloRestService  extends RestService {

    @GET
    @Path("/hello")
    @Produces("text/plain")
    public Response getHelloMessage(@Context HttpServletRequest request,@QueryParam("username") String username) throws Exception {

        // if no authentication, a 401 error is thrown
        matrix.db.Context context = authenticate(request);

        String helloMessage = "";
        if (username == null || username.isEmpty()) {
            helloMessage = "Hello " + context.getUser();
        } else {
            helloMessage = "Hello " + username;
        }

        return Response.ok(helloMessage).build();

    }

}
