package com.example.inputofcalories.repo.service

import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class SHACreatorTest: Assert() {

    @Test
    fun `on same text same encryption test`() = runBlocking {
        val shaCreatorService = SHAViaSecretKeyCreatorService()

        val res_1 = shaCreatorService.encrypt("key_to_encrypt")
        val res_2 = shaCreatorService.encrypt("key_to_encrypt")

        assertEquals(res_1, res_2)
    }

    @Test
    fun `on key encryption output test`() = runBlocking {
        val shaCreatorService = SHAViaSecretKeyCreatorService()

        assertEquals(
            shaCreatorService.encrypt("key_to_encrypt"),
            "C�����\n�)�F�Z\u000B��K�`V"
        )
    }
}