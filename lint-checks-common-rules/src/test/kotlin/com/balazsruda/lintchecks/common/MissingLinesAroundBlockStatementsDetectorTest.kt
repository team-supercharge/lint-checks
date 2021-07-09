package com.balazsruda.lintchecks.common

import com.android.tools.lint.checks.infrastructure.TestFiles
import com.android.tools.lint.checks.infrastructure.TestFiles.java
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import org.junit.Test

@SuppressWarnings("MaxLineLength", "LargeClass")
class MissingLinesAroundBlockStatementsDetectorTest {

    @Test
    fun `missing empty line before for block statement in Java`() {
        lint()
            .files(
                java(
                    """
          package foo;

          class Example {

            public void foo() {
              boolean bar;
              for (int i = 0; i < 1; i++) {
                  bar = !bar;
              }
            }
          }"""
                ).indented()
            )
            .issues(ISSUE_MISSING_EMPTY_LINES_AROUND_BLOCK_STATEMENTS)
            .run()
            .expect(
                """
            |src/foo/Example.java:6: Warning: Block statement isn't preceded by empty line. [MissingEmptyLinesAroundBlockStatements]
            |    boolean bar;
            |                ^
            |0 errors, 1 warnings
            |""".trimMargin()
            )
            .expectFixDiffs(
                """
            |Fix for src/foo/Example.java line 6: Add extra line:
            |@@ -7 +7
            |+
            |""".trimMargin()
            )
    }

    @Test
    fun `missing empty line before if block statement in Java`() {
        lint()
            .files(
                java(
                    """
          package foo;

          class Example {

            public void foo() {
              boolean bar;
              if (true) {
                  bar = !bar;
              }
            }
          }"""
                ).indented()
            )
            .issues(ISSUE_MISSING_EMPTY_LINES_AROUND_BLOCK_STATEMENTS)
            .run()
            .expect(
                """
            |src/foo/Example.java:6: Warning: Block statement isn't preceded by empty line. [MissingEmptyLinesAroundBlockStatements]
            |    boolean bar;
            |                ^
            |0 errors, 1 warnings
            |""".trimMargin()
            )
            .expectFixDiffs(
                """
            |Fix for src/foo/Example.java line 6: Add extra line:
            |@@ -7 +7
            |+
            |""".trimMargin()
            )
    }

    @Test
    fun `missing empty line before variable assignment if block statement in Kotlin`() {
        lint()
            .files(
                TestFiles.kt(
                    """
            package foo

            class Example {

                fun foo() {
                    val i = 0
                    val k = when (i) {
                        0 -> println("Foo")
                        else -> println("Bar")
                    }
                }
            }"""
                ).indented()
            )
            .issues(ISSUE_MISSING_EMPTY_LINES_AROUND_BLOCK_STATEMENTS)
            .run()
            .expect(
                """
            |src/foo/Example.kt:6: Warning: Block statement isn't preceded by empty line. [MissingEmptyLinesAroundBlockStatements]
            |        val i = 0
            |                 ^
            |0 errors, 1 warnings
            |""".trimMargin()
            )
            .expectFixDiffs(
                """
            |Fix for src/foo/Example.kt line 6: Add extra line:
            |@@ -7 +7
            |+
            |""".trimMargin()
            )
    }

    @Test
    fun `missing empty line after foreach block statement in Kotlin`() {
        lint()
            .files(
                TestFiles.kt(
                    """
            package foo

            class Example {

                fun foo() {
                    for (i in 0..0) {
                        bar = true
                    }
                    var foo: Boolean
                }
            }"""
                ).indented()
            )
            .issues(ISSUE_MISSING_EMPTY_LINES_AROUND_BLOCK_STATEMENTS)
            .run()
            .expect(
                """
            |src/foo/Example.kt:8: Warning: Block statement isn't followed by empty line. [MissingEmptyLinesAroundBlockStatements]
            |        }
            |         ^
            |0 errors, 1 warnings
            |""".trimMargin()
            )
            .expectFixDiffs(
                """
            |Fix for src/foo/Example.kt line 8: Add extra line:
            |@@ -9 +9
            |+
            |""".trimMargin()
            )
    }

    @Test
    fun `missing empty line after foreach block statement when comment follows in Kotlin`() {
        lint()
            .files(
                TestFiles.kt(
                    """
            package foo

            class Example {

                fun foo() {
                    for (i in 0..0) {
                        bar = true
                    }
                    // var foo: Boolean
                }
            }"""
                ).indented()
            )
            .issues(ISSUE_MISSING_EMPTY_LINES_AROUND_BLOCK_STATEMENTS)
            .run()
            .expect(
                """
            |src/foo/Example.kt:8: Warning: Block statement isn't followed by empty line. [MissingEmptyLinesAroundBlockStatements]
            |        }
            |         ^
            |0 errors, 1 warnings
            |""".trimMargin()
            )
            .expectFixDiffs(
                """
            |Fix for src/foo/Example.kt line 8: Add extra line:
            |@@ -9 +9
            |+
            |""".trimMargin()
            )
    }

    @Test
    fun `missing empty line after foreach block statement when multi-line comment follows in Kotlin`() {
        lint()
            .files(
                TestFiles.kt(
                    """
            package foo

            class Example {

                fun foo() {
                    for (i in 0..0) {
                        bar = true
                    }
                    /* var foo: Boolean
                    * Multi-line comment */
                }
            }"""
                ).indented()
            )
            .issues(ISSUE_MISSING_EMPTY_LINES_AROUND_BLOCK_STATEMENTS)
            .run()
            .expect(
                """
            |src/foo/Example.kt:8: Warning: Block statement isn't followed by empty line. [MissingEmptyLinesAroundBlockStatements]
            |        }
            |         ^
            |0 errors, 1 warnings
            """.trimMargin()
            )
            .expectFixDiffs(
                """
            |Fix for src/foo/Example.kt line 8: Add extra line:
            |@@ -9 +9
            |+
            |""".trimMargin()
            )
    }

    @Test
    fun `missing empty line around foreach block statement when comments embrace the block in Kotlin`() {
        lint()
            .files(
                TestFiles.kt(
                    """
            package foo

            class Example {

                fun foo() {
                    var bar: Boolean // bar
                    for (i in 0..0) {
                        bar = true
                    } // foo
                    var foo: Boolean
                }
            }"""
                ).indented()
            )
            .issues(ISSUE_MISSING_EMPTY_LINES_AROUND_BLOCK_STATEMENTS)
            .run()
            .expect(
                """
                |src/foo/Example.kt:6: Warning: Block statement isn't preceded by empty line. [MissingEmptyLinesAroundBlockStatements]
                |        var bar: Boolean // bar
                |                               ^
                |src/foo/Example.kt:9: Warning: Block statement isn't followed by empty line. [MissingEmptyLinesAroundBlockStatements]
                |        } // foo
                |                ^
                |0 errors, 2 warnings
            """.trimMargin()
            )
            .expectFixDiffs(
                """
            |Fix for src/foo/Example.kt line 6: Add extra line:
            |@@ -7 +7
            |+
            |Fix for src/foo/Example.kt line 9: Add extra line:
            |@@ -10 +10
            |+
            |""".trimMargin()
            )
    }

    @Test
    fun `missing empty line around foreach block statement when asterisk comments embrace the block in Kotlin`() {
        lint()
            .files(
                TestFiles.kt(
                    """
            package foo

            class Example {
                fun foo() {
                    val a = 0 /* foo */
                    for (i in 0..0) {
                        println(i)
                    } /* foo */
                    val k = 0
                }
            }"""
                ).indented()
            )
            .issues(ISSUE_MISSING_EMPTY_LINES_AROUND_BLOCK_STATEMENTS)
            .run()
            .expect(
                """
                |src/foo/Example.kt:5: Warning: Block statement isn't preceded by empty line. [MissingEmptyLinesAroundBlockStatements]
                |        val a = 0 /* foo */
                |                           ^
                |src/foo/Example.kt:8: Warning: Block statement isn't followed by empty line. [MissingEmptyLinesAroundBlockStatements]
                |        } /* foo */
                |                   ^
                |0 errors, 2 warnings
            """.trimMargin()
            )
            .expectFixDiffs(
                """
                |Fix for src/foo/Example.kt line 5: Add extra line:
                |@@ -6 +6
                |+
                |Fix for src/foo/Example.kt line 8: Add extra line:
                |@@ -9 +9
                |+
            """.trimMargin()
            )
    }

    @Test
    fun `no missing empty line around block statement in Kotlin`() {
        lint()
            .files(
                TestFiles.kt(
                    """
            package foo

            class Example {

                fun foo() {
                    for (i in 0..0) {
                        bar = true
                    }
                }
            }"""
                ).indented()
            )
            .issues(ISSUE_MISSING_EMPTY_LINES_AROUND_BLOCK_STATEMENTS)
            .run()
            .expectClean()
    }

    @Test
    fun `no missing empty line around Elvis operator in Kotlin`() {
        lint()
            .files(
                TestFiles.kt(
                    """
            package foo

            class Example {

                fun foo() {
                    var i = null
                    i ?: return
                    println("")
                }
            }"""
                ).indented()
            )
            .issues(ISSUE_MISSING_EMPTY_LINES_AROUND_BLOCK_STATEMENTS)
            .run()
            .expectClean()
    }

    @Test
    fun `no missing empty line around one line if statement in Kotlin`() {
        lint()
            .files(
                TestFiles.kt(
                    """
            package foo

            class Example {

                fun foo() {
                    var i = 2
                    if (i == 1) return
                    println("")
                }
            }"""
                ).indented()
            )
            .issues(ISSUE_MISSING_EMPTY_LINES_AROUND_BLOCK_STATEMENTS)
            .run()
            .expectClean()
    }

    @Test
    fun `no missing empty line around one line if statement in Java`() {
        lint()
            .files(
                TestFiles.java(
                    """
            package foo;

            class Example {

                fun foo() {
                    int i = 2;
                    if (i == 1) return;
                    println("");
                }
            }"""
                ).indented()
            )
            .issues(ISSUE_MISSING_EMPTY_LINES_AROUND_BLOCK_STATEMENTS)
            .run()
            .expectClean()
    }

    @Test
    fun `no missing empty line around ternary operator statement in Kotlin`() {
        lint()
            .files(
                TestFiles.java(
                    """
            package foo;

            class Example {

                fun foo() {
                    int i = 2;
                    i = i == 2 ? 3 : 4;
                    println(i);
                }
            }"""
                ).indented()
            )
            .issues(ISSUE_MISSING_EMPTY_LINES_AROUND_BLOCK_STATEMENTS)
            .run()
            .expectClean()
    }

    @Test
    fun `no missing empty line before for block statement when an empty line is preceding it in Kotlin`() {
        lint()
            .files(
                TestFiles.kt(
                    """
            package foo

            class Example {

                fun foo() {

                    for (i in 0..0) {
                        bar = true
                    }
                }
            }"""
                ).indented()
            )
            .issues(ISSUE_MISSING_EMPTY_LINES_AROUND_BLOCK_STATEMENTS)
            .run()
            .expectClean()
    }

    @Test
    fun `no missing empty line before for block statement when a comment is preceding it in Kotlin`() {
        lint()
            .files(
                TestFiles.kt(
                    """
            package foo

            class Example {

                fun foo() {
                    // Beginning of block
                    for (i in 0..0) {
                        bar = true
                    }
                }
            }"""
                ).indented()
            )
            .issues(ISSUE_MISSING_EMPTY_LINES_AROUND_BLOCK_STATEMENTS)
            .run()
            .expectClean()
    }

    @Test
    fun `missing empty line before for block statement when a comment is preceding it in Kotlin`() {
        lint()
            .files(
                TestFiles.kt(
                    """
            package foo

            class Example {

                fun foo() {
                    val i = 0
                    // Beginning of block
                    for (i in 0..0) {
                        bar = true
                    }
                }
            }"""
                ).indented()
            )
            .issues(ISSUE_MISSING_EMPTY_LINES_AROUND_BLOCK_STATEMENTS)
            .run()
            .expect(
                """
                |src/foo/Example.kt:6: Warning: Block statement isn't preceded by empty line. [MissingEmptyLinesAroundBlockStatements]
                |        val i = 0
                |                 ^
                |0 errors, 1 warnings
            """.trimMargin()
            )
            .expectFixDiffs(
                """
            |Fix for src/foo/Example.kt line 6: Add extra line:
            |@@ -7 +7
            |+
            |""".trimMargin()
            )
    }

    @Test
    fun `no missing empty line around foreach block statement when comments embrace the block in Kotlin`() {
        lint()
            .files(
                TestFiles.kt(
                    """
            package foo

            class Example {

                fun foo() { // Beginning of block
                    for (i in 0..0) {
                        bar = true
                    } // End of block
                }
            }"""
                ).indented()
            )
            .issues(ISSUE_MISSING_EMPTY_LINES_AROUND_BLOCK_STATEMENTS)
            .run()
            .expectClean()
    }

    @Test
    fun `no missing empty line around foreach block statement when asterisk comments embrace the block in Kotlin`() {
        lint()
            .files(
                TestFiles.kt(
                    """
            package foo

            class Example {

                fun foo() { /* Beginning of block */
                    for (i in 0..0) {
                        bar = true
                    } /* End of block */
                }
            }"""
                ).indented()
            )
            .issues(ISSUE_MISSING_EMPTY_LINES_AROUND_BLOCK_STATEMENTS)
            .run()
            .expectClean()
    }

    @Test
    fun `missing empty line around foreach block statement when the block is in one line in Kotlin`() {
        lint()
            .files(
                TestFiles.kt(
                    """
            package foo

            class Example {
                fun foo() {
                    val bar = "foo"
                    for (i in 1..3) { println(i) }
                    val x = 0
                }
            }"""
                ).indented()
            )
            .issues(ISSUE_MISSING_EMPTY_LINES_AROUND_BLOCK_STATEMENTS)
            .run()
            .expect(
                """
                |src/foo/Example.kt:5: Warning: Block statement isn't preceded by empty line. [MissingEmptyLinesAroundBlockStatements]
                |        val bar = "foo"
                |                       ^
                |src/foo/Example.kt:6: Warning: Block statement isn't followed by empty line. [MissingEmptyLinesAroundBlockStatements]
                |        for (i in 1..3) { println(i) }
                |                                      ^
                |0 errors, 2 warnings
            """.trimMargin()
            )
            .expectFixDiffs(
                """
                |Fix for src/foo/Example.kt line 5: Add extra line:
                |@@ -6 +6
                |+
                |Fix for src/foo/Example.kt line 6: Add extra line:
                |@@ -7 +7
                |+
            """.trimMargin()
            )
    }

    @Test
    fun `no missing empty line before if block statement when it's in the expression body of a method in Kotlin`() {
        lint()
            .files(
                TestFiles.kt(
                    """
            package foo

            class Test {
                    private fun foo() =
                        if (fooStatement()) {
                            bar()
                        } else {
                            fooBar()
                        }
            }"""
                ).indented()
            )
            .issues(ISSUE_MISSING_EMPTY_LINES_AROUND_BLOCK_STATEMENTS)
            .run()
            .expectClean()
    }

    @Test
    fun `no missing empty line before if block statement when it's a variable assignment in Kotlin`() {
        lint()
            .files(
                TestFiles.kt(
                    """
            package foo

            class Test {

                private fun test() {
                    val foo =
                        if (fooStatement()) {
                            bar()
                        } else {
                            fooBar()
                        }
                }
            }"""
                ).indented()
            )
            .issues(ISSUE_MISSING_EMPTY_LINES_AROUND_BLOCK_STATEMENTS)
            .run()
            .expectClean()
    }

    @Test
    fun `no missing empty line before if block statement when it's a class level variable assignment in Kotlin`() {
        lint()
            .files(
                TestFiles.kt(
                    """
            package foo

            class Test {
                val foo =
                    if (fooStatement()) {
                        bar()
                    } else {
                        fooBar()
                    }
            }"""
                ).indented()
            )
            .issues(ISSUE_MISSING_EMPTY_LINES_AROUND_BLOCK_STATEMENTS)
            .run()
            .expectClean()
    }

    @Test
    fun `no missing empty line around when block statement in Kotlin`() {
        lint()
            .files(
                TestFiles.kt(
                    """
            package foo

            class Test {
                private fun test(fooType: FooType) {
                    when (fooType) {
                        "foo" -> {
                            foo()
                        }
                        "bar" -> {
                            bar()
                        }
                    }
                }
            }"""
                ).indented()
            )
            .issues(ISSUE_MISSING_EMPTY_LINES_AROUND_BLOCK_STATEMENTS)
            .run()
            .expectClean()
    }

    @Test
    fun `no missing empty line around when block statement when a case doesn't use block in Kotlin`() {
        lint()
            .files(
                TestFiles.kt(
                    """
            package foo

            class Test {
                private fun test(testType: TestType) {
                    when (testType) {
                        TestType.Foo -> {
                            foo()
                        }
                        TestType.Bar -> bar()
                    }
                }
            }"""
                ).indented()
            )
            .issues(ISSUE_MISSING_EMPTY_LINES_AROUND_BLOCK_STATEMENTS)
            .run()
            .expectClean()
    }

    @Test
    fun `missing empty line after if block statement when it's in an invisible lambda block in Kotlin`() {
        lint()
            .files(
                TestFiles.kt(
                    """
            package foo

            class Test {
                private fun test(targetScreen: Screen) {
                    targetScreen?.let { targetScreen ->
                        if (targetScreen !is MainScreen) {
                            navigator.navigateBackTo(MainScreen())
                        }
                        navigator.navigateTo(targetScreen)
                    }
                }
            }"""
                ).indented()
            )
            .issues(ISSUE_MISSING_EMPTY_LINES_AROUND_BLOCK_STATEMENTS)
            .run()
            .expect(
                """|
                |src/foo/Test.kt:8: Warning: Block statement isn't followed by empty line. [MissingEmptyLinesAroundBlockStatements]
                |            }
                |             ^
                |0 errors, 1 warnings
            """.trimMargin()
            )
            .expectFixDiffs(
                """
                |Fix for src/foo/Test.kt line 8: Add extra line:
                |@@ -9 +9
                |+
            """.trimMargin()
            )
    }

    @Test
    fun `no missing empty line before if block statement when it's in an invisible lambda block in Kotlin`() {
        lint()
            .files(
                TestFiles.kt(
                    """
            package foo

            class Test {
                private fun test(targetScreen: Screen) {
                    targetScreen?.let { targetScreen ->
                        if (targetScreen !is MainScreen) {
                            navigator.navigateBackTo(MainScreen())
                        }
                    }
                }
            }"""
                ).indented()
            )
            .issues(ISSUE_MISSING_EMPTY_LINES_AROUND_BLOCK_STATEMENTS)
            .run()
            .expectClean()
    }

    @Test
    fun `missing empty line between if block statements in Kotlin`() {
        lint()
            .files(
                TestFiles.kt(
                    """
            package foo

            class Test {
                private fun test() {
                    if (fooCondition()) {
                        foo()
                    }
                    if (barCondition) {
                        bar()
                    }
                }
            }"""
                ).indented()
            )
            .issues(ISSUE_MISSING_EMPTY_LINES_AROUND_BLOCK_STATEMENTS)
            .run()
            .expect(
                """
                |src/foo/Test.kt:7: Warning: Block statement isn't followed by empty line. [MissingEmptyLinesAroundBlockStatements]
                |        }
                |         ^
                |src/foo/Test.kt:7: Warning: Block statement isn't preceded by empty line. [MissingEmptyLinesAroundBlockStatements]
                |        }
                |         ^
                |0 errors, 2 warnings
            """.trimMargin()
            )
            .expectFixDiffs(
                """
            |Fix for src/foo/Test.kt line 7: Add extra line:
            |@@ -8 +8
            |+
            |Fix for src/foo/Test.kt line 7: Add extra line:
            |@@ -8 +8
            |+
            |""".trimMargin()
            )
    }

    @Test
    fun `missing empty line around try block statement in Kotlin`() {
        lint()
            .files(
                TestFiles.kt(
                    """
            package foo

            class Test {
                private fun test() {
                    val i = 1
                    try {
                        foo()
                    }
                    catch (e: SomeException) {
                        bar()
                    }
                    val s = ""
                }
            }"""
                ).indented()
            )
            .issues(ISSUE_MISSING_EMPTY_LINES_AROUND_BLOCK_STATEMENTS)
            .run()
            .expect(
                """
                |src/foo/Test.kt:5: Warning: Block statement isn't preceded by empty line. [MissingEmptyLinesAroundBlockStatements]
                |        val i = 1
                |                 ^
                |src/foo/Test.kt:11: Warning: Block statement isn't followed by empty line. [MissingEmptyLinesAroundBlockStatements]
                |        }
                |         ^
                |0 errors, 2 warnings
            """.trimMargin()
            )
            .expectFixDiffs(
                """
                |Fix for src/foo/Test.kt line 5: Add extra line:
                |@@ -6 +6
                |+
                |Fix for src/foo/Test.kt line 11: Add extra line:
                |@@ -12 +12
                |+
            |""".trimMargin()
            )
    }

    @Test
    fun `missing empty line around try block statement with finally in Kotlin`() {
        lint()
            .files(
                TestFiles.kt(
                    """
            package foo

            class Test {
                private fun test() {
                    val i = 1
                    try {
                        foo()
                    }
                    catch (e: SomeException) {
                        bar()
                    }
                    finally {
                        foobar()
                    }
                    val s = ""
                }
            }"""
                ).indented()
            )
            .issues(ISSUE_MISSING_EMPTY_LINES_AROUND_BLOCK_STATEMENTS)
            .run()
            .expect(
                """
                |src/foo/Test.kt:5: Warning: Block statement isn't preceded by empty line. [MissingEmptyLinesAroundBlockStatements]
                |        val i = 1
                |                 ^
                |src/foo/Test.kt:14: Warning: Block statement isn't followed by empty line. [MissingEmptyLinesAroundBlockStatements]
                |        }
                |         ^
                |0 errors, 2 warnings
            """.trimMargin()
            )
            .expectFixDiffs(
                """
                |Fix for src/foo/Test.kt line 5: Add extra line:
                |@@ -6 +6
                |+
                |Fix for src/foo/Test.kt line 14: Add extra line:
                |@@ -15 +15
                |+
            |""".trimMargin()
            )
    }

    @Test
    fun `missing empty line around try block statement with finally in Java`() {
        lint()
            .files(
                TestFiles.java(
                    """
            package foo;

            class Test {
                private fun test() {
                    int i = 1;
                    try {
                        foo();
                    } catch (SomeException e) {
                        bar();
                    } finally {
                        foobar();
                    }
                    String s = "";
                }
            }"""
                ).indented()
            )
            .issues(ISSUE_MISSING_EMPTY_LINES_AROUND_BLOCK_STATEMENTS)
            .run()
            .expect(
                """
                |src/foo/Test.java:5: Warning: Block statement isn't preceded by empty line. [MissingEmptyLinesAroundBlockStatements]
                |        int i = 1;
                |                  ^
                |src/foo/Test.java:12: Warning: Block statement isn't followed by empty line. [MissingEmptyLinesAroundBlockStatements]
                |        }
                |         ^
                |0 errors, 2 warnings
            """.trimMargin()
            )
            .expectFixDiffs(
                """
                |Fix for src/foo/Test.java line 5: Add extra line:
                |@@ -6 +6
                |+
                |Fix for src/foo/Test.java line 12: Add extra line:
                |@@ -13 +13
                |+
            |""".trimMargin()
            )
    }
}
