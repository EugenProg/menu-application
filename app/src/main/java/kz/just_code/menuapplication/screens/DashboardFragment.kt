package kz.just_code.menuapplication.screens

import kz.just_code.menuapplication.BaseFragment
import kz.just_code.menuapplication.R

class DashboardFragment: BaseFragment() {
    override fun getTitleRes(): Int = R.string.dashboard
    override fun dropDownVisible(): Boolean = true
    override fun showContextMenu(): Boolean = false
    override fun showPopupMenu(): Boolean = false
}