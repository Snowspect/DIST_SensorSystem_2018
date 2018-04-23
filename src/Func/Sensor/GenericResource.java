/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Func.Sensor;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author alexandercarlsen
 */
@Path("login")
public class GenericResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }

  @Path("authentication") 
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Bruger getJson() {
    System.out.println("getJson() blev kaldt fra "+context);
    return new Bruger();
  }
  
  public static class Bruger {
    public String userName = "kage123";
    public String passWord = "kage456";
  }
}
