package renanzambrin.keep.bookmarks.domain.entity;

import java.util.UUID;
import lombok.Builder;
import org.springframework.data.annotation.Id;

@Builder(toBuilder = true)
public record Category(
        @Id UUID id,
        String name
) {}