package renanzambrin.keep.bookmarks.domain.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import renanzambrin.keep.bookmarks.domain.entity.Bookmark;
import renanzambrin.keep.bookmarks.domain.repository.BookmarkRepository;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;

    public Mono<Bookmark> create(Bookmark request) {
        final Bookmark bookmark = Bookmark.builder()
                .id(UUID.randomUUID())
                .url(request.url())
                .name(request.name())
                .description(request.description())
                .build();
        return bookmarkRepository.persist(bookmark);
    }

    public Flux<Bookmark> findAll() {
        return bookmarkRepository.findAll();
    }

    public Mono<Bookmark> findById(UUID id) {
        return bookmarkRepository.findById(id);
    }

    public Mono<Bookmark> update(UUID id, Bookmark bookmark) {
        return bookmarkRepository.findById(id)
                .flatMap(existingBookmark -> {
                    final Bookmark updatedBookmark = Bookmark.builder()
                            .id(existingBookmark.id())
                            .url(bookmark.url())
                            .name(bookmark.name())
                            .description(bookmark.description())
                            .build();
                    return bookmarkRepository.persist(updatedBookmark);
                });
    }

    public Mono<Void> delete(UUID id) {
        return bookmarkRepository.findById(id)
                .flatMap(bookmarkRepository::remove)
                .then();
    }

}