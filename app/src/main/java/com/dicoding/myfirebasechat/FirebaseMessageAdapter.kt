package com.dicoding.myfirebasechat

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.myfirebasechat.databinding.ItemMessageBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import kotlinx.coroutines.NonDisposableHandle.parent
import org.w3c.dom.Text

class FirebaseMessageAdapter(options : FirebaseRecyclerOptions<Message>, private val currentUserName : String? ) :
    FirebaseRecyclerAdapter<Message, FirebaseMessageAdapter.MessageViewHolder>(options) {

    inner class MessageViewHolder(private val binding : ItemMessageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Message) {
            var check = checkAuthority(item.name, binding.tvMessage)

            //Set Direction
            if(check) {
                setChatDirection(true, binding.ivMessenger2, binding.tvMessenger2, binding.tvMessage2, binding.tvTimestamp2)
                setChatDirection(false, binding.ivMessenger, binding.tvMessenger, binding.tvMessage, binding.tvTimestamp)

                binding.tvMessage2.text = item.text
                binding.tvMessenger2.text = item.name
                Glide.with(itemView.context)
                    .load(item.photoUrl)
                    .circleCrop()
                    .into(binding.ivMessenger2)

                if (item.timestamp != null) {
                    binding.tvTimestamp2.text = DateUtils.getRelativeTimeSpanString(item.timestamp)
                }

            } else {
                setChatDirection(false, binding.ivMessenger2, binding.tvMessenger2, binding.tvMessage2, binding.tvTimestamp2)
                setChatDirection(true, binding.ivMessenger, binding.tvMessenger, binding.tvMessage, binding.tvTimestamp)

                binding.tvMessage.text = item.text
                binding.tvMessenger.text = item.name
                Glide.with(itemView.context)
                    .load(item.photoUrl)
                    .circleCrop()
                    .into(binding.ivMessenger)

                if (item.timestamp != null) {
                    binding.tvTimestamp.text = DateUtils.getRelativeTimeSpanString(item.timestamp)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        val binding = ItemMessageBinding.bind(view)
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int, model: Message) {
        holder.bind(model)
    }

    private fun checkAuthority(userName: String?, textView: TextView) : Boolean {
        if (currentUserName == userName && userName != null) {
            return true
        }
        return false
    }

    private fun setChatDirection(check : Boolean, imageView  : ImageView, textView: TextView, textView2 : TextView, textView3 : TextView) {
        if(check) {
            imageView.alpha = 1F
            textView.alpha = 1F
            textView2.alpha = 1F
            textView3.alpha = 1F
        } else {
            imageView.alpha = 0F
            textView.alpha = 0F
            textView2.alpha = 0F
            textView3.alpha = 0F
        }
    }
}