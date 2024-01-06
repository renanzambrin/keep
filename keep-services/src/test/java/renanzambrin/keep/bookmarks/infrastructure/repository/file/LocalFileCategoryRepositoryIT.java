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
import renanzambrin.keep.bookmarks.domain.entity.Category;
import renanzambrin.keep.bookmarks.domain.resolver.CategoryResolver;
import renanzambrin.keep.shared.infrastructure.repository.AbstractLocalFileIntegrationTest;

import static renanzambrin.keep.bookmarks.infrastructure.repository.file.AbstractLocalFileRepository.FILE_EXTENSION;

@ExtendWith(CategoryResolver.class)
class LocalFileCategoryRepositoryIT extends AbstractLocalFileIntegrationTest {

    @Autowired
    private LocalFileCategoryRepository repository;

    @AfterEach
    @SneakyThrows
    void tearDown() {
        Files.deleteIfExists(Path.of(LocalFileCategoryRepository.class.getSimpleName().concat(FILE_EXTENSION)));
    }

    @Test
    void givenValidCategory_WhenPersist_ThenNoErrors(Category category) {
        final Category result = repository.persist(category).block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(category.id(), result.id());
        Assertions.assertEquals(category.name(), result.name());
    }

    @Test
    void givenExistingCategory_WhenFindById_ThenReturnOne(Category category) {
        repository.persist(category).block();
        final Category result = repository.findById(category.id()).block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(category.id(), result.id());
        Assertions.assertEquals(category.name(), result.name());
    }

    @Test
    void givenExistingCategories_WhenFindAll_ThenReturnAll(Category categoryOne, Category categoryTwo) {
        Assertions.assertNotEquals(categoryOne.id().toString(), categoryTwo.id().toString());
        repository.persist(categoryOne).block();
        repository.persist(categoryTwo).block();
        final List<Category> resultList = repository.findAll().collectList().block();
        Assertions.assertNotNull(resultList);
        Assertions.assertFalse(resultList.isEmpty());
        Assertions.assertTrue(resultList.containsAll(List.of(categoryOne, categoryTwo)));
    }

    @Test
    void givenExistingCategory_WhenRemove_ThenReturnTrue(Category category) {
        repository.persist(category).block();
        Assertions.assertTrue(repository.remove(category).block());
        final Category result = repository.findById(category.id()).block();
        Assertions.assertNull(result);
    }

}