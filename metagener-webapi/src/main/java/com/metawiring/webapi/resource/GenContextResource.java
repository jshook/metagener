package com.metawiring.webapi.resource;

import com.metawiring.wiring.GenContext;
import com.metawiring.wiring.GenContexts;
import com.metawiring.wiring.Metagener;
import org.eclipse.jetty.server.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/context")
public class GenContextResource {
    private final static Logger logger = LoggerFactory.getLogger(GenContextResource.class);
    private GenContexts genContexts;

    public GenContextResource(GenContexts genContexts) {
        this.genContexts = genContexts;
    }

    @POST
    @Path("/{contextName}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String loadContext(
            @PathParam("contextName") String contextName,
            String contextDefData) {
        GenContext genContext = Metagener.fromString(contextDefData);
        if (genContext == null) {
            throw new BadRequestException("bad context def");
        }
        if (genContexts.get(contextName) != null) {
            logger.warn("overwriting defined generator context name=" + contextName);
        }
        // logger.info("received via post: contextName:" + contextName + "\n def:\n" + contextDefData);
        genContexts.put(contextName, genContext);
        return contextDefData;
    }

    @GET
    @Path("/{contextName}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getContext(@PathParam("contextName") String contextName) {
        GenContext genContext = genContexts.get(contextName);
        if (genContext == null) {
            throw new NotFoundException("Undefined context name=" + contextName);
        }
        String syntax = Metagener.toSyntax(genContext.getMetagenDef());
        return syntax;
    }


}
