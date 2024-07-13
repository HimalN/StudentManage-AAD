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
    public String saveStudent(studentDto studentDto, Connection connection) {
        try {
            var ps = connection.prepareStatement(SAVE_STUDENT);
            ps.setString(1, studentDto.getId());
            ps.setString(2, studentDto.getName());
            ps.setString(3, studentDto.getEmail());
            ps.setString(4, studentDto.getCity());
            ps.setString(5, studentDto.getLevel());
            if(ps.executeUpdate() != 0 ){
                return "Student Saved";
            } else {
                return "Student not saved";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteStudent(String stundetId, Connection connection) {
        return false;
    }

    @Override
    public boolean updateStudent(String sudentId, studentDto student, Connection connection) {
        return false;
    }


}
