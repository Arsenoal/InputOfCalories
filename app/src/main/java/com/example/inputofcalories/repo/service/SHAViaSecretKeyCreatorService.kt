package com.example.inputofcalories.repo.service

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

const val key: String = "0123456789ABCDEF0123456789ABCDEFsafestPasswordBtw"

class SHAViaSecretKeyCreatorService: SHACreatorService {

    override suspend fun encrypt(text: String): String {
        val key = SecretKeySpec(key.toByteArray(Charsets.UTF_8), "HmacSHA1")
        val mac: Mac = Mac.getInstance("HmacSHA1")
        mac.init(key)

        val bytes: ByteArray = mac.doFinal(text.toByteArray(Charsets.UTF_8))

        return bytes.toString().replace("/","_")

    }
}