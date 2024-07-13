package persistance;

import org.example.demo2.studentDto;

import java.sql.Connection;

public interface StudentData {
    studentDto getStudent(String studentId, Connection connection);
    studentDto saveStudent(studentDto studentDto, Connection connection);
    studentDto deleteStudent(String stundetId , Connection connection);
    studentDto updateStudent(String sudentId, studentDto student,Connection connection);
}
