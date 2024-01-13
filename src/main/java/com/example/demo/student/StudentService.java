package com.example.demo.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    public boolean isValid(Student student){
        return !(student.getEmail()==null&&student.getName()==null&&student.getId()==0);
    }

    public ResponseEntity<String> addNewStudent(@RequestBody Student student) {
        if (isValid(student)){
            studentRepository.save(student);
        }
        return new ResponseEntity<>("There are some null elements!", HttpStatus.BAD_REQUEST);
    }

    public void deleteStudent(Long studentid) {
        boolean exists = studentRepository.existsById(studentid);
        if(!exists){
            throw new IllegalStateException("student with this id, isn`t here");
        }
        studentRepository.deleteById(studentid);

    }
    @Transactional
    public void updateStudent(Long studentid,String name, String email){
        Student student = studentRepository.findById(studentid).orElseThrow(()->new IllegalStateException(
                "student with id " + studentid + " does not exist"));
        if(name != null && !name.isEmpty() && !Objects.equals(student.getName(),name)){
            student.setName(name);
        }
        if(email!=null&&!email.isEmpty()&&!Objects.equals(student.getEmail(),email)){
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
            if(studentOptional.isPresent()){
                throw new IllegalStateException("email taken");
            }
            student.setEmail(email);
        }
    }

    public List<Student> getStudent() {
        return studentRepository.findAll();
    }
}
