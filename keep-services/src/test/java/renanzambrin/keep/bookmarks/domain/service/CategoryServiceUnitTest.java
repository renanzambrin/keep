package renanzambrin.keep.bookmarks.domain.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import renanzambrin.keep.bookmarks.domain.entity.Category;
import renanzambrin.keep.bookmarks.domain.repository.CategoryRepository;
import renanzambrin.keep.bookmarks.domain.resolver.CategoryResolver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, CategoryResolver.class})
class CategoryServiceUnitTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void givenCategoryWithNoUUID_whenCreate_thenCreateNewCategory(Category category) {
        when(categoryRepository.persist(any(Category.class))).thenReturn(Mono.just(category));
        Category result = categoryService.create(category).block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(category.name(), result.name());
    }

    @Test
    void givenCategory_whenFindAll_thenGetAllCategories(Category category) {
        when(categoryRepository.findAll()).thenReturn(Flux.just(category));
        Category result = categoryService.findAll().blockFirst();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(category.name(), result.name());
    }

    @Test
    void givenCategory_whenFindById_thenGetCategory(Category category) {
        when(categoryRepository.findById(any())).thenReturn(Mono.just(category));
        Category result = categoryService.findById(category.id()).block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(category.id(), result.id());
        Assertions.assertEquals(category.name(), result.name());
    }

    @Test
    void givenCategory_whenDelete_thenDeleteCategory(Category category) {
        when(categoryRepository.findById(any())).thenReturn(Mono.just(category));
        when(categoryRepository.remove(any(Category.class))).thenReturn(Mono.just(true));
        Boolean result = categoryService.delete(category.id()).block();
        Assertions.assertTrue(result);
    }

}