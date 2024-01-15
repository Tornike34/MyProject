package com.example.demo.student;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;


    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public ResponseEntity<String> addNewStudent(Student student) {
        if (ObjectsValidator.validateNotNull(student)) {
            studentRepository.save(student);
            return new ResponseEntity<>("Student added successfully!", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("There are some null elements!", HttpStatus.BAD_REQUEST);
    }

    public void deleteStudent(Long studentid) {
        boolean exists = studentRepository.existsById(studentid);
        if (!exists) {
            throw new IllegalStateException("student with this id, isn`t here");
        }
        studentRepository.deleteById(studentid);

    }

    @Transactional
    public ResponseEntity<String> updateStudent(Long studentid, String name, String email) {
        Student student = studentRepository.findById(studentid).orElseThrow(() -> new IllegalStateException("student with id " + studentid + " does not exist"));
        if (ObjectsValidator.validateNotNull(student)) {
            if (!Objects.equals((student.getName()), name)) {
                student.setName(name);
            }
            if (!Objects.equals(student.getEmail(), email)) {
                Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
                if (studentOptional.isPresent()) {
                    throw new IllegalStateException("email taken");
                }
                student.setEmail(email);
            }
        }
        return new ResponseEntity<>("There is null attribute!", HttpStatus.BAD_REQUEST);
    }

    public List<Student> getStudent() {
        return studentRepository.findAll();
    }
}
