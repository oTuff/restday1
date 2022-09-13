package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.EmployeeDTO;
import facades.EmployeeFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("employee")
public class EmployeeResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final EmployeeFacade FACADE = EmployeeFacade.getInstance(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Path("/all")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllEmployeeDTO() {
        List<EmployeeDTO> employeeDTOList = EmployeeDTO.getDtos(FACADE.getAllEmployees());
        String json = GSON.toJson(employeeDTOList);
        return Response.ok().entity(json).build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getEmployee(@PathParam("id") int id) {
        EmployeeDTO employeeDTO = new EmployeeDTO(FACADE.getEmployeeById(id));
        String json = GSON.toJson(employeeDTO);
        return Response.ok().entity(json).build();
    }

    @GET
    @Path("/highestpaid")
    @Produces({MediaType.APPLICATION_JSON})
    public Response highestPaid() {
        List<EmployeeDTO> employeeDTOList = EmployeeDTO.getDtos(FACADE.getEmployeesWithHighestSalary());
        String json = GSON.toJson(employeeDTOList);
        return Response.ok().entity(json).build();
    }


    @GET
    @Path("/name/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getEmployeeByName(@PathParam("name") String name) {
        List<EmployeeDTO> employeeDTOList = EmployeeDTO.getDtos(FACADE.getEmployeesByName(name));
        String json = GSON.toJson(employeeDTOList);
        return Response.ok().entity(json).build();
    }

}
