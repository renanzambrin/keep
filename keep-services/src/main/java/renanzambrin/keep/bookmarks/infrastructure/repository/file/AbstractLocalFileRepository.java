package renanzambrin.keep.bookmarks.infrastructure.repository.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractLocalFileRepository<K, V> {

    public static final String FILE_EXTENSION = ".keep";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AtomicReference<Map<K, V>> values = new AtomicReference<>(new HashMap<>());

    public AbstractLocalFileRepository() {
        values.get().putAll(readFromFile());
    }

    private Map<K, V> readFromFile() {
        try {
            if (!Files.exists(getPath())) {
                Files.createFile(getPath());
            }
            String json = Files.readString(getPath());
            return objectMapper.readValue(json, new TypeReference<>() {});
        } catch (IOException e) {
            return new HashMap<>();
        }
    }

    private Path getPath() {
        return Paths.get(this.getClass().getSimpleName().concat(FILE_EXTENSION));
    }

    protected V add(K key, V value) {
        values.get().put(key, value);
        writeToFile();
        return value;
    }

    private void writeToFile() {
        try {
            String json = objectMapper.writeValueAsString(values);
            Files.writeString(getPath(), json);
        } catch (IOException e) {
            throw new RuntimeException("Unable to write to file.", e);
        }
    }

    protected Map<K, V> get() {
        return values.get();
    }

    protected Boolean deleteByKey(K key) {
        values.get().remove(key);
        return Boolean.TRUE;
    }

}