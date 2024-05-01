package com.practice.oauth2.global.oauth.exception

import com.novalink.simpleparkingserver.global.error.code.ErrorCode
import com.practice.oauth2.global.error.BasicException

class InvalidSocialTypeException : BasicException(ErrorCode.INVALID_SOCIAL_TYPE) {
}