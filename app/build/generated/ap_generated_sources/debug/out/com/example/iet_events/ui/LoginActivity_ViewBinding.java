// Generated code from Butter Knife. Do not modify!
package com.example.iet_events.ui;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.iet_events.R;
import com.google.android.material.textfield.TextInputEditText;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target, View source) {
    this.target = target;

    target.all_main_login_layout = Utils.findRequiredViewAsType(source, R.id.all_main_login_layout, "field 'all_main_login_layout'", ConstraintLayout.class);
    target.email_login_layout = Utils.findRequiredViewAsType(source, R.id.email_login_layout, "field 'email_login_layout'", ConstraintLayout.class);
    target.emailLogin = Utils.findRequiredViewAsType(source, R.id.emailLogin, "field 'emailLogin'", TextView.class);
    target.googleLogin = Utils.findRequiredViewAsType(source, R.id.googleLogin, "field 'googleLogin'", TextView.class);
    target.githubLogin = Utils.findRequiredViewAsType(source, R.id.githubLogin, "field 'githubLogin'", TextView.class);
    target.email_login_input = Utils.findRequiredViewAsType(source, R.id.email_login_input, "field 'email_login_input'", TextInputEditText.class);
    target.password_login_input = Utils.findRequiredViewAsType(source, R.id.password_login_input, "field 'password_login_input'", TextInputEditText.class);
    target.mail_login_button = Utils.findRequiredViewAsType(source, R.id.mail_login_button, "field 'mail_login_button'", Button.class);
    target.go_to_register = Utils.findRequiredViewAsType(source, R.id.go_to_register, "field 'go_to_register'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.all_main_login_layout = null;
    target.email_login_layout = null;
    target.emailLogin = null;
    target.googleLogin = null;
    target.githubLogin = null;
    target.email_login_input = null;
    target.password_login_input = null;
    target.mail_login_button = null;
    target.go_to_register = null;
  }
}
