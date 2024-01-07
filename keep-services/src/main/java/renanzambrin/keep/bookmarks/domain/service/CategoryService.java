package renanzambrin.keep.bookmarks.domain.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import renanzambrin.keep.bookmarks.domain.entity.Category;
import renanzambrin.keep.bookmarks.domain.repository.CategoryRepository;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Mono<Category> create(final Category request) {
        final Category category = request.toBuilder()
                .id(UUID.randomUUID())
                .build();
        return categoryRepository.persist(category);
    }

    public Flux<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Mono<Category> update(UUID id, Category category) {
        return findById(id)
                .flatMap(existingCategory -> {
                    final Category updatedCategory = existingCategory.toBuilder()
                            .name(category.name())
                            .build();
                    return categoryRepository.persist(updatedCategory);
                });
    }

    public Mono<Category> findById(UUID id) {
        return categoryRepository.findById(id);
    }

    public Mono<Void> delete(UUID id) {
        return findById(id)
                .flatMap(categoryRepository::remove)
                .then();
    }

}