package com.woowacourse.tecobrary.wishbook.command.application;

import com.woowacourse.tecobrary.wishbook.command.domain.DuplicatedWishBookIsbnException;
import com.woowacourse.tecobrary.wishbook.command.domain.NotFoundWishBookException;
import com.woowacourse.tecobrary.wishbook.command.domain.WishBook;
import com.woowacourse.tecobrary.wishbook.command.domain.WishBookRepository;
import com.woowacourse.tecobrary.wishbook.command.util.WishBookInfoDtoMapper;
import com.woowacourse.tecobrary.wishbook.ui.dto.WishBookInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class WishBookService {

    private final WishBookRepository wishBookRepository;

    @Autowired
    public WishBookService(final WishBookRepository wishBookRepository) {
        this.wishBookRepository = wishBookRepository;
    }

    public List<WishBookInfoDto> findWishBooksOnPage(final int page, final int number) {
        Page<WishBook> pageWishBooks = wishBookRepository.findAll(PageRequest.of(page - 1, number));

        return pageWishBooks.getContent()
                .stream()
                .map(WishBookInfoDtoMapper::toDto)
                .collect(toList());
    }

    public Long createWishBook(final WishBookInfoDto wishBookInfoDto) {
        checkDuplicatedWishBookIsbn(wishBookInfoDto);

        WishBook wishBook = WishBookInfoDtoMapper.toEntity(wishBookInfoDto);
        return wishBookRepository.save(wishBook).getId();
    }

    private void checkDuplicatedWishBookIsbn(final WishBookInfoDto wishBookInfoDto) {
        if (wishBookRepository.existsByWishBookInfoIsbn(wishBookInfoDto.getIsbn())) {
            throw new DuplicatedWishBookIsbnException();
        }
    }

    public WishBookInfoDto findById(final Long id) {
        WishBook wishBook = getWishBook(id);
        return WishBookInfoDtoMapper.toDto(wishBook);
    }

    private WishBook getWishBook(final Long id) {
        return wishBookRepository.findById(id)
                .orElseThrow(NotFoundWishBookException::new);
    }

    public WishBook findNotEnrolledById(Long id) {
        return wishBookRepository.findByIdAndDeletedAtNull(id)
                .orElseThrow(NotFoundWishBookException::new);
    }

    public WishBook findEnrolledById(Long id) {
        return wishBookRepository.findByIdAndDeletedAtNotNull(id)
                .orElseThrow(NotFoundWishBookException::new);
    }

    @Transactional
    public WishBookInfoDto softDeleteById(Long id) {
        checkNotSoftDeleted(id);
        return WishBookInfoDtoMapper.toDto(softDelete(id));
    }

    private void checkNotSoftDeleted(final Long id) {
        if (!wishBookRepository.existsByIdAndDeletedAtNotNull(id)) {
            throw new AlreadySoftDeletedWishBookException();
        }
    }

    private WishBook softDelete(final Long id) {
        WishBook wishBook = getWishBook(id);
        wishBook.softDelete();
        return wishBook;
    }

    @Transactional
    public void deleteWishBook(final Long id) {
        checkNotExistWishBook(id);
        wishBookRepository.deleteById(id);
    }

    private void checkNotExistWishBook(final Long id) {
        if (!wishBookRepository.existsById(id)) {
            throw new NotFoundWishBookException();
        }
    }
}
