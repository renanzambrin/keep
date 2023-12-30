package renanzambrin.keep.shared.infrastructure.repository;

import java.time.Duration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;
import renanzambrin.keep.shared.annotation.MongoDB;

public class AbstractMongoDbIntegrationTest extends AbstractDatabaseIntegrationTest {

    private static final MongoDBContainer MONGO_DB_CONTAINER =
            new MongoDBContainer(DockerImageName.parse("mongo:latest"))
                    .withExposedPorts(27017)
                    .withStartupTimeout(Duration.ofSeconds(30));

    static {
        MONGO_DB_CONTAINER.start();
    }

    @DynamicPropertySource
    static void setProperties(final DynamicPropertyRegistry registry) {
        registry.add("keep.database.type", () -> MongoDB.DATABASE_TYPE_VALUE);
        registry.add("keep.database.url", MONGO_DB_CONTAINER::getConnectionString);
    }

}