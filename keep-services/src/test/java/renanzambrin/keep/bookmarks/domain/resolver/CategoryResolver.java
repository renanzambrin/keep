package renanzambrin.keep.bookmarks.domain.resolver;

import java.util.UUID;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import renanzambrin.keep.bookmarks.domain.entity.Category;

public class CategoryResolver implements ParameterResolver {

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(Category.class);
    }

    @Override
    public Category resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return Category.builder()
                .id(UUID.randomUUID())
                .name("test-category-name")
                .build();
    }

}