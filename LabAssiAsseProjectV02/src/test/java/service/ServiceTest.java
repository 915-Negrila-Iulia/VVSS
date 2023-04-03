package service;

import domain.Student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


public class ServiceTest {

    private Service service;

    @BeforeEach
    public void setUp(){
        String filenameStudents = "src\\test\\fisiere\\students.xml";
        String filenameAssignments = "src\\test\\fisiere\\assignments.xml";
        String filenameGrades = "src\\test\\fisiere\\grades.xml";

        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();

        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudents);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameAssignments);
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameGrades);

        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
    }

    @AfterEach
    public void tearDown() throws IOException {
        Path file = Paths.get("src\\test\\fisiere\\students.xml");
        Files.write(file, Collections.singletonList("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><inbox></inbox>"), StandardCharsets.UTF_8);
        file = Paths.get("src\\test\\fisiere\\assignments.xml");
        Files.write(file, Collections.singletonList("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><inbox></inbox>"), StandardCharsets.UTF_8);
        file = Paths.get("src\\test\\fisiere\\grades.xml");
        Files.write(file, Collections.singletonList("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><inbox></inbox>"), StandardCharsets.UTF_8);
    }

    @Test
    public void tcAddStudentValidId(){
        Student student = new Student("1","Dori Poppi", 935, "dorina@yahoo.com");
        assertNull(service.addStudent(student));
        assertEquals(service.findStudent("1").getNume(), "Dori Poppi");
    }

    @Test
    public void tcAddStudentInvalidId(){
        Student student = new Student("","Dori Poppi", 935, "dorina@yahoo.com");
        assertThrows(ValidationException.class, () -> service.addStudent(student));
    }

    @Test
    public void tcAddStudentValidEmail() {
        Student student = new Student("1", "Dori Poppi", 935, "dorina@ubbcluj.ro");
        assertNull(service.addStudent(student));
        assertEquals(service.findStudent("1").getEmail(), "dorina@ubbcluj.ro");
    }

    @Test
    public void tcAddStudentInvalidEmail() {
        Student student = new Student("1", "Dori Poppi", 935, "");
        assertThrows(ValidationException.class, () -> service.addStudent(student));
    }

    @Test
    public void tcAddStudentValidName() {
        Student student = new Student("1", "Dori Poppi", 935, "dorina@ubbcluj.ro");
        assertNull(service.addStudent(student));
        assertEquals(service.findStudent("1").getEmail(), "dorina@ubbcluj.ro");
    }

    @Test
    public void tcAddStudentInvalidName() {
        Student student = new Student("1", "Dori Poppi", 935, "");
        assertThrows(ValidationException.class, () -> service.addStudent(student));
    }

    @Test
    public void tcAddStudentValidStudentGroupBVA2() {
        List<String> ids = Stream.of("1", "2", "3", "4").collect(Collectors.toList());
        List<Integer> groups = Stream.of(0, 1, Integer.MAX_VALUE-1, Integer.MAX_VALUE).collect(Collectors.toList());
        for(int i = 0; i < 2; i++) {
            Student student = new Student(ids.get(i), "Dori Poppi", groups.get(i), "dorina@ubbcluj.ro");
            assertNull(service.addStudent(student));
        }
    }

    @Test
    public void tcAddStudentInvalidStudentGroupBVA2() {
        List<String> ids = Stream.of("1", "2").collect(Collectors.toList());
        List<Integer> groups = Stream.of(-1, Integer.MAX_VALUE+1).collect(Collectors.toList());
        for(int i = 0; i < 2; i++) {
            Student student = new Student(ids.get(i), "Dori Poppi", groups.get(i), "dorina@ubbcluj.ro");
            assertThrows(ValidationException.class, () -> service.addStudent(student));
        }
    }
}
