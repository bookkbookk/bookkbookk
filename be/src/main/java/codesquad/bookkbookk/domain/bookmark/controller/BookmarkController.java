package codesquad.bookkbookk.domain.bookmark.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.bookkbookk.common.resolver.MemberId;
import codesquad.bookkbookk.domain.bookmark.data.dto.CreateBookmarkRequest;
import codesquad.bookkbookk.domain.bookmark.service.BookmarkService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping
    public ResponseEntity<Void> createBookmark(@MemberId Long memberId,
                                               @RequestBody CreateBookmarkRequest createBookmarkRequest) {
        bookmarkService.createBookmark(memberId, createBookmarkRequest);

        return ResponseEntity.ok().build();
    }

}
