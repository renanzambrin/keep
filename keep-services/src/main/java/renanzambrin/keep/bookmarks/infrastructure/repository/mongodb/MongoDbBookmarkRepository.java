package renanzambrin.keep.bookmarks.infrastructure.repository.mongodb;

import java.util.UUID;
import com.mongodb.client.result.DeleteResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import renanzambrin.keep.bookmarks.domain.entity.Bookmark;
import renanzambrin.keep.bookmarks.domain.repository.BookmarkRepository;
import renanzambrin.keep.shared.annotation.MongoDB;

@MongoDB
@Repository
@RequiredArgsConstructor
public class MongoDbBookmarkRepository implements BookmarkRepository {

    private final ReactiveMongoTemplate template;

    @Override
    public Mono<Bookmark> persist(Bookmark bookmark) {
        return template.save(bookmark);
    }

    @Override
    public Mono<Boolean> remove(Bookmark bookmark) {
        return template.remove(bookmark)
                .map(DeleteResult::wasAcknowledged);
    }

    @Override
    public Flux<Bookmark> findAll() {
        return template.findAll(Bookmark.class);
    }

    @Override
    public Mono<Bookmark> findById(UUID id) {
        return template.findById(id, Bookmark.class);
    }

}