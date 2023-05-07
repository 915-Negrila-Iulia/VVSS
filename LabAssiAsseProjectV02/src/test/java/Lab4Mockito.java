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

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class Lab4Mockito {

    private Service service;

    private StudentXMLRepo studentXMLRepository = mock(StudentXMLRepo.class);
    private TemaXMLRepo temaXMLRepository = mock(TemaXMLRepo.class);
    private NotaXMLRepo notaXMLRepository = mock(NotaXMLRepo.class);

    private StudentValidator studentValidator = mock(StudentValidator.class);
    private TemaValidator temaValidator = mock(TemaValidator.class);
    private NotaValidator notaValidator = mock(NotaValidator.class);

    @BeforeEach
    public void setUp() {
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
    }

    @Test
    void addStudent_allStubs() {
        Student student = new Student("1", "Dori Poppi", 935, "dorina@ubbcluj.ro");

        when(studentXMLRepository.save(any())).thenReturn(null);

        service.addStudent(student);

        verify(studentValidator).validate(any());
        verify(studentXMLRepository).save(any());
    }

    @Test
    void addStudent_noStub() throws IOException {
        StudentValidator studentValidatorReal = new StudentValidator();
        String filenameStudent = "src\\test\\fisiere\\students.xml";
        StudentXMLRepo studentXMLRepositoryReal = new StudentXMLRepo(filenameStudent);

        service = new Service(studentXMLRepositoryReal, studentValidatorReal, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
        Student student = new Student("1", "Dori Poppi", 935, "dorina@ubbcluj.ro");
        assertNull(service.addStudent(student));

        Path file = Paths.get("src\\test\\fisiere\\students.xml");
        Files.write(file, Collections.singletonList("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><inbox></inbox>"), StandardCharsets.UTF_8);
    }

    @Test
    void addStudent_repoStub() {
        StudentValidator studentValidatorReal = new StudentValidator();
        service = new Service(studentXMLRepository, studentValidatorReal, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
        Student student = new Student("1", "Dori Poppi", 935, "dorina@ubbcluj.ro");
        service.addStudent(student);

        verify(studentXMLRepository).save(any());
    }

    @Test
    void addAssignment_integration() {
        String filenameStudent = "src\\test\\fisiere\\students.xml";
        String filenameTema = "src\\test\\fisiere\\assignments.xml";

        StudentXMLRepo studentXMLRepositoryReal = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaXMLRepositoryReal = new TemaXMLRepo(filenameTema);

        StudentValidator studentValidatorReal = new StudentValidator();
        TemaValidator temaValidatorReal = new TemaValidator();

        service = new Service(studentXMLRepositoryReal, studentValidatorReal, temaXMLRepositoryReal, temaValidatorReal, notaXMLRepository, notaValidator);
    }

}
