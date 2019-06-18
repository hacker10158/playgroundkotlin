package com.troy.playgroundkotlin.core.utility

import android.net.Uri
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.request.ImageRequestBuilder

class FrescoHelper {
    companion object {
        fun loadInto(url : String, draweeView : SimpleDraweeView) {
            val uri = Uri.parse(url)
            val request = ImageRequestBuilder.newBuilderWithSource(uri)
                .build()

            val controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(draweeView.controller)
                .setTapToRetryEnabled(true)
                .build()

            draweeView.controller = controller
        }
    }
}