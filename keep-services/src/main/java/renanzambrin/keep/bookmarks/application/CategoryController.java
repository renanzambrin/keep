package renanzambrin.keep.bookmarks.application;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

}

@Builder
record NewCategoryRequestDTO(String name) {

    public Category toCategory() {
        return Category.builder()
                .name(this.name())
                .build();
    }

}