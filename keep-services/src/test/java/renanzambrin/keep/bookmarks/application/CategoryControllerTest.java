package renanzambrin.keep.bookmarks.application;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import renanzambrin.keep.bookmarks.domain.entity.Category;
import renanzambrin.keep.bookmarks.domain.resolver.CategoryResolver;
import renanzambrin.keep.bookmarks.domain.service.CategoryService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = CategoryController.class)
@ExtendWith(CategoryResolver.class)
class CategoryControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CategoryService categoryService;

    @Test
    void givenValidRequest_WhenPost_ThenCategoryIsReturned(Category category) {
        when(categoryService.create(any())).thenReturn(Mono.just(category));

        CategoryDTO requestDTO = CategoryDTO.builder()
                .name("test-category")
                .build();

        webTestClient.post()
                .uri("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(requestDTO), CategoryDTO.class)
                .exchange()
                .expectBody(Category.class)
                .isEqualTo(category);
    }

    @Test
    void givenValidRequest_WhenGetAll_ThenCategoriesAreReturned(Category categoryOne, Category categoryTwo) {
        List<Category> categories = Arrays.asList(categoryOne, categoryTwo);
        when(categoryService.findAll()).thenReturn(Flux.fromIterable(categories));
        webTestClient.get()
                .uri("/categories")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2)
                .contains(categories.toArray(new Category[0]));
    }

    @Test
    void givenValidId_WhenGetById_ThenCategoryIsReturned(Category category) {
        final UUID id = UUID.randomUUID();
        when(categoryService.findById(id)).thenReturn(Mono.just(category));
        webTestClient.get()
                .uri("/categories/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectBody(Category.class)
                .isEqualTo(category);
    }

    @Test
    void givenValidId_WhenDelete_ThenTrueIsReturned() {
        final UUID id = UUID.randomUUID();
        when(categoryService.delete(id)).thenReturn(Mono.empty());
        webTestClient.delete()
                .uri("/categories/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    void givenValidId_WhenUpdate_ThenCategoryIsReturned(Category category) {
        final UUID id = UUID.randomUUID();
        when(categoryService.update(eq(id), any())).thenReturn(Mono.just(category));

        CategoryDTO requestDTO = CategoryDTO.builder()
                .name("test-updated-category")
                .build();

        webTestClient.put()
                .uri("/categories/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(requestDTO), CategoryDTO.class)
                .exchange()
                .expectBody(Category.class)
                .isEqualTo(category);
    }

}