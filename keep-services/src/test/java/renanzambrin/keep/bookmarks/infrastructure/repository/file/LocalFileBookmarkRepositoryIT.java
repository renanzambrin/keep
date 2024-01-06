package renanzambrin.keep.bookmarks.infrastructure.repository.file;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import renanzambrin.keep.bookmarks.domain.entity.Bookmark;
import renanzambrin.keep.bookmarks.domain.resolver.BookmarkResolver;
import renanzambrin.keep.shared.infrastructure.repository.AbstractLocalFileIntegrationTest;

import static renanzambrin.keep.bookmarks.infrastructure.repository.file.AbstractLocalFileRepository.FILE_EXTENSION;

@ExtendWith(BookmarkResolver.class)
class LocalFileBookmarkRepositoryIT extends AbstractLocalFileIntegrationTest {

    @Autowired
    private LocalFileBookmarkRepository repository;

    @AfterEach
    @SneakyThrows
    void tearDown() {
        Files.deleteIfExists(Path.of(LocalFileBookmarkRepository.class.getSimpleName().concat(FILE_EXTENSION)));
    }

    @Test
    void givenValidBookmark_WhenPersist_ThenNoErrors(Bookmark bookmark) {
        final Bookmark result = repository.persist(bookmark).block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(bookmark.id(), result.id());
        Assertions.assertEquals(bookmark.name(), result.name());
        Assertions.assertEquals(bookmark.url(), result.url());
        Assertions.assertEquals(bookmark.description(), result.description());
    }

    @Test
    void givenExistingBookmark_WhenFindById_ThenReturnOne(Bookmark bookmark) {
        repository.persist(bookmark).block();
        final Bookmark result = repository.findById(bookmark.id()).block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(bookmark.id(), result.id());
        Assertions.assertEquals(bookmark.name(), result.name());
        Assertions.assertEquals(bookmark.url(), result.url());
        Assertions.assertEquals(bookmark.description(), result.description());
    }

    @Test
    void givenExistingBookmarks_WhenFindAll_ThenReturnAll(Bookmark bookmarkOne, Bookmark bookmarkTwo) {
        Assertions.assertNotEquals(bookmarkOne.id().toString(), bookmarkTwo.id().toString());
        repository.persist(bookmarkOne).block();
        repository.persist(bookmarkTwo).block();
        final List<Bookmark> resultList = repository.findAll().collectList().block();
        Assertions.assertNotNull(resultList);
        Assertions.assertFalse(resultList.isEmpty());
        Assertions.assertTrue(resultList.containsAll(List.of(bookmarkOne, bookmarkTwo)));
    }

    @Test
    void givenExistingBookmark_WhenRemove_ThenReturnTrue(Bookmark bookmark) {
        repository.persist(bookmark).block();
        Assertions.assertTrue(repository.remove(bookmark).block());
        final Bookmark result = repository.findById(bookmark.id()).block();
        Assertions.assertNull(result);
    }

}