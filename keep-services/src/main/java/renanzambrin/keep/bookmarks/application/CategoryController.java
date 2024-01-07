package renanzambrin.keep.bookmarks.application;

import java.util.UUID;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public Mono<Category> create(@RequestBody CategoryDTO request) {
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

    @PutMapping("/{id}")
    public Mono<Category> update(@PathVariable("id") UUID id, @RequestBody CategoryDTO updateDTO) {
        return categoryService.update(id, updateDTO.toCategory());
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable("id") UUID id) {
        return categoryService.delete(id);
    }

}

@Builder
record CategoryDTO(String name) {

    public Category toCategory() {
        return Category.builder()
                .name(this.name())
                .build();
    }

}