package news.around.theworld.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import news.around.theworld.R

fun ImageView.loadImageFromUrl(url: String?){
    if(url == null){
        Glide.with(this)
            .load(R.drawable.img_not_available)
            .override(1280,768)
            .into(this)
    }else {
        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.img_not_available)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .override(1280,768)
            .into(this)
    }
}