package com.balazsruda.lintchecks.common

import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API

internal const val DEFAULT_PRIORITY = 10 // Can be ignored

class IssueRegistry : com.android.tools.lint.client.api.IssueRegistry() {
    override val api = CURRENT_API

    override val issues
        get() = listOf(
            ISSUE_MISSING_EMPTY_LINES_AROUND_BLOCK_STATEMENTS
        )

    override val vendor: Vendor
        get() = Vendor(
            vendorName = "Team Supercharge",
            identifier = "lint-checks",
            feedbackUrl = "https://github.com/team-supercharge/lint-checks/issues",
            contact = "https://github.com/team-supercharge/lint-checks"
        )
}
