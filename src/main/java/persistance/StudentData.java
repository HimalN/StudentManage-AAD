package persistance;

import org.example.demo2.studentDto;

import java.sql.Connection;

public interface StudentData {
    studentDto getStudent(String studentId, Connection connection);
    String saveStudent(studentDto studentDto, Connection connection);
    boolean deleteStudent(String stundetId , Connection connection);
    boolean updateStudent(String sudentId, studentDto student,Connection connection);
}
