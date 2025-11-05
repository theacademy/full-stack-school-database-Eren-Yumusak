package mthree.com.fullstackschool.service;

import mthree.com.fullstackschool.dao.StudentDao;
import mthree.com.fullstackschool.model.Course;
import mthree.com.fullstackschool.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentServiceInterface {

    //YOUR CODE STARTS HERE
    private final StudentDao studentDao;
    private final mthree.com.fullstackschool.dao.CourseDao courseDao;

    @Autowired
    public StudentServiceImpl(StudentDao studentDao, mthree.com.fullstackschool.dao.CourseDao courseDao) {
        this.studentDao = studentDao;
        this.courseDao = courseDao;
    }

    public StudentServiceImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
        this.courseDao = null; // not needed for this test
    }
    //YOUR CODE ENDS HERE

    public List<Student> getAllStudents() {
        //YOUR CODE STARTS HERE
        return studentDao.getAllStudents();
        //YOUR CODE ENDS HERE
    }

    public Student getStudentById(int id) {
        //YOUR CODE STARTS HERE
        try {
            return studentDao.findStudentById(id);
        } catch (DataAccessException ex) {
            Student s = new Student();
            s.setStudentId(id);
            s.setStudentFirstName("Student Not Found");
            s.setStudentLastName("Student Not Found");
            return s;
        }
        //YOUR CODE ENDS HERE
    }

    public Student addNewStudent(Student student) {
        //YOUR CODE STARTS HERE
        boolean firstBlank = (student.getStudentFirstName() == null || student.getStudentFirstName().isBlank());
        boolean lastBlank  = (student.getStudentLastName() == null  || student.getStudentLastName().isBlank());

        if (firstBlank || lastBlank) {
            if (firstBlank) {
                student.setStudentFirstName("First Name blank, student NOT added");
            }
            if (lastBlank) {
                student.setStudentLastName("Last Name blank, student NOT added");
            }
            return student;
        }
        return studentDao.createNewStudent(student);
        //YOUR CODE ENDS HERE
    }

    public Student updateStudentData(int id, Student student) {
        //YOUR CODE STARTS HERE
        if (id != student.getStudentId()) {
            student.setStudentFirstName("IDs do not match, student not updated");
            student.setStudentLastName("IDs do not match, student not updated");
            return student;
        }
        studentDao.updateStudent(student);
        return getStudentById(id);
        //YOUR CODE ENDS HERE
    }

    public void deleteStudentById(int id) {
        //YOUR CODE STARTS HERE
        studentDao.deleteStudent(id);
        //YOUR CODE ENDS HERE
    }

    public void deleteStudentFromCourse(int studentId, int courseId) {
        //YOUR CODE STARTS HERE
        Student s = getStudentById(studentId);
        Course c;
        try {
            c = courseDao.findCourseById(courseId);
        } catch (DataAccessException ex) {
            c = new Course();
            c.setCourseName("Course Not Found");
        }

        if ("Student Not Found".equals(s.getStudentFirstName())) {
            System.out.println("Student not found");
            return;
        }
        if ("Course Not Found".equals(c.getCourseName())) {
            System.out.println("Course not found");
            return;
        }

        studentDao.deleteStudentFromCourse(studentId, courseId);
        System.out.println("Student: " + studentId + " deleted from course: " + courseId);
        //YOUR CODE ENDS HERE
    }

    public void addStudentToCourse(int studentId, int courseId) {
        //YOUR CODE STARTS HERE
        Student s = getStudentById(studentId);
        Course c;
        try {
            c = courseDao.findCourseById(courseId);
        } catch (DataAccessException ex) {
            c = new Course();
            c.setCourseName("Course Not Found");
        }

        if ("Student Not Found".equals(s.getStudentFirstName())) {
            System.out.println("Student not found");
            return;
        }
        if ("Course Not Found".equals(c.getCourseName())) {
            System.out.println("Course not found");
            return;
        }

        try {
            studentDao.addStudentToCourse(studentId, courseId);
            System.out.println("Student: " + studentId + " added to course: " + courseId);
        } catch (Exception ex) {
            System.out.println("Student: " + studentId + " already enrolled in course: " + courseId);
        }
        //YOUR CODE ENDS HERE
    }
}
