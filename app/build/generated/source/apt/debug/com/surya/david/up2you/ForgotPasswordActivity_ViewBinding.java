// Generated code from Butter Knife. Do not modify!
package com.surya.david.up2you;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ForgotPasswordActivity_ViewBinding implements Unbinder {
  private ForgotPasswordActivity target;

  private View view2131296533;

  @UiThread
  public ForgotPasswordActivity_ViewBinding(ForgotPasswordActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ForgotPasswordActivity_ViewBinding(final ForgotPasswordActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.submit_email, "field 'submitemail' and method 'onViewClicked'");
    target.submitemail = Utils.castView(view, R.id.submit_email, "field 'submitemail'", Button.class);
    view2131296533 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
    target.email = Utils.findRequiredViewAsType(source, R.id.email, "field 'email'", TextInputEditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ForgotPasswordActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.submitemail = null;
    target.email = null;

    view2131296533.setOnClickListener(null);
    view2131296533 = null;
  }
}
