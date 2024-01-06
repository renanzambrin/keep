package renanzambrin.keep.bookmarks.infrastructure.repository.file;

import java.util.UUID;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import renanzambrin.keep.bookmarks.domain.entity.Category;
import renanzambrin.keep.bookmarks.domain.repository.CategoryRepository;
import renanzambrin.keep.shared.annotation.LocalFile;

@LocalFile
@Repository
public class LocalFileCategoryRepository extends AbstractLocalFileRepository<UUID, Category> implements CategoryRepository {

    @Override
    public Mono<Category> persist(final Category category) {
        return Mono.fromCallable(() -> add(category.id(), category));
    }

    @Override
    public Mono<Boolean> remove(Category category) {
        return Mono.just(deleteByKey(category.id()));
    }

    @Override
    public Flux<Category> findAll() {
        return Flux.fromIterable(get().values());
    }

    @Override
    public Mono<Category> findById(UUID id) {
        return Mono.justOrEmpty(get().get(id));
    }

}