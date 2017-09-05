package com.part.services;

import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.dassault_systemes.platform.restServices.MediaProviderJSON;
import com.dassault_systemes.platform.restServices.RestService;

import matrix.db.BusinessObject;
import matrix.db.BusinessObjectWithSelect;
import matrix.db.Query;
import matrix.db.QueryIterator;
import matrix.util.StringList;

public class PartRestService extends RestService {

    private static final String QUERYPARAM_PARTNAME = "partName";
    private static final String QUERYPARAM_PARTREVISION = "partRevision";
    private static final String QUERYPARAM_PARTVAULT = "partVault";
    private static final String QUERYPARAM_PARTDESCRIPTION = "partDescription";

    private static final String WILDCARD = "*";

    private static final String OBJ_TYPE = "Part";
    private static final String OBJ_POLICY = "EC Part";

    private static final String SELECT_TYPE = "type";
    private static final String SELECT_NAME = "name";
    private static final String SELECT_REVISION = "revision";
    private static final String SELECT_POLICY = "policy";
    private static final String SELECT_VAULT = "vault";
    private static final String SELECT_ORIGINATED = "originated";
    private static final String SELECT_MODIFIED = "modified";
    private static final String SELECT_ORIGINATOR = "originator";
    private static final String SELECT_ATTRIBUTE_ORIGINATOR = "attribute[" + SELECT_ORIGINATOR + "].value";
    private static final String SELECT_CURRENT = "current";

    private static final short PAGE_SIZE = 1000;

    @OPTIONS
    @Produces("application/json; charset=UTF-8")
    public Response options() {
        return Response.ok().build();
    }

    @GET
    @Path("/query")
    @Consumes("text/plain")
    @Produces({ MediaProviderJSON.TYPE, MediaType.APPLICATION_JSON })
    public Response queryPart(@Context HttpServletRequest request,
        @QueryParam(QUERYPARAM_PARTNAME) String partName,
        @QueryParam(QUERYPARAM_PARTREVISION) String partRevision,
        @QueryParam(QUERYPARAM_PARTVAULT) String partVault) throws Exception {

        // if no authentication, a 401 error is thrown
        matrix.db.Context context = authenticate(request);

        StringList sList = null;
        QueryIterator queryIterator = null;
        JsonObjectBuilder jObjectBuilder = null;
        ArrayList<JsonObject> jsonObjectArrayList = new ArrayList<JsonObject>();
        try {

            // Start the creation
            context.start(true);

            // build a query
            Query query = new Query();
            query.setExpandType(true);
            query.setBusinessObjectType(OBJ_TYPE);
            BusinessObjectWithSelect bows = null;

            // set the name from the parameter
            if (partName == null || partName.isEmpty()) {
                query.setBusinessObjectName(WILDCARD);
            } else {
                query.setBusinessObjectName(partName);
            }

            // set the revision from the parameter
            if (partRevision == null || partRevision.isEmpty()) {
                query.setBusinessObjectRevision(WILDCARD);
            } else {
                query.setBusinessObjectRevision(partRevision);
            }

            // set the vault from the parameter
            if (partVault == null || partVault.isEmpty()) {
                query.setVaultPattern(WILDCARD);
            } else {
                query.setVaultPattern(partVault);
            }

            // run the query
            query.setWhereExpression("");
            query.evaluate(context);

            sList = new StringList();
            sList.add(SELECT_TYPE);
            sList.add(SELECT_NAME);
            sList.add(SELECT_REVISION);
            sList.add(SELECT_POLICY);
            sList.add(SELECT_VAULT);
            sList.add(SELECT_ORIGINATED);
            sList.add(SELECT_MODIFIED);
            sList.add(SELECT_CURRENT);
            sList.add(SELECT_ATTRIBUTE_ORIGINATOR);

            queryIterator = query.getIterator(context, sList, PAGE_SIZE);
            while (queryIterator.hasNext()) {

                // Build the result
                bows = queryIterator.next();
                jObjectBuilder = Json.createObjectBuilder();
                jObjectBuilder.add(SELECT_TYPE, OBJ_TYPE);
                jObjectBuilder.add(SELECT_NAME, bows.getSelectData(SELECT_NAME));
                jObjectBuilder.add(SELECT_REVISION, bows.getSelectData(SELECT_REVISION));
                jObjectBuilder.add(SELECT_POLICY, bows.getSelectData(SELECT_POLICY));
                jObjectBuilder.add(SELECT_VAULT, bows.getSelectData(SELECT_VAULT));
                jObjectBuilder.add(SELECT_ORIGINATED, bows.getSelectData(SELECT_ORIGINATED));
                jObjectBuilder.add(SELECT_MODIFIED, bows.getSelectData(SELECT_MODIFIED));
                jObjectBuilder.add(SELECT_CURRENT, bows.getSelectData(SELECT_CURRENT));
                jObjectBuilder.add(SELECT_ORIGINATOR, bows.getSelectData(SELECT_ATTRIBUTE_ORIGINATOR));
                jsonObjectArrayList.add(jObjectBuilder.build());

            }
            queryIterator.close();

        } catch (Exception e) {

            return Response.status(500).build();

        } finally {

            try {
                context.commit();
            } catch (Exception e) {
                return Response.status(500).build();
            }

        }

        return Response.ok(jsonObjectArrayList.toString()).build();

    }

    @GET
    @Path("/create")
    @Consumes("text/plain")
    @Produces("text/html")
    public Response createPart(@Context HttpServletRequest request,
        @QueryParam(QUERYPARAM_PARTNAME) String partName,
        @QueryParam(QUERYPARAM_PARTREVISION) String partRevision,
        @QueryParam(QUERYPARAM_PARTVAULT) String partVault) throws Exception {

        // if no authentication, a 401 error is thrown
        matrix.db.Context context = authenticate(request);

        // set the name from the parameter
        if (partName == null || partName.isEmpty()) {
            return Response.status(400).build();
        }

        // set the revision from the parameter
        if (partRevision == null || partRevision.isEmpty()) {
            return Response.status(400).build();
        }

        // set the vault from the parameter
        if (partVault == null || partVault.isEmpty()) {
            return Response.status(400).build();
        }

        try {

            context = authenticate(request);
            BusinessObject busObj = new BusinessObject(OBJ_TYPE, partName, partRevision, partVault);
            busObj.create(context, OBJ_POLICY);

        } catch (Exception e) {
            return Response.status(500).build();
        }

        return Response.ok().build();

    }

    @GET
    @Path("/update")
    @Consumes("text/plain")
    @Produces("text/html")
    public Response updatePart(@Context HttpServletRequest request,
        @QueryParam(QUERYPARAM_PARTNAME) String partName,
        @QueryParam(QUERYPARAM_PARTREVISION) String partRevision,
        @QueryParam(QUERYPARAM_PARTVAULT) String partVault,
        @QueryParam(QUERYPARAM_PARTDESCRIPTION) String partDescription) throws Exception {

        // if no authentication, a 401 error is thrown
        matrix.db.Context context = authenticate(request);

        // set the name from the parameter
        if (partName == null || partName.isEmpty()) {
            return Response.status(400).build();
        }

        // set the revision from the parameter
        if (partRevision == null || partRevision.isEmpty()) {
            return Response.status(400).build();
        }

        // set the vault from the parameter
        if (partVault == null || partVault.isEmpty()) {
            return Response.status(400).build();
        }
        
        // set the vault from the parameter
        if (partDescription == null || partDescription.isEmpty()) {
            return Response.status(400).build();
        }
        
        try {

            context = authenticate(request);
            BusinessObject busObj = new BusinessObject(OBJ_TYPE, partName, partRevision, partVault);
            busObj.open(context);
            busObj.setDescription(context, partDescription);
            busObj.update(context);
            busObj.close(context);

        } catch (Exception e) {
            return Response.status(500).build();
        }

        return Response.ok().build();

    }

}
