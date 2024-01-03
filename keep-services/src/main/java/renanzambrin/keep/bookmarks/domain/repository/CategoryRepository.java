package renanzambrin.keep.bookmarks.domain.repository;

import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import renanzambrin.keep.bookmarks.domain.entity.Category;

public interface CategoryRepository {

    Mono<Category> persist(Category category);

    Mono<Boolean> remove(Category category);

    Flux<Category> findAll();

    Mono<Category> findById(UUID id);

}