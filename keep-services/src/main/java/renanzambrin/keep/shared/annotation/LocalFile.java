package renanzambrin.keep.shared.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ConditionalOnProperty(name = "keep.database.type", havingValue = LocalFile.DATABASE_TYPE_VALUE)
public @interface LocalFile {

    String DATABASE_TYPE_VALUE = "local-file";

}