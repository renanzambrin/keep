package renanzambrin.keep.bookmarks.domain.resolver;

import java.util.UUID;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import renanzambrin.keep.bookmarks.domain.entity.Bookmark;

public class BookmarkResolver implements ParameterResolver {

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(Bookmark.class);
    }

    @Override
    public Bookmark resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return Bookmark.builder()
                .id(UUID.randomUUID())
                .name("test-bookmark-name")
                .url("http://test.bookmark.url")
                .description("test-bookmark-description")
                .build();
    }

}