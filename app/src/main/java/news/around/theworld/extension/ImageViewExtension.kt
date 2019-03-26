package news.around.theworld.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import news.around.theworld.R

fun ImageView.loadImageFromUrl(url: String?){
    if(url == null){
        Glide.with(this)
            .load(R.drawable.img_not_available)
            .into(this)
    }else {
        Glide.with(this)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(this)
    }
}