package com.eduunity.repo;

import com.eduunity.dto.TopicContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenerateTopicContentRepository extends JpaRepository<TopicContent, Integer> {

    public Optional<TopicContent> findByModuleIdAndTopicName(Integer moduleId, String topicName);
}
