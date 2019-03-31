package com.balazsruda.lintchecks.common

import com.android.tools.lint.detector.api.CURRENT_API

internal const val DEFAULT_PRIORITY = 10 // Can be ignored

class IssueRegistry : com.android.tools.lint.client.api.IssueRegistry() {
    override val api = CURRENT_API

    override val issues
        get() = listOf(
            ISSUE_MISSING_EMPTY_LINES_AROUND_BLOCK_STATEMENTS
        )
}
