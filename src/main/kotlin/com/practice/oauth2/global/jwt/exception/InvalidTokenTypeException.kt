package com.practice.oauth2.global.jwt.exception

import com.novalink.simpleparkingserver.global.error.code.ErrorCode
import com.practice.oauth2.global.error.BasicException

class InvalidTokenTypeException : BasicException(ErrorCode.INVALID_TOKEN_TYPE)