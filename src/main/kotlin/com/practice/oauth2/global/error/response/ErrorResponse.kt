package com.practice.oauth2.global.error.response

import com.novalink.simpleparkingserver.global.error.code.ErrorCode

class ErrorResponse(
    val status: Int,
    val code: Int,
    val message: String
) {
    constructor(errorCode: ErrorCode) : this(errorCode.status, errorCode.code, errorCode.message)
}