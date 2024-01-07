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
import renanzambrin.keep.bookmarks.domain.entity.Bookmark;
import renanzambrin.keep.bookmarks.domain.resolver.BookmarkResolver;
import renanzambrin.keep.bookmarks.domain.service.BookmarkService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = BookmarkController.class)
@ExtendWith(BookmarkResolver.class)
class BookmarkControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private BookmarkService bookmarkService;

    @Test
    void givenValidRequest_WhenPost_ThenBookmarkIsReturned(Bookmark bookmark) {
        when(bookmarkService.create(any())).thenReturn(Mono.just(bookmark));

        BookmarkDTO requestDTO = BookmarkDTO.builder()
                .name("test-bookmark")
                .build();

        webTestClient.post()
                .uri("/bookmarks")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(requestDTO), BookmarkDTO.class)
                .exchange()
                .expectBody(Bookmark.class)
                .isEqualTo(bookmark);
    }

    @Test
    void givenValidRequest_WhenGetAll_ThenCategoriesAreReturned(Bookmark bookmarkOne, Bookmark bookmarkTwo) {
        List<Bookmark> bookmarks = Arrays.asList(bookmarkOne, bookmarkTwo);
        when(bookmarkService.findAll()).thenReturn(Flux.fromIterable(bookmarks));
        webTestClient.get()
                .uri("/bookmarks")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectBodyList(Bookmark.class)
                .hasSize(2)
                .contains(bookmarks.toArray(new Bookmark[0]));
    }

    @Test
    void givenValidId_WhenGetById_ThenBookmarkIsReturned(Bookmark bookmark) {
        final UUID id = UUID.randomUUID();
        when(bookmarkService.findById(id)).thenReturn(Mono.just(bookmark));
        webTestClient.get()
                .uri("/bookmarks/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectBody(Bookmark.class)
                .isEqualTo(bookmark);
    }

    @Test
    void givenValidId_WhenDelete_ThenTrueIsReturned() {
        final UUID id = UUID.randomUUID();
        when(bookmarkService.delete(id)).thenReturn(Mono.empty());
        webTestClient.delete()
                .uri("/bookmarks/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    void givenValidId_WhenUpdate_ThenBookmarkIsReturned(Bookmark bookmark) {
        final UUID id = UUID.randomUUID();
        when(bookmarkService.update(eq(id), any())).thenReturn(Mono.just(bookmark));

        BookmarkDTO requestDTO = BookmarkDTO.builder()
                .name("test-updated-bookmark")
                .url("https://updated-bookmark.test")
                .description("test-updated-description")
                .build();

        webTestClient.put()
                .uri("/bookmarks/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(requestDTO), BookmarkDTO.class)
                .exchange()
                .expectBody(Bookmark.class)
                .isEqualTo(bookmark);
    }

}