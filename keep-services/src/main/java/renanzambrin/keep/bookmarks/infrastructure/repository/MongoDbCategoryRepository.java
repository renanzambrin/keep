package renanzambrin.keep.bookmarks.infrastructure.repository;

import java.util.UUID;
import com.mongodb.client.result.DeleteResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import renanzambrin.keep.bookmarks.domain.entity.Category;
import renanzambrin.keep.bookmarks.domain.repository.CategoryRepository;
import renanzambrin.keep.shared.annotation.MongoDB;

@MongoDB
@Repository
@RequiredArgsConstructor
public class MongoDbCategoryRepository implements CategoryRepository {

    private final ReactiveMongoTemplate template;

    @Override
    public Mono<Category> persist(Category category) {
        return template.save(category);
    }

    @Override
    public Mono<Category> findById(UUID id) {
        return template.findById(id, Category.class);
    }

    @Override
    public Flux<Category> findAll() {
        return template.findAll(Category.class);
    }

    @Override
    public Mono<Boolean> remove(Category category) {
        return template.remove(category)
                .map(DeleteResult::wasAcknowledged);
    }

}