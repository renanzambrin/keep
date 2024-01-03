package renanzambrin.keep.bookmarks.infrastructure.repository.mongodb;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import renanzambrin.keep.bookmarks.domain.entity.Bookmark;
import renanzambrin.keep.bookmarks.domain.resolver.BookmarkResolver;
import renanzambrin.keep.shared.infrastructure.repository.AbstractMongoDbIntegrationTest;

@ExtendWith(BookmarkResolver.class)
class MongoDbBookmarkRepositoryIT extends AbstractMongoDbIntegrationTest {

    @Autowired
    private MongoDbBookmarkRepository repository;

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