package com.example.metaperspective.model;

import com.example.metaperspective.ClassStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Class {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    int schoolId;

    String status;

    @OneToMany(mappedBy = "aClass",cascade = CascadeType.ALL)
    List<Student> studentList=new ArrayList<>();
}
