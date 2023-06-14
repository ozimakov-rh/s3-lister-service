package com.redhat.demo;

import io.smallrye.mutiny.Multi;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/list")
public class ListResource {

    @Inject
    ListerService listerService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Multi<String> list() {
        return listerService.listFiles();
    }

}
