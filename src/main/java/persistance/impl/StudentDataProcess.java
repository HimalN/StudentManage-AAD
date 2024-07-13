package persistance.impl;

import org.example.demo2.studentDto;
import persistance.StudentData;

import java.sql.Connection;
import java.sql.SQLException;

public class StudentDataProcess implements StudentData {
    static String SAVE_STUDENT = "INSERT INTO student VALUES(?,?,?,?,?)";
    static String GET_STUDENT = "SELECT * FROM student WHERE id= ?";
    static String UPDATE_STUDENT = "UPDATE student SET name=?, email = ?, city = ?, level = ? WHERE id = ?";
    static String DELETE_STUDENT = "DELETE from student WHERE id=?";

    @Override
    public studentDto getStudent(String studentId, Connection connection) {
        studentDto studentDto = new studentDto();
        try{
            var ps = connection.prepareStatement(GET_STUDENT);
            ps.setString(1, studentId);
            var resultSet = ps.executeQuery();
            while (resultSet.next()){
                studentDto.setId(resultSet.getString("id"));
                studentDto.setName(resultSet.getString("name"));
                studentDto.setEmail(resultSet.getString("email"));
                studentDto.setCity(resultSet.getString("city"));
                studentDto.setLevel(resultSet.getString("level"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return studentDto;
    }

    @Override
    public studentDto saveStudent(studentDto studentDto, Connection connection) {
        return null;
    }

    @Override
    public studentDto deleteStudent(String stundetId, Connection connection) {
        return null;
    }

    @Override
    public studentDto updateStudent(String sudentId, studentDto student, Connection connection) {
        return null;
    }
}
