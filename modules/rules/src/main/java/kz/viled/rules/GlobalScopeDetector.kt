package kz.viled.rules

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import org.jetbrains.uast.UClass
import org.jetbrains.uast.UElement
import org.jetbrains.uast.getUCallExpression
import org.jetbrains.uast.toUElementOfType
import java.util.*

class GlobalScopeDetector : Detector(), Detector.UastScanner {

    override fun getApplicableUastTypes(): List<Class<out UElement>> = listOf(UClass::class.java)

    override fun createUastHandler(context: JavaContext): UElementHandler =
        GlobalScopeVisitor(context)


    class GlobalScopeVisitor(private val context: JavaContext) : UElementHandler() {

        override fun visitClass(node: UClass) {
            super.visitClass(node)


//            if ("kotlinx.coroutines.GlobalScope" == node.references()
//                    ?.getExpressionType()?.canonicalText
//            ) {
//                context.report(
//                    ISSUE,
//                    node,
//                    context.getLocation(node.containingFile),
//                    "GLOBAL SCOPE is found"
//                )
//            }

            val globalScopes = node.references
                .filter {
                    "kotlinx.coroutines.GlobalScope" == it.canonicalText }
                .toMutableSet()

            globalScopes.forEach {
                context.report(ISSUE, it.element, context.getLocation(it.element), "GLOBAL SCOPE is found")
            }
        }
    }

    companion object {
        private val IMPLEMENTATION = Implementation(
            GlobalScopeDetector::class.java,
            EnumSet.of(Scope.JAVA_FILE)
        )

        val ISSUE: Issue = Issue
            .create(
                id = "GlobalScopeDetector",
                briefDescription = "GlobalScope is not good solution",
                explanation = "Use viewModelScope or lifecycleScope".trimIndent(),
                category = Category.CORRECTNESS,
                priority = 10,
                severity = Severity.ERROR,
                androidSpecific = false,
                implementation = IMPLEMENTATION
            )
    }
}