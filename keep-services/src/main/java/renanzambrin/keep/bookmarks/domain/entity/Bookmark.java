package renanzambrin.keep.bookmarks.domain.entity;

import java.util.UUID;
import lombok.Builder;
import org.springframework.data.annotation.Id;

@Builder
public record Bookmark(
        @Id UUID id,
        String name,
        String url,
        String description
) {}