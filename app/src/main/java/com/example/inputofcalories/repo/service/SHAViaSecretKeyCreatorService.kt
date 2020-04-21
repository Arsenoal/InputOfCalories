package com.example.inputofcalories.repo.service

import android.util.Base64
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class IoCEncoderService: EncoderService {

    @Suppress("GetInstance")
    override suspend fun encode(text: String): String {
        val cipher: Cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey())

        return Base64.encodeToString(cipher.doFinal(text.toByteArray(Charsets.UTF_8)), Base64.DEFAULT)

    }

    private fun getSecretKey() = run {
        val key = "0123456789ABCDEF0123456789ABCDEFsafestPasswordBtw"

        var bites = key.toByteArray(Charsets.UTF_8)
        val sha = MessageDigest.getInstance("SHA-1")
        bites = sha.digest(bites)
        bites = bites.copyOf(16)
        SecretKeySpec(bites, "AES")
    }
}