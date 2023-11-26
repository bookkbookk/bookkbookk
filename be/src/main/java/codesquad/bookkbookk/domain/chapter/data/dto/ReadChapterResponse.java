package codesquad.bookkbookk.domain.chapter.data.dto;

import java.util.List;
import java.util.stream.Collectors;

import codesquad.bookkbookk.domain.bookmark.data.entity.Bookmark;
import codesquad.bookkbookk.domain.chapter.data.entity.Chapter;
import codesquad.bookkbookk.domain.topic.data.entity.Topic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadChapterResponse {

    private final Long chapterId;
    private final String title;
    private final Integer statusId;
    private final List<ReadChapterTopicResponse> topics;

    @AllArgsConstructor
    @Getter
    private static class ReadChapterTopicResponse {

        private final Long topicId;
        private final String title;
        private final ReadChapterBookmarkResponse recentBookmark;

        private static List<ReadChapterTopicResponse> from(List<Topic> topics) {
            return topics.stream()
                    .map(ReadChapterTopicResponse::from)
                    .collect(Collectors.toUnmodifiableList());
        }

        private static ReadChapterTopicResponse from(Topic topic) {
            List<Bookmark> bookmarks = topic.getBookmarks();

            if (bookmarks.isEmpty()) {
                return new  ReadChapterTopicResponse(topic.getId(), topic.getTitle(), null);
            }
            return new ReadChapterTopicResponse(topic.getId(), topic.getTitle(),
                    ReadChapterBookmarkResponse.from(bookmarks.get(bookmarks.size() - 1)));
        }

    }

    @AllArgsConstructor
    @Getter
    private static class ReadChapterBookmarkResponse {

        private final String authorProfileImgUrl;
        private final String content;

        private static ReadChapterBookmarkResponse from(Bookmark bookmark) {
            return new ReadChapterBookmarkResponse(bookmark.getWriter().getProfileImgUrl(), bookmark.getContent());
        }

    }

    @Builder
    private ReadChapterResponse(Long chapterId, String title, Integer statusId, List<ReadChapterTopicResponse> topics) {
        this.chapterId = chapterId;
        this.title = title;
        this.statusId = statusId;
        this.topics = topics;
    }

    public static List<ReadChapterResponse> from(List<Chapter> chapters) {
        return chapters.stream()
                .map(ReadChapterResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    private static ReadChapterResponse from(Chapter chapter) {
        return ReadChapterResponse.builder()
                .chapterId(chapter.getId())
                .title(chapter.getTitle())
                .statusId(chapter.getStatus().getId())
                .topics(ReadChapterTopicResponse.from(chapter.getTopics()))
                .build();
    }

}
