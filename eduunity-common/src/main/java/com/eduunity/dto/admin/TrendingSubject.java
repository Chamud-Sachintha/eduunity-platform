package com.eduunity.dto.admin;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sh_trending_subjects")
public class TrendingSubject {

    @Id
    @GeneratedValue
    private int id;
    private String subjectName;
    private String subjectImage;
    private String subjectDescription;
}
