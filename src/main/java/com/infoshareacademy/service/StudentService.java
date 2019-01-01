package com.infoshareacademy.service;

import com.infoshareacademy.dao.ComputerDao;
import com.infoshareacademy.dao.StudentDao;
import com.infoshareacademy.model.Computer;
import com.infoshareacademy.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Path("/api/")
public class StudentService {

    private Logger LOG = LoggerFactory.getLogger(StudentService.class);

    @Context
    private UriInfo uriInfo;

    @Inject
    StudentDao studentDao;

    @Inject
    ComputerDao computerDao;

    public StudentService() {
    }

    @GET
    @Path("/students")
    @Produces(MediaType.TEXT_PLAIN)
    public Response studentsList() {
        String allStudents = new String();
        List<Student> list = studentDao.findAll();

        LOG.info("Found {} students", list.size());

        for (Student s : list) {
            allStudents += (s.toString() + "\n");
        }

        if (list.isEmpty()) {
            return Response.noContent().build();
        }

        return Response.ok(allStudents).build();
    }

    @GET
    @Path("/students/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response sortedStudentsList(@PathParam("name") String n) {
        String allStudents = new String();
        List<Student> list = studentDao.sortByName(n);

        LOG.info("Found {} students", list.size());

        for (Student s : list) {
            allStudents += (s.toString() + "\n");
        }

        if (list.isEmpty()) {
            return Response.noContent().build();
        }

        return Response.ok(allStudents).build();
    }

    @POST
    @Path("/computers")
    @Produces (MediaType.APPLICATION_JSON)
    public Response addComputer(Computer computer){

        computerDao.save(computer);

        String allComputers = new String();
        List<Computer> list = computerDao.findAll();

        LOG.info("Found {} computers", list.size());

        for (Computer c : list) {
            allComputers += (c.toString() + "\n");
        }

        if (list.isEmpty()) {
            return Response.noContent().build();
        }

        return Response.ok(allComputers).build();
    }
    @DELETE
    @Path("/computers/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public  Response deleteComputer(@QueryParam("id") Long id){
            if (computerDao.findById(id)==null){
            LOG.warn ("Computer with id: {} not found", id);
            return Response.status(404).build();
        }
        computerDao.delete(id);
        LOG.info("Successfully removed computer with id {}", id);
        return Response.status(200).build();
    }
    }


