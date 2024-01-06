package renanzambrin.keep.shared.infrastructure.repository;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import renanzambrin.keep.shared.annotation.LocalFile;

public abstract class AbstractLocalFileIntegrationTest extends AbstractDatabaseIntegrationTest {

    @DynamicPropertySource
    static void setProperties(final DynamicPropertyRegistry registry) {
        registry.add("keep.database.type", () -> LocalFile.DATABASE_TYPE_VALUE);
    }

}