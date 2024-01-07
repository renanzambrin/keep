package renanzambrin.keep.bookmarks.domain.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import renanzambrin.keep.bookmarks.domain.entity.Category;
import renanzambrin.keep.bookmarks.domain.repository.CategoryRepository;
import renanzambrin.keep.bookmarks.domain.resolver.CategoryResolver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, CategoryResolver.class})
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryService categoryService;

    @Captor
    private ArgumentCaptor<Category> captor;

    @Test
    void givenCategoryWithNoUUID_whenCreate_thenCreateNewCategory() {
        Mockito.when(categoryRepository.persist(ArgumentMatchers.any())).thenReturn(Mono.empty());

        final Category category = Category.builder()
                .name("Test Category")
                .build();

        categoryService.create(category).block();

        Mockito.verify(categoryRepository).persist(captor.capture());
        Category result = captor.getValue();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(category.name(), result.name());
        Assertions.assertNull(category.id());
        Assertions.assertNotNull(result.id());
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
        Assertions.assertDoesNotThrow(() -> categoryService.delete(category.id()).block());
    }

    @Test
    void givenCategory_whenUpdate_thenUpdateCategory(Category category) {
        Category updatedCategory = Category.builder()
                .id(category.id())
                .name("Updated Category")
                .build();
        when(categoryRepository.findById(category.id())).thenReturn(Mono.just(category));
        when(categoryRepository.persist(any(Category.class))).thenReturn(Mono.just(updatedCategory));
        Category result = categoryService.update(category.id(), updatedCategory).block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(updatedCategory.id(), result.id());
        Assertions.assertEquals(updatedCategory.name(), result.name());
    }

}