package com.novalink.simpleparkingserver.global.error.code

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: Int,
    val code: Int,
    val message: String
) {
    //internal
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), 0, "internal server error"),

    //bad request
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), 1, "send request in the correct format"),

    //jwt
    INVALID_TOKEN(HttpStatus.FORBIDDEN.value(), 10001, "this token is not valid"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED.value(), 10002, "this token is expired"),
    INVALID_TOKEN_TYPE(HttpStatus.BAD_REQUEST.value(), 10003, "this token's type is not valid"),

    //user
    NOT_FOUND_USER(HttpStatus.NOT_FOUND.value(), 20001, "this user is not found"),

    //auth
    NOT_PASSED_AUTHENTICATION(HttpStatus.UNAUTHORIZED.value(), 30001, "this request requires authentication"),
    INVALID_ROLE(HttpStatus.FORBIDDEN.value(), 30002, "this role is not valid for this request"),
    ALREADY_EXISTS_USER(HttpStatus.BAD_REQUEST.value(), 30003, "user with this email already exists"),
    NOT_CORRECT_PASSWORD(HttpStatus.BAD_REQUEST.value(), 30004, "password is not correct"),

    //OAuth
    OAUTH_LOGIN_FAILURE(HttpStatus.FORBIDDEN.value(), 40001, "social login fails"),
}