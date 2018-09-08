// Generated code from Butter Knife. Do not modify!
package com.surya.david.up2you;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class HomeActivity_ViewBinding implements Unbinder {
  private HomeActivity target;

  @UiThread
  public HomeActivity_ViewBinding(HomeActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public HomeActivity_ViewBinding(HomeActivity target, View source) {
    this.target = target;

    target.drawerLayout = Utils.findRequiredViewAsType(source, R.id.drawer_layout, "field 'drawerLayout'", DrawerLayout.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.navigationView = Utils.findRequiredViewAsType(source, R.id.nav_view, "field 'navigationView'", NavigationView.class);
    target.bnv = Utils.findRequiredViewAsType(source, R.id.bnv, "field 'bnv'", BottomNavigationView.class);
    target.bsSort = Utils.findRequiredViewAsType(source, R.id.bs_sort, "field 'bsSort'", LinearLayout.class);
    target.bsFilter = Utils.findRequiredViewAsType(source, R.id.bs_filter, "field 'bsFilter'", LinearLayout.class);
    target.bsSearch = Utils.findRequiredViewAsType(source, R.id.bs_search, "field 'bsSearch'", LinearLayout.class);
    target.coLayout = Utils.findRequiredViewAsType(source, R.id.co_layout, "field 'coLayout'", CoordinatorLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    HomeActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.drawerLayout = null;
    target.toolbar = null;
    target.navigationView = null;
    target.bnv = null;
    target.bsSort = null;
    target.bsFilter = null;
    target.bsSearch = null;
    target.coLayout = null;
  }
}
