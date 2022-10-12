package com.example.smartcity.common

class ApiException(val errorCode: Int,val msg: String): Throwable(msg)