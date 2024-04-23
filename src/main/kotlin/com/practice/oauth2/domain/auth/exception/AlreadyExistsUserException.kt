package com.practice.oauth2.domain.auth.exception

import com.novalink.simpleparkingserver.global.error.code.ErrorCode
import com.practice.oauth2.global.error.BasicException

class AlreadyExistsUserException : BasicException(ErrorCode.ALREADY_EXISTS_USER)