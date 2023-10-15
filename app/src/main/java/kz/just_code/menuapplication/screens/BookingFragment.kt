package kz.just_code.menuapplication.screens

import kz.just_code.menuapplication.BaseFragment
import kz.just_code.menuapplication.R

class BookingFragment: BaseFragment() {
    override fun getTitleRes(): Int = R.string.booking
    override fun dropDownVisible(): Boolean = false
    override fun showContextMenu(): Boolean = false
    override fun showPopupMenu(): Boolean = true
}