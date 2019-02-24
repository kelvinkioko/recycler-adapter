package net.gotev.recycleradapterdemo.adapteritems

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_title_subtitle.*
import net.gotev.recycleradapter.AdapterItem
import net.gotev.recycleradapter.RecyclerAdapterNotifier
import net.gotev.recycleradapter.RecyclerAdapterViewHolder
import net.gotev.recycleradapterdemo.R


open class TitleSubtitleItem(private val title: String, private val subtitle: String = "subtitle")
    : AdapterItem<TitleSubtitleItem.Holder>() {

    override fun onFilter(searchTerm: String) = title.contains(searchTerm, ignoreCase = true)

    override fun getLayoutId() = R.layout.item_title_subtitle

    override fun bind(holder: Holder) {
        holder.titleField.text = title
        holder.subtitleField.text = subtitle
    }

    private fun onTitleClicked(context: Context, position: Int) {
        showToast(context, "clicked TITLE at position $position")
    }

    private fun onSubTitleClicked(context: Context, position: Int) {
        showToast(context, "clicked SUBTITLE at position $position")
    }

    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun hasToBeReplacedBy(newItem: AdapterItem<*>) = false

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TitleSubtitleItem) return false

        if (title != other.title) return false
        if (subtitle != other.subtitle) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + subtitle.hashCode()
        return result
    }

    class Holder(itemView: View, adapter: RecyclerAdapterNotifier)
        : RecyclerAdapterViewHolder(itemView, adapter), LayoutContainer {

        override val containerView: View?
            get() = itemView

        internal val titleField: TextView by lazy { title }
        internal val subtitleField: TextView by lazy { subtitle }

        init {
            titleField.setOnClickListener {
                containerView?.context?.let { context ->
                    (getAdapterItem() as? TitleSubtitleItem)?.apply {
                        onTitleClicked(context, adapterPosition)
                    }
                }
            }

            subtitleField.setOnClickListener {
                containerView?.context?.let { context ->
                    (getAdapterItem() as? TitleSubtitleItem)?.apply {
                        onSubTitleClicked(context, adapterPosition)
                    }
                }
            }
        }
    }
}