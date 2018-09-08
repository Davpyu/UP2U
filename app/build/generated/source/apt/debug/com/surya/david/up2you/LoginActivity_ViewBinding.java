// Generated code from Butter Knife. Do not modify!
package com.surya.david.up2you;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  private View view2131230818;

  private View view2131230765;

  private View view2131230899;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(final LoginActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.forgot_pass, "field 'forgotpass' and method 'onViewClick'");
    target.forgotpass = Utils.castView(view, R.id.forgot_pass, "field 'forgotpass'", Button.class);
    view2131230818 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_loginui, "field 'btnLoginui' and method 'onViewClicked'");
    target.btnLoginui = Utils.castView(view, R.id.btn_loginui, "field 'btnLoginui'", Button.class);
    view2131230765 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.register, "field 'register' and method 'onViewClicke'");
    target.register = Utils.castView(view, R.id.register, "field 'register'", TextView.class);
    view2131230899 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicke();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.forgotpass = null;
    target.btnLoginui = null;
    target.register = null;

    view2131230818.setOnClickListener(null);
    view2131230818 = null;
    view2131230765.setOnClickListener(null);
    view2131230765 = null;
    view2131230899.setOnClickListener(null);
    view2131230899 = null;
  }
}
