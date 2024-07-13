package org.example.demo2.controller;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo2.studentDto;
import org.example.demo2.util.utilProcess;
import persistance.impl.StudentDataProcess;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebServlet(urlPatterns = "")
public class studentController extends HttpServlet {
    Connection connection;

    static String SAVE_STUDENT = "INSERT INTO student VALUES(?,?,?,?,?)";
    static String UPDATE_STUDENT = "UPDATE student SET name=?, email = ?, city = ?, level = ? WHERE id = ?";
    static String DELETE_STUDENT = "DELETE from student WHERE id=?";

    @Override
    public void init() throws ServletException {
        try {
            String driver = getServletContext().getInitParameter("driver-class");
            String dbUrl = getServletContext().getInitParameter("dbURL");
            String userName = getServletContext().getInitParameter("dbUserName");
            String password = getServletContext().getInitParameter("dbPassword");
            Class.forName(driver);
            this.connection = DriverManager.getConnection(dbUrl,userName,password);

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var studentId = req.getParameter("id");
        var dataProcess = new StudentDataProcess();
        try (var writer = resp.getWriter()){
            var student = dataProcess.getStudent(studentId, connection);
            System.out.println(student);
            resp.setContentType("application/json");
            var jsonb = JsonbBuilder.create();
            jsonb.toJson(student,writer);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (!req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }


        Jsonb jsonb = JsonbBuilder.create();
        /*this binds the values to the object*/
        studentDto studentDTO = jsonb.fromJson(req.getReader(), studentDto.class);
        studentDTO.setId(utilProcess.generateId());
        System.out.println(studentDTO);

        /*Jsonb jsonb = JsonbBuilder.create();
        List<studentDto> studentDTO = jsonb.fromJson(req.getReader(), new ArrayList<studentDto>() {}.getClass().getGenericSuperclass()); *//*this binds the values to the object*//*
        System.out.println(studentDTO);*/

        try {
            var ps = connection.prepareStatement(SAVE_STUDENT);
            ps.setString(1, studentDTO.getId());
            ps.setString(2, studentDTO.getName());
            ps.setString(3, studentDTO.getEmail());
            ps.setString(4, studentDTO.getCity());
            ps.setString(5, studentDTO.getLevel());
            if(ps.executeUpdate() != 0 ){
                resp.getWriter().write("Student Saved");
                resp.sendError(HttpServletResponse.SC_CREATED);
            } else {
                resp.getWriter().write("Student Not Saved");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        /*------------------------------------------------------------*/
        /*BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        PrintWriter writer = resp.getWriter();
        reader.lines().forEach(line -> sb.append(line).append(line + "\n"));
        System.out.println(sb);
        writer.write(sb.toString());
        writer.close();*/

        /*JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        System.out.println(jsonObject.getString("name"));*/

        /*JsonReader reader = Json.createReader(req.getReader());
        JsonArray jsonArray = reader.readArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = reader.readObject();
            System.out.println(jsonObject.getString("name"));
            System.out.println(jsonObject.getString("email"));
            System.out.println(jsonObject.getString("address"));
        }*/
    }

    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (!req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }

        try{
            var ps = this.connection.prepareStatement(UPDATE_STUDENT);
            var studentId = req.getParameter("id");
            /*var updateStudent = new studentDto();*/
            Jsonb jsonb = JsonbBuilder.create();
            var updatedStudent = jsonb.fromJson(req.getReader(),studentDto.class);
            ps.setString(1,updatedStudent.getName());
            ps.setString(2,updatedStudent.getEmail());
            ps.setString(3,updatedStudent.getCity());
            ps.setString(4,updatedStudent.getLevel());
            ps.setString(5,studentId);
            if(ps.executeUpdate() != 0 ){
                resp.getWriter().write("Student Updated");
                resp.sendError(HttpServletResponse.SC_NO_CONTENT);
            } else {
                resp.getWriter().write("Error while updating");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }

        var studentId = req.getParameter("id");
        try{
            var ps = this.connection.prepareStatement(DELETE_STUDENT);
            ps.setString(1,studentId);

            if(ps.executeUpdate() != 0 ){
                resp.getWriter().write("Student Deleted");
                resp.sendError(HttpServletResponse.SC_NO_CONTENT);
            } else {
                resp.getWriter().write("Error while Deleting");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

}