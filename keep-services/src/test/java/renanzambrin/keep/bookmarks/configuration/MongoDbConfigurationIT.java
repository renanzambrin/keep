package renanzambrin.keep.bookmarks.configuration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import renanzambrin.keep.shared.infrastructure.repository.AbstractMongoDbIntegrationTest;

@SpringBootTest
class MongoDbConfigurationIT extends AbstractMongoDbIntegrationTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void givenDatabaseTypeIsMongo_WhenContextIsLoaded_ThenMongoDbConfigurationIsLoaded() {
        final MongoDbConfiguration bean = context.getBean(MongoDbConfiguration.class);
        Assertions.assertNotNull(bean);
    }

}