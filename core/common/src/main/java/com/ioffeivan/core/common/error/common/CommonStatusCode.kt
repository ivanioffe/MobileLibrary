package com.ioffeivan.core.common.error.common

/**
 * Defines unique identifiers for common system-level exceptions and unknown errors.
 */
enum class CommonStatusCode(val code: Int) {
    NullPointerException(1),
    IllegalStateException(2),
    IllegalArgumentException(3),
    ArrayIndexOutOfBoundsException(4),
    Unknown(0),
    ;

    companion object {
        fun from(code: Int) = entries.find { it.code == code } ?: Unknown
    }
}
