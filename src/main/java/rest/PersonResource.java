package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PersonDTO;
import errorhandling.PersonNotFoundException;
import utils.EMF_Creator;
import facades.PersonFacade;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

//Todo Remove or change relevant parts before ACTUAL use
@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final PersonFacade FACADE =  PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Path("/all")
    @Produces({MediaType.APPLICATION_JSON})
    public Response demo() {
        List<PersonDTO> personDTOList = FACADE.getAll();
        String json = GSON.toJson(personDTOList);
        return Response.ok().entity(json).build();
    }
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response getPerson(@PathParam("id") long id) throws PersonNotFoundException {
        PersonDTO personDTO= FACADE.getById(id);
        return Response.ok().entity(personDTO).build();

    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createPerson(String jsonInput){
        PersonDTO personDTO = GSON.fromJson(jsonInput, PersonDTO.class);
        FACADE.create(personDTO);
        System.out.println(personDTO);
        return Response.ok().entity(GSON.toJson(personDTO)).build();
    }

    @PUT
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updatePerson(@PathParam("id") long id, String jsonInput){
        PersonDTO personDTO = GSON.fromJson(jsonInput, PersonDTO.class);
        personDTO.setId(id);
        FACADE.update(personDTO);
        System.out.println(personDTO);
        return Response.ok().entity(GSON.toJson(personDTO)).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public void deletePerson(@PathParam("id") long id){
        FACADE.delete(id);
    }

    @Path("/exception")
    @GET
    @Produces("text/plain")
    public String throwException() throws Exception {
        throw new Exception("my exception");
    }
}
