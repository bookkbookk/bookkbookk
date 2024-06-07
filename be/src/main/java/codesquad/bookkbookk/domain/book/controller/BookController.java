package codesquad.bookkbookk.domain.book.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import codesquad.bookkbookk.common.resolver.MemberId;
import codesquad.bookkbookk.domain.book.data.dto.CreateBookRequest;
import codesquad.bookkbookk.domain.book.data.dto.CreateBookResponse;
import codesquad.bookkbookk.domain.book.data.dto.UpdateBookStatusRequest;
import codesquad.bookkbookk.domain.book.data.dto.UpdateBookStatusResponse;
import codesquad.bookkbookk.domain.book.service.BookService;
import codesquad.bookkbookk.domain.bookmark.data.dto.ReadBookmarkResponse;
import codesquad.bookkbookk.domain.bookmark.service.BookmarkService;
import codesquad.bookkbookk.domain.chapter.data.dto.ReadChapterResponse;
import codesquad.bookkbookk.domain.chapter.service.ChapterService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;
    private final ChapterService chapterService;
    private final BookmarkService bookmarkService;

    @PostMapping
    public ResponseEntity<CreateBookResponse> createBook(@MemberId Long memberId,
                                                         @RequestBody CreateBookRequest request) {
        CreateBookResponse response = bookService.createBook(memberId, request);

        return ResponseEntity.ok()
                .body(response);
    }

    @GetMapping("/{bookId}/chapters")
    public ResponseEntity<List<ReadChapterResponse>> readChapters(@MemberId Long memberId, @PathVariable Long bookId,
                                                                  @RequestParam int statusId) {
        List<ReadChapterResponse> response = chapterService.readChapters(memberId, bookId, statusId);

        return ResponseEntity.ok()
                .body(response);
    }

    @GetMapping("/{bookId}/bookmarks")
    public ResponseEntity<List<ReadBookmarkResponse>> readBookmarks(@MemberId Long memberId, @PathVariable Long bookId,
                                                                    @RequestParam Integer startPage,
                                                                    @RequestParam Integer endPage) {
        List<ReadBookmarkResponse> response = bookmarkService.readBookmarks(memberId, bookId, startPage, endPage);

        return ResponseEntity.ok()
                .body(response);
    }

    @PatchMapping("/{bookId}")
    public ResponseEntity<UpdateBookStatusResponse> updateBookStatus(@MemberId Long memberId, @PathVariable Long bookId,
                                                                     @RequestBody UpdateBookStatusRequest request) {
        UpdateBookStatusResponse response = bookService.updateBookStatus(memberId, bookId, request);

        return ResponseEntity.ok()
                .body(response);
    }

}
