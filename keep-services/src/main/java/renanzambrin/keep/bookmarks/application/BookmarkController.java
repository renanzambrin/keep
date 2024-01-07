package renanzambrin.keep.bookmarks.application;

import java.util.UUID;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import renanzambrin.keep.bookmarks.domain.entity.Bookmark;
import renanzambrin.keep.bookmarks.domain.service.BookmarkService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookmarks")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping
    public Mono<Bookmark> create(@RequestBody BookmarkDTO bookmark) {
        return bookmarkService.create(bookmark.toBookmark());
    }

    @GetMapping
    public Flux<Bookmark> getAll() {
        return bookmarkService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Bookmark> getById(@PathVariable UUID id) {
        return bookmarkService.findById(id);
    }

    @PutMapping("/{id}")
    public Mono<Bookmark> update(@PathVariable UUID id, @RequestBody Bookmark bookmark) {
        return bookmarkService.update(id, bookmark);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable UUID id) {
        return bookmarkService.delete(id);
    }

}

@Builder
record BookmarkDTO(String url, String name, String description) {

    public Bookmark toBookmark() {
        return Bookmark.builder()
                .name(name)
                .url(url)
                .description(description)
                .build();
    }

}