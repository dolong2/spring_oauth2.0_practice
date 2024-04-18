package com.practice.oauth2.global.error

import com.novalink.simpleparkingserver.global.error.code.ErrorCode

open class BasicException(
    val errorCode: ErrorCode
) : RuntimeException() {
}