package com.example.metaperspective.repositories;

import com.example.metaperspective.model.Class;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassRepo extends JpaRepository<Class,Integer> {
    public List<Class> findBySchoolId(int schoolId);
}
