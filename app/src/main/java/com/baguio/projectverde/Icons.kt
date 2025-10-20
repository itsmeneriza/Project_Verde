package com.baguio.projectverde

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource

object AppIcons

val AppIcons.QrCode: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.qrcode)
