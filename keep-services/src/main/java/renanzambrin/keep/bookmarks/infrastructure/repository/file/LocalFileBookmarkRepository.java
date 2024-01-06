package renanzambrin.keep.bookmarks.infrastructure.repository.file;

import java.util.UUID;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import renanzambrin.keep.bookmarks.domain.entity.Bookmark;
import renanzambrin.keep.bookmarks.domain.repository.BookmarkRepository;
import renanzambrin.keep.shared.annotation.LocalFile;

@LocalFile
@Repository
public class LocalFileBookmarkRepository extends AbstractLocalFileRepository<UUID, Bookmark> implements BookmarkRepository {

    @Override
    public Mono<Bookmark> persist(final Bookmark bookmark) {
        return Mono.fromCallable(() -> add(bookmark.id(), bookmark));
    }

    @Override
    public Mono<Boolean> remove(Bookmark category) {
        return Mono.just(deleteByKey(category.id()));
    }

    @Override
    public Flux<Bookmark> findAll() {
        return Flux.fromIterable(get().values());
    }

    @Override
    public Mono<Bookmark> findById(UUID id) {
        return Mono.justOrEmpty(get().get(id));
    }

}