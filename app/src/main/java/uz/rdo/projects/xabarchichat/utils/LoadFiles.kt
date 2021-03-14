package uz.rdo.projects.xabarchichat.utils

import android.widget.ImageView
import com.squareup.picasso.Picasso


fun loadImageForURL(url: String, img_container: ImageView) {
    Picasso.get().load(url).into(img_container)
}