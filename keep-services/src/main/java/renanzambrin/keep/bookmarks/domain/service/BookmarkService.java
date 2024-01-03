package renanzambrin.keep.bookmarks.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import renanzambrin.keep.bookmarks.domain.repository.BookmarkRepository;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;

}