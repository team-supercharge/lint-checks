package com.balazsruda.lintchecks.android

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Category.Companion.CORRECTNESS
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope.JAVA_FILE
import com.android.tools.lint.detector.api.Severity
import com.intellij.psi.impl.source.PsiExtensibleClass
import org.jetbrains.uast.UImportStatement
import org.jetbrains.uast.tryResolve
import java.util.EnumSet

val ISSUE_ANDROID_UTIL_LOG_USAGE = Issue.create(
    "AndroidUtilLogUsage",
    "Use Timber for logging instead",
    "Timber logging is much more powerful",
    CORRECTNESS,
    DEFAULT_PRIORITY,
    Severity.WARNING,
    Implementation(AndroidUtilLogUsageDetector::class.java, EnumSet.of(JAVA_FILE /*, GRADLE_FILE, TEST_SOURCES*/))
)

class AndroidUtilLogUsageDetector : Detector(), Detector.UastScanner {
    override fun getApplicableUastTypes() = listOf(UImportStatement::class.java)

    override fun createUastHandler(context: JavaContext) = ImportHandler(context)

    class ImportHandler(private val context: JavaContext) : UElementHandler() {

        override fun visitImportStatement(node: UImportStatement) {

            val resolved = node.tryResolve() as? PsiExtensibleClass ?: return

            if (resolved.qualifiedName == "android.util.Log") {
                context.report(
                    ISSUE_ANDROID_UTIL_LOG_USAGE, node, context.getLocation(node),
                    "Should not be using android.util.Log."
                )
            }
        }
    }
}
