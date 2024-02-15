package com.example.metaperspective.repositories;

import com.example.metaperspective.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student,Integer> {
}
