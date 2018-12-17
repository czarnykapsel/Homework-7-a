package com.infoshareacademy.web;


import com.infoshareacademy.dao.CourseDao;
import com.infoshareacademy.dao.TeacherDao;
import com.infoshareacademy.model.Course;
import com.infoshareacademy.model.Teacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Transactional
@WebServlet(urlPatterns = "/teacher")
public class TeacherServlet extends HttpServlet {

    private Logger LOG = LoggerFactory.getLogger(StudentServlet.class);

    @Inject
    private CourseDao courseDao;

    @Inject
    private TeacherDao teacherDao;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        final String action = req.getParameter("action");
        LOG.info("Requested action: {}", action);

        if (action == null || action.isEmpty()) {
            resp.getWriter().write("Empty action parameter.");
            return;
        }

        if (action.equals("findAll")) {
            findAll(req, resp);
        } else if (action.equals("add")) {
            addTeacher(req, resp);
        } else if (action.equals("delete")) {
            deleteTeacher(req, resp);
        } else if (action.equals("update")) {
            updateTeacher(req, resp);
        } else {
            resp.getWriter().write("Unknown action.");
        }

    }

    private void updateTeacher(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        final String pesel = req.getParameter("pesel");
        LOG.info("Updating teacher with pesel = {}", pesel);

        final Teacher existingTeacher = teacherDao.findById(pesel);
        if (existingTeacher == null) {
            LOG.info("No teacher found for pesel = {}, nothing to be updated", pesel);
        } else {
            existingTeacher.setName(req.getParameter("name"));
            existingTeacher.setSurname(req.getParameter("surname"));

            String courseIdStr = req.getParameter("courseid");
            Long courseId = Long.valueOf(courseIdStr);
            Course course = courseDao.findById(courseId);

            if (existingTeacher.getCourses() != null) {
                existingTeacher.getCourses().add(course);
            } else {
                existingTeacher.setCourses(Arrays.asList(course));
            }

            teacherDao.update(existingTeacher);
            LOG.info("Teacher object updated: {}", existingTeacher);
        }

        findAll(req, resp);
    }

    private void addTeacher(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        final Teacher p = new Teacher();
        p.setName(req.getParameter("name"));
        p.setSurname(req.getParameter("surname"));
        p.setPesel(req.getParameter("pesel"));

        String courseIdStr = req.getParameter("courseid");
        Long courseId = Long.valueOf(courseIdStr);
        Course course = courseDao.findById(courseId);

        p.setCourses(Arrays.asList(course));

       teacherDao.save(p);
        LOG.info("Saved a new teacher object: {}", p);

        findAll(req, resp);
    }

    private void deleteTeacher(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final String pesel= req.getParameter("pesel");
        LOG.info("Removing teacher with pesel = {}", pesel);

        teacherDao.delete(pesel);

        findAll(req, resp);
    }

    private void findAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final List<Teacher> result = teacherDao.findAll();
        LOG.info("Found {} objects", result.size());
        for (Teacher p : result) {
            resp.getWriter().write(p.toString() + "\n");
        }
    }
}
