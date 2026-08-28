package com.uvarov.testapp.ui.catdetail

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class CatDetailDestination(val catId: String) : NavKey
