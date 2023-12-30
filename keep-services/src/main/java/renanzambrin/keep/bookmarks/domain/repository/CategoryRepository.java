package renanzambrin.keep.bookmarks.domain.repository;

import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import renanzambrin.keep.bookmarks.domain.entity.Category;

public interface CategoryRepository {

    Mono<Category> persist(Category category);

    Mono<Category> findById(UUID id);

    Flux<Category> findAll();

    Mono<Boolean> remove(Category category);

}