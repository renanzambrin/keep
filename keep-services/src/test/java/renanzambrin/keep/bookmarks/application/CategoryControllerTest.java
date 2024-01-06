package renanzambrin.keep.bookmarks.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import renanzambrin.keep.bookmarks.domain.entity.Category;
import renanzambrin.keep.bookmarks.domain.resolver.CategoryResolver;
import renanzambrin.keep.bookmarks.domain.service.CategoryService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest
@ExtendWith(CategoryResolver.class)
class CategoryControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CategoryService categoryService;

    @Test
    void givenValidRequest_WhenPost_ThenCategoryIsReturned(Category category) {
        when(categoryService.create(any())).thenReturn(Mono.just(category));

        NewCategoryRequestDTO requestDTO = NewCategoryRequestDTO.builder()
                .name("test-category")
                .build();

        webTestClient.post()
                .uri("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(requestDTO), NewCategoryRequestDTO.class)
                .exchange()
                .expectBody(Category.class)
                .isEqualTo(category);
    }

}