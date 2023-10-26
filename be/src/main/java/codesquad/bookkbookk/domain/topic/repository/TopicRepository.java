package codesquad.bookkbookk.domain.topic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import codesquad.bookkbookk.domain.topic.data.entity.Topic;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    List<Topic> findByChapterId(Long chapterId);

}
