package codesquad.bookkbookk.domain.chapter.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import codesquad.bookkbookk.common.error.exception.BookNotFoundException;
import codesquad.bookkbookk.domain.book.data.entity.Book;
import codesquad.bookkbookk.domain.book.repository.BookRepository;
import codesquad.bookkbookk.domain.chapter.data.dto.CreateChapterRequest;
import codesquad.bookkbookk.domain.chapter.data.dto.CreateChapterResponse;
import codesquad.bookkbookk.domain.chapter.data.entity.Chapter;
import codesquad.bookkbookk.domain.chapter.repository.ChapterRepository;
import codesquad.bookkbookk.domain.topic.data.entity.Topic;
import codesquad.bookkbookk.domain.topic.repository.TopicRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChapterService {

    private final ChapterRepository chapterRepository;
    private final TopicRepository topicRepository;
    private final BookRepository bookRepository;

    public CreateChapterResponse createChapter(CreateChapterRequest request) {
        Book book = bookRepository.findById(request.getBookId()).orElseThrow(BookNotFoundException::new);
        List<CreateChapterRequest.ChapterDataDTO> dataList = request.toEntities(book);

        List<Chapter> chapterList = dataList.stream()
                .map(CreateChapterRequest.ChapterDataDTO::getChapter)
                .collect(Collectors.toUnmodifiableList());
        chapterRepository.saveAll(chapterList);

        List<Topic> topicList = dataList.stream()
                .flatMap(dto -> dto.getTopicList().stream())
                .collect(Collectors.toUnmodifiableList());
        topicRepository.saveAll(topicList);

        return new CreateChapterResponse(chapterList.stream()
                .map(Chapter::getId)
                .collect(Collectors.toUnmodifiableList()));
    }

}
