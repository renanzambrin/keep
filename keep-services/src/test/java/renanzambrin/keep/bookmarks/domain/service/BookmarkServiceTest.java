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
import renanzambrin.keep.bookmarks.domain.entity.Bookmark;
import renanzambrin.keep.bookmarks.domain.repository.BookmarkRepository;
import renanzambrin.keep.bookmarks.domain.resolver.BookmarkResolver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, BookmarkResolver.class})
class BookmarkServiceTest {

    @Mock
    private BookmarkRepository repository;
    @InjectMocks
    private BookmarkService service;

    @Captor
    private ArgumentCaptor<Bookmark> captor;

    @Test
    void givenBookmarkWithNoUUID_whenCreate_thenCreateNewBookmark() {
        Mockito.when(repository.persist(ArgumentMatchers.any())).thenReturn(Mono.empty());

        final Bookmark bookmark = Bookmark.builder()
                .name("Test Bookmark")
                .description("A very good description")
                .url("https:a-url.test")
                .build();

        service.create(bookmark).block();

        Mockito.verify(repository).persist(captor.capture());
        Bookmark result = captor.getValue();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(bookmark.name(), result.name());
        Assertions.assertEquals(bookmark.description(), result.description());
        Assertions.assertEquals(bookmark.url(), result.url());
        Assertions.assertNull(bookmark.id());
        Assertions.assertNotNull(result.id());
    }

    @Test
    void givenBookmark_whenFindAll_thenGetAllCategories(Bookmark bookmark) {
        when(repository.findAll()).thenReturn(Flux.just(bookmark));
        Bookmark result = service.findAll().blockFirst();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(bookmark.name(), result.name());
    }

    @Test
    void givenBookmark_whenFindById_thenGetBookmark(Bookmark bookmark) {
        when(repository.findById(any())).thenReturn(Mono.just(bookmark));
        Bookmark result = service.findById(bookmark.id()).block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(bookmark.id(), result.id());
        Assertions.assertEquals(bookmark.name(), result.name());
    }

    @Test
    void givenBookmark_whenDelete_thenDeleteBookmark(Bookmark bookmark) {
        when(repository.findById(any())).thenReturn(Mono.just(bookmark));
        when(repository.remove(any(Bookmark.class))).thenReturn(Mono.just(true));
        Assertions.assertDoesNotThrow(() -> service.delete(bookmark.id()).block());
    }

    @Test
    void givenBookmark_whenUpdate_thenUpdateBookmark(Bookmark bookmark) {
        Bookmark updatedBookmark = Bookmark.builder()
                .id(bookmark.id())
                .name("Updated Bookmark")
                .description("Updated Description")
                .url("https://updated-url.test")
                .build();
        when(repository.findById(bookmark.id())).thenReturn(Mono.just(bookmark));
        when(repository.persist(any(Bookmark.class))).thenReturn(Mono.just(updatedBookmark));
        Bookmark result = service.update(bookmark.id(), updatedBookmark).block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(updatedBookmark.id(), result.id());
        Assertions.assertEquals(updatedBookmark.name(), result.name());
    }

}