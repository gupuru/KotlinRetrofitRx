package gupuru.kotlinretrofitsample.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import gupuru.kotlinretrofitsample.R
import gupuru.kotlinretrofitsample.entity.Items
import kotlinx.android.synthetic.main.item_search.view.*
import java.util.*

class SearchAdapter(context: Context, items: ArrayList<Items>) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private val context: Context
    private val items: ArrayList<Items>
    private val onClickListener: View.OnClickListener

    init {
        this.items = items
        this.context = context
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as Items

            val uri: Uri = Uri.parse(item.previewUrl)
            val intent: Intent = Intent(Intent.ACTION_VIEW, uri)
            context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_search, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        Picasso.with(context)
                .load(item.album.images.first().url)
                .into(holder.image)

        holder.name.text = item.name

        with (holder.layout) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.image_music
        val name = view.text_music_name
        val layout = view.item_search

    }

}