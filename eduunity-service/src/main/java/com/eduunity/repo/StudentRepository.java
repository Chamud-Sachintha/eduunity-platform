package com.eduunity.repo;

import com.eduunity.dto.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    Optional<Student> findByNicNumber(String nicNumber);

    public int countStudentsByStatus(int status);

    Page<Student> findStudentsByStatus(int status, Pageable pageable);

    Optional<Student> findByEmail(String email);
}
