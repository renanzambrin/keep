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
import reactor.core.publisher.Mono;
import renanzambrin.keep.bookmarks.domain.entity.Category;
import renanzambrin.keep.bookmarks.domain.repository.CategoryRepository;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryService service;

    @Captor
    private ArgumentCaptor<Category> captor;

    @Test
    void givenValidCategory() {
        Mockito.when(categoryRepository.persist(ArgumentMatchers.any())).thenReturn(Mono.empty());

        final Category category = Category.builder()
                .name("Test Category")
                .build();

        service.create(category).block();

        Mockito.verify(categoryRepository).persist(captor.capture());
        Category result = captor.getValue();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(category.name(), result.name());
        Assertions.assertNull(category.id());
        Assertions.assertNotNull(result.id());
    }

}