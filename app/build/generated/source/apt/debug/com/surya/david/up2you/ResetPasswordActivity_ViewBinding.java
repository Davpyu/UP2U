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

public class ResetPasswordActivity_ViewBinding implements Unbinder {
  private ResetPasswordActivity target;

  private View view2131230952;

  @UiThread
  public ResetPasswordActivity_ViewBinding(ResetPasswordActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ResetPasswordActivity_ViewBinding(final ResetPasswordActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.submit_new_password, "field 'newpw' and method 'onViewClick'");
    target.newpw = Utils.castView(view, R.id.submit_new_password, "field 'newpw'", Button.class);
    view2131230952 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClick();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ResetPasswordActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.newpw = null;

    view2131230952.setOnClickListener(null);
    view2131230952 = null;
  }
}
