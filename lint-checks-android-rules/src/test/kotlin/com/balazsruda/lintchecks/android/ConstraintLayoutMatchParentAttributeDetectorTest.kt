package com.balazsruda.lintchecks.android

import com.android.tools.lint.checks.infrastructure.TestFiles.xml
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import org.junit.Test

@SuppressWarnings("MaxLineLength")
class ConstraintLayoutMatchParentAttributeDetectorTest {

    @Test fun `match_parent is used among children of a ConstraintLayout`() {
    lint()
        .files(xml("res/layout/layout.xml", """
        <android.support.constraint.ConstraintLayout
            xmlns:android="http://schemas.android.com/apk/res/android"
            android:layout_width="match_parent"
            android:layout_height="match_parent">

        <TextView
            android:layout_height="match_parent"
            android:layout_width="match_parent" />

        </android.support.constraint.ConstraintLayout>""").indented())
        .issues(ISSUE_CONSTRAINT_LAYOUT_MATCH_PARENT_ATTRIBUTE_DETECTOR)
        .run()
        .expect("""
                |res/layout/layout.xml:7: Warning: Please don't use match_parent in ConstraintLayout} [ConstraintLayoutMatchParentAttribute]
                |    android:layout_height="match_parent"
                |                           ~~~~~~~~~~~~
                |res/layout/layout.xml:8: Warning: Please don't use match_parent in ConstraintLayout} [ConstraintLayoutMatchParentAttribute]
                |    android:layout_width="match_parent" />
                |                          ~~~~~~~~~~~~
                |0 errors, 2 warnings
            """.trimMargin())
        .expectFixDiffs("""
            |Fix for res/layout/layout.xml line 7: Replace "match_parent" with "0dp":
            |@@ -8 +8
            |-         android:layout_height="match_parent" />
            |+         android:layout_height="0dp" />
            |Fix for res/layout/layout.xml line 7: Replace "match_parent" with "0dp" and parent constraints:
            |@@ -3 +3
            |+     xmlns:app="http://schemas.android.com/apk/res-auto"
            |@@ -4 +5
            |-     android:layout_height="match_parent" >
            |+     android:layout_height="0dp"
            |+     app:layout_constraintBottom_toBottomOf="parent" >
            |@@ -8 +10
            |-         android:layout_height="match_parent" />
            |+         android:layout_height="match_parent"
            |+         app:layout_constraintTop_toTopOf="parent" />
            |Fix for res/layout/layout.xml line 8: Replace "match_parent" with "0dp":
            |@@ -7 +7
            |-         android:layout_width="match_parent"
            |+         android:layout_width="0dp"
            |Fix for res/layout/layout.xml line 8: Replace "match_parent" with "0dp" and parent constraints:
            |@@ -3 +3
            |-     android:layout_width="match_parent"
            |-     android:layout_height="match_parent" >
            |+     xmlns:app="http://schemas.android.com/apk/res-auto"
            |+     android:layout_width="0dp"
            |+     android:layout_height="match_parent"
            |+     app:layout_constraintEnd_toEndOf="parent" >
            |@@ -8 +10
            |-         android:layout_height="match_parent" />
            |+         android:layout_height="match_parent"
            |+         app:layout_constraintStart_toStartOf="parent" />
            |""".trimMargin())
    }

    @Test fun `wrap_content and exact dp are used among children of a ConstraintLayout`() {
        lint()
            .files(xml("res/layout/layout.xml", """
        <android.support.constraint.ConstraintLayout
            xmlns:android="http://schemas.android.com/apk/res/android"
            android:layout_width="match_parent"
            android:layout_height="match_parent">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="16dp" />

        </android.support.constraint.ConstraintLayout>""").indented())
            .issues(ISSUE_CONSTRAINT_LAYOUT_MATCH_PARENT_ATTRIBUTE_DETECTOR)
            .run()
            .expectClean()
    }
}
