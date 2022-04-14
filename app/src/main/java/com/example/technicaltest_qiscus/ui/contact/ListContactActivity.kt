package com.example.technicaltest_qiscus.ui.contact

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.technicaltest_qiscus.R
import com.example.technicaltest_qiscus.databinding.ActivityListContactBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListContactActivity : AppCompatActivity(R.layout.activity_list_contact),
    ListContactAdapter.OnContactClickCallback {

    private val binding: ActivityListContactBinding by viewBinding()
    private val contactViewModel: ListContactViewModel by viewModel()
    private var contactAdapter: ListContactAdapter? = null

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, ListContactActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "List Contact"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        contactAdapter = ListContactAdapter(this)

        contactViewModel.getListContact(1, 100, "")

        contactViewModel.listContact.observe(this, { list ->
            if (list != null) {
                if (list.isEmpty()) {
                    binding.tvNoData.visibility = View.VISIBLE
                    binding.rvListContact.visibility = View.GONE
                } else {
                    binding.tvNoData.visibility = View.GONE
                    binding.rvListContact.visibility = View.VISIBLE
                }
                contactAdapter?.addOrUpdate(list)
            }
        })

        with(binding.rvListContact) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@ListContactActivity)
            adapter = contactAdapter
        }

    }

    override fun onContactClicked(position: Int) {
        val chat = contactAdapter?.getData()?.get(position)
        if (chat != null) {
            contactViewModel.createChat(chat)
        }

        contactViewModel.chatUser.observe(this, { user ->
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}