package com.an7one.officialdoc.jetpackCompose.codelab.layout

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity

class DemoLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // 03. Modifiers
            // PhotographerCard()

            /*
            // 05. Material Components
            LayoutsCodelabTheme {
                LayoutsCodelab()
            }*/

            /*
            // 06. Working with Lists
            // SimpleList()
            // LazyList()
            // ImageList()
            ScrollingList()
             */

            /*
            // 07.Create One's Own Custom Layout
            BodyContentCustomLayout()
             */

            /*
            // 08 Complex Custom Layout
            BodyContentComplexLayout()
             */

            /*
            // 09 Layout Modifiers Under the Hood
            BodyContentLayoutModifier()
             */

            /*
            // 10 Constraint Layout
            // ConstraintLayoutContent()
            // LargeConstraintLayout()
            DecoupledConstraintLayout()
             */

            // 11 Intrinsics
            TwoTexts(text1 = "Hi", text2 = "there")
        }
    }
}
