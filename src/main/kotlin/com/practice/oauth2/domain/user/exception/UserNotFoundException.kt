package com.practice.oauth2.domain.user.exception

import com.novalink.simpleparkingserver.global.error.code.ErrorCode
import com.practice.oauth2.global.error.BasicException

class UserNotFoundException : BasicException(ErrorCode.NOT_FOUND_USER)