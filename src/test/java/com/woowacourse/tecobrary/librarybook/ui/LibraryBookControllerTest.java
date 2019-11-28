package com.woowacourse.tecobrary.librarybook.ui;

import com.woowacourse.tecobrary.common.util.RestAssuredTestUtils;
import com.woowacourse.tecobrary.librarybook.common.LibraryBookStatic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

class LibraryBookControllerTest extends RestAssuredTestUtils implements LibraryBookStatic {

    @DisplayName("[POST] /books, 도서를 성공적으로 등록한다.")
    @Test
    void successfullyCreateLibraryBook() {
        given().
                contentType(JSON).
                body(new LibraryBookRequestDto(IMAGE, TITLE, AUTHOR, PUBLISHER, ISBN, DESCRIPTION)).
        when().
                post(baseUrl("/books")).
        then().
                log().ifError().
                log().ifValidationFails().
                statusCode(200).
                contentType(JSON).
                body("id", notNullValue()).
                body("message", is(TITLE + " register succeed"));
    }

    @DisplayName("[POST] /books, isbn이 같은 도서가 이미 존재할 때, 등록을 실패한다.")
    @Test
    void failedCreateLibraryBook() {
        given().
                contentType(JSON).
                body(new LibraryBookRequestDto(IMAGE, TITLE, AUTHOR, PUBLISHER, "0123", DESCRIPTION)).
        when().
                post(baseUrl("/books")).
        then().
                log().ifError().
                log().ifValidationFails().
                statusCode(400).
                contentType(JSON).
                body("message", is(TITLE + " register failed"));
    }

    @DisplayName("[GET] /books/all, 총 도서 수를 조회한다.")
    @Test
    void readLibraryBookTotalCount() {
        given().
        when().
                get(baseUrl("/books/all")).
        then().
                log().ifError().
                statusCode(200).
                contentType(JSON).
                body("total", is(1));
    }

    @Test
    void readLibraryBook() {
        given().
                pathParam("id", 1L).
        when().
                get(baseUrl("/books/{id}")).
        then().
                log().ifError().
                statusCode(200).
                contentType(JSON).
                body("id", is(1)).
                body("image", is("https://image.book")).
                body("title", is("제목")).
                body("author", is("작가")).
                body("publisher", is("출판사")).
                body("isbn", is("0123")).
                body("description", is("요약"));
    }

    @DisplayName("[GET] /books/all, 총 도서 수를 조회한다.")
    @Test
    void readLibraryBookTotalCount() {
        given().
        when().
                get(baseUrl("/books/all")).
        then().
                log().ifError().
                statusCode(200).
                contentType(JSON).
                body("total", is(1));
    }
}