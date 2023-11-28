package codesquad.bookkbookk.domain.book.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.bookkbookk.common.resolver.MemberId;
import codesquad.bookkbookk.domain.book.data.dto.CreateBookRequest;
import codesquad.bookkbookk.domain.book.data.dto.CreateBookResponse;
import codesquad.bookkbookk.domain.book.data.dto.UpdateBookStatusRequest;
import codesquad.bookkbookk.domain.book.data.dto.UpdateBookStatusResponse;
import codesquad.bookkbookk.domain.book.service.BookService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<CreateBookResponse> createBook(@MemberId Long memberId,
                                                         @RequestBody CreateBookRequest request) {
        CreateBookResponse response = bookService.createBook(memberId, request);

        return ResponseEntity.ok()
                .body(response);
    }

    @PatchMapping("/{bookId}")
    public ResponseEntity<UpdateBookStatusResponse> updateBookStatus(@PathVariable Long bookId,
                                                                     @RequestBody UpdateBookStatusRequest request) {
        UpdateBookStatusResponse response = bookService.updateBookStatus(bookId, request);

        return ResponseEntity.ok()
                .body(response);
    }

}
