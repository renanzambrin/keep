package renanzambrin.keep.bookmarks.application;

import java.util.UUID;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import renanzambrin.keep.bookmarks.domain.entity.Category;
import renanzambrin.keep.bookmarks.domain.service.CategoryService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public Mono<Category> create(final NewCategoryRequestDTO request) {
        return categoryService.create(request.toCategory());
    }

    @GetMapping
    public Flux<Category> getAll() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Category> getById(@PathVariable("id") UUID id) {
        return categoryService.findById(id);
    }

    @DeleteMapping("/{id}")
    public Mono<Boolean> deleteCategory(@PathVariable("id") UUID id) {
        return categoryService.delete(id);
    }

}

@Builder
record NewCategoryRequestDTO(String name) {

    public Category toCategory() {
        return Category.builder()
                .name(this.name())
                .build();
    }

}