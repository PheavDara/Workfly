package com.dara.finderjob

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dara.finderjob.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding

    // Define constants for item types
    private companion object {
        const val TYPE_ARROW = 1
        const val TYPE_SWITCH = 2
    }

    private val settingsList = listOf(
        SettingItem("Edit profile", R.drawable.ic_edit, TYPE_ARROW),
        SettingItem("Update your resume", R.drawable.ic_update, TYPE_ARROW),
        SettingItem("Create your resume", R.drawable.ic_added, TYPE_ARROW),
        SettingItem("Notification", R.drawable.ic_bell, TYPE_SWITCH, true),
        SettingItem("Dark Mode", R.drawable.ic_dark_mode, TYPE_SWITCH, false),
        SettingItem("Password", R.drawable.ic_lock, TYPE_ARROW),
        SettingItem("Language", R.drawable.ic_language, TYPE_ARROW),
        SettingItem("Logout", R.drawable.ic_logout, TYPE_ARROW)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Setup RecyclerView
        binding.settingsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.settingsRecyclerView.adapter = SettingsAdapter(settingsList)
    }

    data class SettingItem(
        val title: String,
        val iconResId: Int,
        val type: Int,  // 1 for arrow, 2 for switch
        val switchState: Boolean = false
    )

    class SettingsAdapter(private val settingsList: List<SettingItem>) :
        RecyclerView.Adapter<SettingsAdapter.ViewHolder>() {

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val settingIcon: ImageView = itemView.findViewById(R.id.settingIcon)
            val settingTitle: TextView = itemView.findViewById(R.id.settingTitle)
            @SuppressLint("UseSwitchCompatOrMaterialCode")
            val settingSwitch: Switch = itemView.findViewById(R.id.settingSwitch)
            val settingArrow: ImageView = itemView.findViewById(R.id.settingArrow)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_setting, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = settingsList[position]

            // Common elements
            holder.settingIcon.setImageResource(item.iconResId)
            holder.settingTitle.text = item.title

            // Handle different item types
            when (item.type) {
                TYPE_ARROW -> {
                    holder.settingArrow.visibility = View.VISIBLE
                    holder.settingSwitch.visibility = View.GONE
                }
                TYPE_SWITCH -> {
                    holder.settingArrow.visibility = View.GONE
                    holder.settingSwitch.visibility = View.VISIBLE
                    holder.settingSwitch.isChecked = item.switchState
                }
            }

            // Handle item clicks if needed
            holder.itemView.setOnClickListener {
                // Handle item click here
            }
        }

        override fun getItemCount(): Int = settingsList.size
    }
}