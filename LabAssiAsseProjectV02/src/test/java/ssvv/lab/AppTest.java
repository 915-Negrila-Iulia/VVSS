package ssvv.lab;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import repository.StudentFileRepository;

public class AppTest extends TestCase {
    public AppTest(String testName){
        super(testName);
    }

    public static Test suite(){
        return new TestSuite(AppTest.class);
    }

    public void testApp(){
        assertTrue(true);
    }

}
