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

  private View view2131230940;

  @UiThread
  public EmailConfirmActivity_ViewBinding(EmailConfirmActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public EmailConfirmActivity_ViewBinding(final EmailConfirmActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.submit_code, "field 'submitcode' and method 'onViewClicked'");
    target.submitcode = Utils.castView(view, R.id.submit_code, "field 'submitcode'", Button.class);
    view2131230940 = view;
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

    target.submitcode = null;

    view2131230940.setOnClickListener(null);
    view2131230940 = null;
  }
}
