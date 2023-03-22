import domain.Student;

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

import static org.junit.jupiter.api.Assertions.*;


public class ServiceTest {

    private Service service;

    @BeforeEach
    public void setUp(){
        String filenameStudents = "F:\\An3 Sem2\\VVSS\\Lab\\LabAssiAsseProjectV02\\VVSS\\LabAssiAsseProjectV02\\src\\test\\fisiere\\students.xml";
        String filenameAssignments = "F:\\An3 Sem2\\VVSS\\Lab\\LabAssiAsseProjectV02\\VVSS\\LabAssiAsseProjectV02\\src\\test\\fisiere\\assignments.xml";
        String filenameGrades = "F:\\An3 Sem2\\VVSS\\Lab\\LabAssiAsseProjectV02\\VVSS\\LabAssiAsseProjectV02\\src\\test\\fisiere\\grades.xml";

        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();

        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudents);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameAssignments);
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameGrades);

        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
    }

    @Test
    public void TCAddStudentValidId(){
        Student student = new Student("1","Mary Poppins", 935, "mary@yahoo.com");
        assertNotNull(service.addStudent(student));
        assertEquals(service.findStudent("1").getNume(), "Mary Poppins");
    }

    @Test
    public void TCAddStudentInvalidId(){
        Student student = new Student("","Mary Poppins", 935, "mary@yahoo.com");
        assertThrows(ValidationException.class, () -> service.addStudent(student));
    }

}
