import domain.Nota;
import domain.Student;

import domain.Tema;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


public class IntegrationTest {

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
    public void tcAddStudentValidGroup() {
        Student student = new Student("1", "Dori Poppi", 935, "dorina@ubbcluj.ro");
        assertNull(service.addStudent(student));
    }

    @Test
    public void tcAddAssignmentValidUnique(){
        assertNull(service.addTema(new Tema("1", "bafta", 1, 1)));
    }

    @Test
    public void tcAddGradeInvalidGrade(){
        assertThrows(ValidationException.class, ()->service.addNota(new Nota("1","","",9.0,
                LocalDate.MAX),"good"));
    }

    @Test
    public void testAll(){
        service.addStudent(new Student("1", "Dori Poppi", 935, "dorina@ubbcluj.ro"));
        service.addTema(new Tema("1", "bafta", 1, 1));
        service.addNota(new Nota("1","1","1",9.0,
                LocalDate.now()),"good");
//        assertEquals(9.0, service.addNota(new Nota("1","1","1",9.0,
//                LocalDate.now()),"good"));
    }
}
