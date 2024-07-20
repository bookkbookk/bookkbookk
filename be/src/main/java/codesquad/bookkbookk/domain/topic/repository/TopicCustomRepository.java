package codesquad.bookkbookk.domain.topic.repository;

import java.util.List;

import codesquad.bookkbookk.domain.topic.data.entity.Topic;

public interface TopicCustomRepository {

    List<Topic> saveAllInBulk(List<Topic> topics);

}
