package com.eduunity.repo;

import com.eduunity.dto.NewModule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GeneratedModuleRepository extends JpaRepository<NewModule, Integer> {

    public List<NewModule> findAllByStudentId(int studentId);
}
