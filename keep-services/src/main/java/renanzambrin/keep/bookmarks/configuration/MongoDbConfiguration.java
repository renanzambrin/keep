package renanzambrin.keep.bookmarks.configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.bson.UuidRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import renanzambrin.keep.shared.annotation.MongoDB;

@MongoDB
@Configuration
public class MongoDbConfiguration {

    private static final String MONGODB_PREFIX = "mongodb://";

    @Value("${keep.database.url}")
    private String databaseUrl;
    @Value("${keep.database.name}")
    private String databaseName;

    @Bean
    public MongoClientSettings mongoClientSettings() {
        return MongoClientSettings.builder()
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .applyConnectionString(new ConnectionString(getConnectionString()))
                .build();
    }

    private String getConnectionString() {
        String connectionString = databaseUrl.startsWith(MONGODB_PREFIX) ? databaseUrl : MONGODB_PREFIX + databaseUrl;
        return connectionString.endsWith(databaseName) ? connectionString : connectionString + "/" + databaseName;
    }

    @Bean
    public MongoClient mongoClient(MongoClientSettings mongoClientSettings) {
        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate(MongoClient mongoClient) {
        return new ReactiveMongoTemplate(mongoClient, databaseName);
    }

}