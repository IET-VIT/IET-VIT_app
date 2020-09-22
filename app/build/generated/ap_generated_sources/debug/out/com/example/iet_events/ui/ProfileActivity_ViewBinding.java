// Generated code from Butter Knife. Do not modify!
package com.example.iet_events.ui;

import android.view.View;
import android.widget.Button;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.iet_events.R;
import com.google.android.material.textfield.TextInputEditText;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ProfileActivity_ViewBinding implements Unbinder {
  private ProfileActivity target;

  @UiThread
  public ProfileActivity_ViewBinding(ProfileActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ProfileActivity_ViewBinding(ProfileActivity target, View source) {
    this.target = target;

    target.profile_image_view = Utils.findRequiredViewAsType(source, R.id.profile_image_view, "field 'profile_image_view'", CircleImageView.class);
    target.profile_name_input = Utils.findRequiredViewAsType(source, R.id.profile_name_input, "field 'profile_name_input'", TextInputEditText.class);
    target.update_button = Utils.findRequiredViewAsType(source, R.id.update_button, "field 'update_button'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ProfileActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.profile_image_view = null;
    target.profile_name_input = null;
    target.update_button = null;
  }
}
