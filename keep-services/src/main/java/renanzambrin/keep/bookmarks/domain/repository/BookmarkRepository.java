package renanzambrin.keep.bookmarks.domain.repository;

import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import renanzambrin.keep.bookmarks.domain.entity.Bookmark;

public interface BookmarkRepository {

    Mono<Bookmark> persist(Bookmark bookmark);

    Mono<Boolean> remove(Bookmark bookmark);

    Flux<Bookmark> findAll();

    Mono<Bookmark> findById(UUID id);

}