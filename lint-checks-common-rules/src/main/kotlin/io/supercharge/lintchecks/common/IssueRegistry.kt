package io.supercharge.lintchecks.common

import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API
import io.supercharge.lintchecks.constants.VENDOR_CONTACT
import io.supercharge.lintchecks.constants.VENDOR_FEEDBACK_URL
import io.supercharge.lintchecks.constants.VENDOR_IDENTIFIER
import io.supercharge.lintchecks.constants.VENDOR_NAME

class IssueRegistry : com.android.tools.lint.client.api.IssueRegistry() {
    override val api = CURRENT_API

    override val issues
        get() = listOf(
            ISSUE_MISSING_EMPTY_LINES_AROUND_BLOCK_STATEMENTS
        )

    override val vendor: Vendor
        get() = Vendor(
            vendorName = VENDOR_NAME,
            identifier = VENDOR_IDENTIFIER,
            feedbackUrl = VENDOR_FEEDBACK_URL,
            contact = VENDOR_CONTACT
        )
}
