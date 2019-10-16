package com.example.inputofcalories.common.exception.assertion

class AssertionHandler {

    inner class Builder {
        fun setStrategy(assertionHandleStrategy: AssertionHandleStrategy): Builder {
            AssertionHandler.assertionHandleStrategy = assertionHandleStrategy

            return this
        }

        fun build(): AssertionHandler = this@AssertionHandler
    }

    companion object {
        private lateinit var assertionHandleStrategy: AssertionHandleStrategy

        fun onException(t: Throwable) = assertionHandleStrategy.execute(t)
    }
}