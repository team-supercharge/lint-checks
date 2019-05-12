package com.balazsruda.lintchecks.android

import com.android.tools.lint.checks.infrastructure.TestFiles.java
import com.android.tools.lint.checks.infrastructure.TestFiles.kt
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import org.junit.Test

class AndroidUtilLogUsageDetectorTest {

    @Test
    fun `android util Log usage in Java`() {
        lint()
            .files(
                java(
                    """
            import android.util.Log;

            class Test {
                public void foo() {
                }
            }"""
                ).indented()
            )
            .issues(ISSUE_ANDROID_UTIL_LOG_USAGE)
            .run()
            .expect(
                """
            |src/Test.java:1: Warning: Should not be using android.util.Log. [AndroidUtilLogUsage]
            |import android.util.Log;
            |~~~~~~~~~~~~~~~~~~~~~~~~
            |0 errors, 1 warnings""".trimMargin()
            )
    }

    @Test
    fun `android util Log usage in Kotlin`() {
        lint()
            .files(
                kt(
                    """
            import android.util.Log

            class Test {
              fun test()
            }"""
                ).indented()
            )
            .issues(ISSUE_ANDROID_UTIL_LOG_USAGE)
            .run()
            .expect(
                """
            |src/Test.kt:1: Warning: Should not be using android.util.Log. [AndroidUtilLogUsage]
            |import android.util.Log
            |~~~~~~~~~~~~~~~~~~~~~~~
            |0 errors, 1 warnings""".trimMargin()
            )
    }

    @Test
    fun `Timber Log usage in Kotlin`() {
        lint()
            .files(
                kt(
                    """
            import timber.log.Timber

            class Test {
              fun test()
            }"""
                ).indented()
            )
            .issues(ISSUE_ANDROID_UTIL_LOG_USAGE)
            .run()
            .expectClean()
    }
}
