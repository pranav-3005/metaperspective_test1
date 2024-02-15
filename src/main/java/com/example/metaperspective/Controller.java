package com.example.metaperspective;

import com.example.metaperspective.model.Class;
import com.example.metaperspective.model.School;
import com.example.metaperspective.model.Student;
import com.example.metaperspective.repositories.ClassRepo;
import com.example.metaperspective.repositories.SchoolRepo;
import com.example.metaperspective.repositories.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    ClassRepo classRepo;

    @Autowired
    SchoolRepo schoolRepo;

    @Autowired
    StudentRepo studentRepo;

    @PostMapping("/addSchool")
    public String addSchool(@RequestBody School school)
    {
        schoolRepo.save(school);
        return "School have been created";
    }

    @PostMapping("/addClass")
    public String addClass(@RequestBody Class aClass)
    {
        classRepo.save(aClass);
        return "class have been added";
    }

    @PostMapping("/addStudent")
    public String addStudent(@RequestBody Student student, @RequestParam(name = "classId") int classId)
    {
        Class aClass=classRepo.getById(classId);
        student.setAClass(aClass);

        aClass.getStudentList().add(student);
        classRepo.save(aClass);
        studentRepo.save(student);

        return "student added";
    }

    //get

    @GetMapping("/getSchool")
    public List<School> getSchools()
    {
        List<School> schoolList=schoolRepo.findAll();

        return schoolList;
    }

    @GetMapping("/getClass")
    public Class getClass(@RequestParam int classId)
    {
        Class aClass= classRepo.getById(classId);

        List<Student> studentList=new ArrayList<>();

        Class bClass=new Class();
        bClass.setId(aClass.getId());
        bClass.setStatus(aClass.getStatus());

        for(Student student:aClass.getStudentList())
        {
            Student student1=new Student();

            student1.setStudentId(student.getStudentId());
            student1.setAssignment(student.getAssignment());
            student1.setAClass(null);

            studentList.add(student1);
        }

        bClass.setStudentList(studentList);

        return bClass;
    }

    //update
    @PutMapping("/updateSchool")
    public String updateSchool(@RequestBody School school)
    {
        schoolRepo.save(school);

        return "school is updated (name/since)";
    }

    //delete


    @DeleteMapping("/deleteStudent")
    public String deleteStudent(@RequestParam int studentId)
    {

        Class aClass=studentRepo.getById(studentId).getAClass();
        studentRepo.deleteById(studentId);

        for(Student student:aClass.getStudentList())
        {
            if(student.getStudentId()==studentId)
                aClass.getStudentList().remove(student);
        }

        classRepo.save(aClass);
        return "Student have been removed";
    }

    @DeleteMapping("/deleteClass")
    public String deleteClass(@RequestParam int classid)
    {
        Class aClass= classRepo.getById(classid);

        studentRepo.deleteAll(aClass.getStudentList());

        classRepo.deleteById(classid);

        return "class along its students profile are deleted";
    }

    @DeleteMapping("/deleteSchool")
    public String deleteSchool(@RequestParam int schoolId)
    {
        List<Class> classList= classRepo.findBySchoolId(schoolId);

        for(Class aClass:classList)
        {
            studentRepo.deleteAll(aClass.getStudentList());
        }

        classRepo.deleteAll(classList);

        schoolRepo.deleteById(schoolId);

        return "school is deleted";
    }

}
