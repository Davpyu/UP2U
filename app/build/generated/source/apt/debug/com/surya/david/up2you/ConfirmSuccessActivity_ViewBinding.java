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

public class ConfirmSuccessActivity_ViewBinding implements Unbinder {
  private ConfirmSuccessActivity target;

  private View view2131230757;

  @UiThread
  public ConfirmSuccessActivity_ViewBinding(ConfirmSuccessActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ConfirmSuccessActivity_ViewBinding(final ConfirmSuccessActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.btn_back_login, "field 'backlogin' and method 'onViewClicked'");
    target.backlogin = Utils.castView(view, R.id.btn_back_login, "field 'backlogin'", Button.class);
    view2131230757 = view;
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
    ConfirmSuccessActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.backlogin = null;

    view2131230757.setOnClickListener(null);
    view2131230757 = null;
  }
}
