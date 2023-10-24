package codesquad.bookkbookk.domain.book.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import codesquad.bookkbookk.common.resolver.MemberId;
import codesquad.bookkbookk.domain.book.data.dto.CreateBookRequest;
import codesquad.bookkbookk.domain.book.data.dto.CreateBookResponse;
import codesquad.bookkbookk.domain.book.data.dto.ReadBookResponse;
import codesquad.bookkbookk.domain.book.service.BookService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<CreateBookResponse> createBook(@MemberId Long memberId, @RequestBody CreateBookRequest request) {
        CreateBookResponse response = bookService.createBook(memberId, request);

        return ResponseEntity.ok()
                .body(response);
    }

    @GetMapping
    public ResponseEntity<ReadBookResponse> readBooks(@MemberId Long memberId, @RequestParam Integer page,
                                                      @RequestParam Integer size) {
        ReadBookResponse response = bookService.readBooks(memberId, page, size);

        return ResponseEntity.ok()
                .body(response);
    }

}
