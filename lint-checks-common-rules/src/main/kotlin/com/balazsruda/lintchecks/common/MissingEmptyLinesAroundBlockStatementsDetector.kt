package com.balazsruda.lintchecks.common

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.LintFix
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiWhiteSpace
import java.util.EnumSet
import org.jetbrains.kotlin.psi.psiUtil.siblings
import org.jetbrains.uast.UBlockExpression
import org.jetbrains.uast.UDoWhileExpression
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UExpression
import org.jetbrains.uast.UForEachExpression
import org.jetbrains.uast.UForExpression
import org.jetbrains.uast.UIfExpression
import org.jetbrains.uast.USwitchExpression
import org.jetbrains.uast.UTryExpression
import org.jetbrains.uast.UWhileExpression

val ISSUE_MISSING_EMPTY_LINES_AROUND_BLOCK_STATEMENTS = Issue.create(
    "MissingEmptyLinesAroundBlockStatements",
    "Marks missing empty lines around blocks statements.",
    "Block statements (such as 'if', 'for', 'while', 'switch', 'when') should always surrounded by empty lines.\n" +
            "Exception if they are placed exactly at the end or the beginning of an enclosing block.\n" +
            "Applying this rule we can get more readable code.",
    Category.CORRECTNESS,
    DEFAULT_PRIORITY,
    Severity.WARNING,
    Implementation(MissingEmptyLinesAroundBlockStatementsDetector::class.java, EnumSet.of(Scope.JAVA_FILE))
)

class MissingEmptyLinesAroundBlockStatementsDetector : Detector(), Detector.UastScanner {
    override fun getApplicableUastTypes() = listOf(
        UIfExpression::class.java,
        USwitchExpression::class.java,
        UForExpression::class.java,
        UForEachExpression::class.java,
        UWhileExpression::class.java,
        UDoWhileExpression::class.java,
        UTryExpression::class.java
    )

    override fun createUastHandler(context: JavaContext) = MethodBlocksHandler(context)

    @SuppressWarnings("TooManyFunctions")
    class MethodBlocksHandler(private val context: JavaContext) : UElementHandler() {

        override fun visitIfExpression(node: UIfExpression) {
            if (node.thenExpression !is UBlockExpression) {
                return
            }

            checkWhiteSpaceAroundBlock(node)
        }

        override fun visitSwitchExpression(node: USwitchExpression) =
            checkWhiteSpaceAroundBlock(node)

        override fun visitForExpression(node: UForExpression) =
            checkWhiteSpaceAroundBlock(node)

        override fun visitForEachExpression(node: UForEachExpression) =
            checkWhiteSpaceAroundBlock(node)

        override fun visitWhileExpression(node: UWhileExpression) =
            checkWhiteSpaceAroundBlock(node)

        override fun visitDoWhileExpression(node: UDoWhileExpression) =
            checkWhiteSpaceAroundBlock(node)

        override fun visitTryExpression(node: UTryExpression) =
            checkWhiteSpaceAroundBlock(node)

        private fun checkWhiteSpaceAroundBlock(node: UElement) {
            checkWhiteSpaceAroundBlock(node, forward = true)
            checkWhiteSpaceAroundBlock(node, forward = false)
        }

        private fun checkWhiteSpaceAroundBlock(node: UElement, forward: Boolean) {
            val firstBlockLevelPsiNode = firstBlockLevelNode(node)?.sourcePsi ?: return

            val firstRelevantWhiteSpaceNode = firstRelevantWhiteSpaceNode(firstBlockLevelPsiNode, forward) ?: return

            if (firstRelevantWhiteSpaceNode.text.count { p -> p == '\n' } > 1) return

            context.report(
                ISSUE_MISSING_EMPTY_LINES_AROUND_BLOCK_STATEMENTS,
                context.getLocation(firstRelevantWhiteSpaceNode),
                "Block statement isn't ${if (forward) "followed" else "preceded"} by empty line.",
                createQuickFix()
            )
        }

        private fun firstBlockLevelNode(uastNode: UElement): UElement? {
            var nextNode = uastNode

            while (nextNode.uastParent !is UBlockExpression) {
                nextNode.uastParent ?: return null
                nextNode = nextNode.uastParent as UElement
            }

            return nextNode as UExpression
        }

        private fun firstRelevantWhiteSpaceNode(blockChildLevelPsiNode: PsiElement, forward: Boolean): PsiWhiteSpace? {

            blockChildLevelPsiNode.siblings(forward).forEach { psiElement ->
                if (psiElement is PsiWhiteSpace && psiElement.text.contains("\n")) {

                    val psiNextSibling = if (forward) psiElement.node.treeNext else psiElement.node.treePrev

                    if (!forward && psiNextSibling is PsiComment) {
                        // continue
                    } else if (psiNextSibling.elementType.toString() == getBraceNodeIElementType(forward)) {
                        return null
                    } else {
                        return psiElement
                    }
                }
            }

            return null
        }

        private fun getBraceNodeIElementType(forward: Boolean) = if (forward) "RBRACE" else "LBRACE"

        private fun createQuickFix() = LintFix.create()
            .name("Add extra line")
            .replace()
            .text("\n")
            .with("\n\n")
            .autoFix()
            .build()
    }
}
