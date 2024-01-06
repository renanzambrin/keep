package renanzambrin.keep.bookmarks.domain.service;

import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import renanzambrin.keep.bookmarks.domain.entity.Category;
import renanzambrin.keep.bookmarks.domain.repository.CategoryRepository;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Mono<Category> create(final Category request) {
        final Category category = request.toBuilder()
                .id(UUID.randomUUID())
                .build();
        return categoryRepository.persist(category);
    }

}