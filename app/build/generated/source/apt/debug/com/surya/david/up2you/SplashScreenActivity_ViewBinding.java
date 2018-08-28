// Generated code from Butter Knife. Do not modify!
package com.surya.david.up2you;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SplashScreenActivity_ViewBinding implements Unbinder {
  private SplashScreenActivity target;

  @UiThread
  public SplashScreenActivity_ViewBinding(SplashScreenActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SplashScreenActivity_ViewBinding(SplashScreenActivity target, View source) {
    this.target = target;

    target.logo = Utils.findRequiredViewAsType(source, R.id.logo, "field 'logo'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SplashScreenActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.logo = null;
  }
}
