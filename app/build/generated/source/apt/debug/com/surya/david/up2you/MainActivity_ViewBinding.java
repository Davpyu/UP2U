// Generated code from Butter Knife. Do not modify!
package com.surya.david.up2you;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  private View view2131230761;

  private View view2131230766;

  private View view2131230763;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(final MainActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.btn_login, "field 'btnlogin' and method 'onViewClicked'");
    target.btnlogin = Utils.castView(view, R.id.btn_login, "field 'btnlogin'", TextView.class);
    view2131230761 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_register, "field 'btnreg' and method 'onClick'");
    target.btnreg = Utils.castView(view, R.id.btn_register, "field 'btnreg'", TextView.class);
    view2131230766 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_loginasguest, "field 'btnloginguest' and method 'onClicked'");
    target.btnloginguest = Utils.castView(view, R.id.btn_loginasguest, "field 'btnloginguest'", TextView.class);
    view2131230763 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClicked();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.btnlogin = null;
    target.btnreg = null;
    target.btnloginguest = null;

    view2131230761.setOnClickListener(null);
    view2131230761 = null;
    view2131230766.setOnClickListener(null);
    view2131230766 = null;
    view2131230763.setOnClickListener(null);
    view2131230763 = null;
  }
}
