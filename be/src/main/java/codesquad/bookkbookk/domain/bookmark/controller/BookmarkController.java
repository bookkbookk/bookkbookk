package codesquad.bookkbookk.domain.bookmark.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.bookkbookk.common.resolver.MemberId;
import codesquad.bookkbookk.domain.bookmark.data.dto.CreateBookmarkReactionRequest;
import codesquad.bookkbookk.domain.bookmark.data.dto.CreateBookmarkRequest;
import codesquad.bookkbookk.domain.bookmark.data.dto.UpdateBookmarkRequest;
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

    @PatchMapping("/{bookmarkId}")
    public ResponseEntity<Void> updateBookmark(@MemberId Long memberId, @PathVariable Long bookmarkId,
                                               @RequestBody UpdateBookmarkRequest updateBookmarkRequest) {
        bookmarkService.updateBookmark(memberId, bookmarkId, updateBookmarkRequest);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{bookmarkId}")
    public ResponseEntity<Void> deleteBookmark(@MemberId Long memberId, @PathVariable Long bookmarkId) {
        bookmarkService.deleteBookmark(memberId, bookmarkId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{bookmarkId}/reactions")
    public ResponseEntity<Void> createBookmarkReaction(@MemberId Long memberId, @PathVariable Long bookmarkId,
                                                       @RequestBody CreateBookmarkReactionRequest request) {
        bookmarkService.createBookmarkReaction(memberId, bookmarkId, request);

        return ResponseEntity.ok().build();
    }

}
