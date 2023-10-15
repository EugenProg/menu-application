package kz.just_code.menuapplication.screens

import kz.just_code.menuapplication.BaseFragment
import kz.just_code.menuapplication.R

class DriversFragment: BaseFragment() {
    override fun getTitleRes(): Int = R.string.drivers
    override fun dropDownVisible(): Boolean = false
    override fun showContextMenu(): Boolean = true
    override fun showPopupMenu(): Boolean = false
}