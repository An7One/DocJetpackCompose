/**
 * to be completed
 */
package com.an7one.officialDoc.jetpackCompose.codeLab.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.an7one.officialDoc.jetpackCompose.codeLab.viewModel.ViewModelHelloCodeLab

class HelloCodeLabViewModelActivity : AppCompatActivity() {
    private val helloCodeLabViewModel by viewModels<ViewModelHelloCodeLab>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*...*/

        helloCodeLabViewModel.name.observe(this) { name ->

        }
    }
}