package com.example.contactsapp.contactlistscreen.ui

import com.bumptech.glide.Glide
import com.example.contactsapp.R
import com.example.contactsapp.base.Item
import com.example.contactsapp.model.ContactModel
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import kotlinx.android.synthetic.main.item_contact.view.*

fun contactAdapterDelegate(onClick: (Int) -> Unit): AdapterDelegate<List<Item>> =
    adapterDelegateLayoutContainer<ContactModel, Item>(R.layout.item_contact) {

        //Kotlin synthetic not working without that for some reason
        itemView.apply {
            setOnClickListener { onClick(adapterPosition) }

            bind {
                tvName.text = item.name
                tvPhoneNumber.text = item.phoneNumber
                Glide.with(itemView)
                    .load(item.imageUrl ?: R.drawable.ic_empty_avatar)
                    .circleCrop()
                    .into(ivAvatar)
            }
        }
    }
