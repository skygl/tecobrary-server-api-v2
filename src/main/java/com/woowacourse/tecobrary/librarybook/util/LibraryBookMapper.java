package com.woowacourse.tecobrary.librarybook.util;

import com.woowacourse.tecobrary.common.model.BookCoverUrl;
import com.woowacourse.tecobrary.common.model.BookInfo;
import com.woowacourse.tecobrary.librarybook.domain.LibraryBook;
import com.woowacourse.tecobrary.librarybook.ui.LibraryBookRequestDto;

public class LibraryBookMapper {

    public static LibraryBook map(final LibraryBookRequestDto libraryBookRequestDto) {
        return new LibraryBook(
                new BookCoverUrl(libraryBookRequestDto.getImage()),
                new BookInfo(libraryBookRequestDto.getTitle(),
                        libraryBookRequestDto.getAuthor(),
                        libraryBookRequestDto.getPublisher(),
                        libraryBookRequestDto.getIsbn(),
                        libraryBookRequestDto.getDescription()));
    }
}
