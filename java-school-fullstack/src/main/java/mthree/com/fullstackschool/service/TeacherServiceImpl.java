package mthree.com.fullstackschool.service;

import mthree.com.fullstackschool.dao.TeacherDao;
import mthree.com.fullstackschool.model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherServiceInterface {

    //YOUR CODE STARTS HERE
    private final TeacherDao teacherDao;

    @Autowired
    public TeacherServiceImpl(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }
    //YOUR CODE ENDS HERE

    public List<Teacher> getAllTeachers() {
        //YOUR CODE STARTS HERE
        return teacherDao.getAllTeachers();
        //YOUR CODE ENDS HERE
    }

    public Teacher getTeacherById(int id) {
        //YOUR CODE STARTS HERE
        try {
            return teacherDao.findTeacherById(id);
        } catch (DataAccessException ex) {
            Teacher t = new Teacher();
            t.setTeacherId(id);
            t.setTeacherFName("Teacher Not Found");
            t.setTeacherLName("Teacher Not Found");
            return t;
        }
        //YOUR CODE ENDS HERE
    }

    public Teacher addNewTeacher(Teacher teacher) {
        //YOUR CODE STARTS HERE
        boolean fBlank = (teacher.getTeacherFName() == null || teacher.getTeacherFName().isBlank());
        boolean lBlank = (teacher.getTeacherLName() == null || teacher.getTeacherLName().isBlank());

        if (fBlank || lBlank) {
            if (fBlank) {
                teacher.setTeacherFName("First Name blank, teacher NOT added");
            }
            if (lBlank) {
                teacher.setTeacherLName("Last Name blank, teacher NOT added");
            }
            return teacher;
        }
        return teacherDao.createNewTeacher(teacher);
        //YOUR CODE ENDS HERE
    }

    public Teacher updateTeacherData(int id, Teacher teacher) {
        //YOUR CODE STARTS HERE
        if (id != teacher.getTeacherId()) {
            teacher.setTeacherFName("IDs do not match, teacher not updated");
            teacher.setTeacherLName("IDs do not match, teacher not updated");
            return teacher;
        }
        teacherDao.updateTeacher(teacher);
        return getTeacherById(id);
        //YOUR CODE ENDS HERE
    }

    public void deleteTeacherById(int id) {
        //YOUR CODE STARTS HERE
        teacherDao.deleteTeacher(id);
        //YOUR CODE ENDS HERE
    }
}
