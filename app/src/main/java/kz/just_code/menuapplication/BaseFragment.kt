package kz.just_code.menuapplication

import android.os.Bundle
import android.view.ContextMenu
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ListPopupWindow
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import kz.just_code.menuapplication.databinding.FragmentBaseBinding

abstract class BaseFragment : Fragment() {
    private lateinit var binding: FragmentBaseBinding

    @StringRes
    abstract fun getTitleRes(): Int
    abstract fun dropDownVisible(): Boolean
    abstract fun showContextMenu(): Boolean
    abstract fun showPopupMenu(): Boolean

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setTitle(getTitleRes())

        binding.toolbar.setOnMenuItemClickListener {
            Toast.makeText(requireContext(), it.title, Toast.LENGTH_SHORT).show()
            true
        }

        setUpDropDownMenu()
        setUpContextMenu()
        setUpPopupMenu()
        setUpListPopupMenu()
    }

    private fun setUpListPopupMenu() {
        val listPopupMenu = ListPopupWindow(requireContext(), null, com.google.android.material.R.attr.listPopupWindowStyle)

        listPopupMenu.anchorView = binding.listPopupMenuView

        val items = listOf("Option 1", "Option 2", "Option 3", "Option 4")
        val adapter = ArrayAdapter(requireContext(), R.layout.item_menu, items)
        listPopupMenu.setAdapter(adapter)

        listPopupMenu.setOnItemClickListener { adapterView, view, i, l ->
            listPopupMenu.dismiss()
        }

        listPopupMenu.setOnDismissListener {
            Toast.makeText(this.context, "Dismiss list popup menu", Toast.LENGTH_SHORT).show()
        }

        binding.listPopupMenuView.setOnClickListener {
            listPopupMenu.show()
        }
    }

    private fun setUpPopupMenu() {
        binding.popupMenuView.isVisible = showPopupMenu()
        if (showPopupMenu()) {
            binding.popupMenuView.setUpPopupMenu {
                Toast.makeText(this.context, "Clicked position: $it", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUpContextMenu() {
        binding.contextMenuView.isVisible = showContextMenu()
        if (showContextMenu()) {
            registerForContextMenu(binding.contextMenuView)
            binding.contextMenuView.setOnClickListener {
                it.showContextMenu()
            }
        }
    }

    private fun setUpDropDownMenu() {
        binding.dropdownContainer.isVisible = dropDownVisible()
        if (dropDownVisible()) {
            val items = listOf("Option 1", "Option 2", "Option 3", "Option 4")

            binding.dropdownMenu.setUpDropdownMenu(items)
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = requireActivity().menuInflater
        inflater.inflate(R.menu.popup_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return if (item.itemId in listOf(R.id.option_1, R.id.option_2, R.id.option_3)) {
            Toast.makeText(requireContext(), item.title, Toast.LENGTH_SHORT).show()
            true
        } else super.onContextItemSelected(item)
    }
}

fun AutoCompleteTextView.setUpDropdownMenu(itemsList: List<String>) {
    val adapter = ArrayAdapter(this.context, R.layout.item_menu, itemsList)
    this.setAdapter(adapter)
}

fun View.setUpPopupMenu(itemClick: ((position:Int) -> Unit)? = null) {
    val popup = PopupMenu(this.context, this, Gravity.START)
    popup.menuInflater.inflate(R.menu.popup_menu, popup.menu)

    popup.setOnDismissListener {
        Toast.makeText(this.context, "Dismiss popup menu", Toast.LENGTH_SHORT).show()
    }

    popup.setOnMenuItemClickListener {
        itemClick?.invoke(it.itemId)
        true
    }

    this.setOnClickListener {
        popup.show()
    }
}