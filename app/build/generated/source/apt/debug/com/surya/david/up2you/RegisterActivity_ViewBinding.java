// Generated code from Butter Knife. Do not modify!
package com.surya.david.up2you;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RegisterActivity_ViewBinding implements Unbinder {
  private RegisterActivity target;

  private View view2131296308;

  private View view2131296553;

  @UiThread
  public RegisterActivity_ViewBinding(RegisterActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RegisterActivity_ViewBinding(final RegisterActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.btn_reg_user, "field 'reguser' and method 'onViewClicked'");
    target.reguser = Utils.castView(view, R.id.btn_reg_user, "field 'reguser'", Button.class);
    view2131296308 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.tl, "field 'tl' and method 'onDateClick'");
    target.tl = Utils.castView(view, R.id.tl, "field 'tl'", TextView.class);
    view2131296553 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onDateClick();
      }
    });
    target.profileName = Utils.findRequiredViewAsType(source, R.id.profile_name, "field 'profileName'", TextInputEditText.class);
    target.username = Utils.findRequiredViewAsType(source, R.id.username, "field 'username'", TextInputEditText.class);
    target.pass = Utils.findRequiredViewAsType(source, R.id.pass, "field 'pass'", TextInputEditText.class);
    target.confirmPass = Utils.findRequiredViewAsType(source, R.id.confirm_pass, "field 'confirmPass'", EditText.class);
    target.jkL = Utils.findRequiredViewAsType(source, R.id.jk_l, "field 'jkL'", RadioButton.class);
    target.jkP = Utils.findRequiredViewAsType(source, R.id.jk_p, "field 'jkP'", RadioButton.class);
    target.gen = Utils.findRequiredViewAsType(source, R.id.gen, "field 'gen'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RegisterActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.reguser = null;
    target.tl = null;
    target.profileName = null;
    target.username = null;
    target.pass = null;
    target.confirmPass = null;
    target.jkL = null;
    target.jkP = null;
    target.gen = null;

    view2131296308.setOnClickListener(null);
    view2131296308 = null;
    view2131296553.setOnClickListener(null);
    view2131296553 = null;
  }
}
