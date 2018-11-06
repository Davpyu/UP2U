// Generated code from Butter Knife. Do not modify!
package com.surya.david.up2you;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class EmailConfirmActivity_ViewBinding implements Unbinder {
  private EmailConfirmActivity target;

  private View view2131296306;

  @UiThread
  public EmailConfirmActivity_ViewBinding(EmailConfirmActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public EmailConfirmActivity_ViewBinding(final EmailConfirmActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.btn_back_login, "field 'btnBackLogin' and method 'onViewClicked'");
    target.btnBackLogin = Utils.castView(view, R.id.btn_back_login, "field 'btnBackLogin'", Button.class);
    view2131296306 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    EmailConfirmActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.btnBackLogin = null;

    view2131296306.setOnClickListener(null);
    view2131296306 = null;
  }
}
