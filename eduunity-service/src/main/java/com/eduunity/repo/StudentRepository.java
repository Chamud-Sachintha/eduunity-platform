package com.eduunity.repo;

import com.eduunity.dto.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    Optional<Student> findByNicNumber(String nicNumber);

    Optional<Student> findByEmail(String email);
}
