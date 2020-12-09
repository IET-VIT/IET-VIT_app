// Generated code from Butter Knife. Do not modify!
package com.example.iet_events.fragments;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.iet_events.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AdminTaskFragment_ViewBinding implements Unbinder {
  private AdminTaskFragment target;

  @UiThread
  public AdminTaskFragment_ViewBinding(AdminTaskFragment target, View source) {
    this.target = target;

    target.users_recyclerview = Utils.findRequiredViewAsType(source, R.id.users_recyclerview, "field 'users_recyclerview'", RecyclerView.class);
    target.post_task_button = Utils.findRequiredViewAsType(source, R.id.post_task_button, "field 'post_task_button'", Button.class);
    target.admin_task_description = Utils.findRequiredViewAsType(source, R.id.admin_task_description, "field 'admin_task_description'", EditText.class);
    target.admin_task_code = Utils.findRequiredViewAsType(source, R.id.admin_task_code, "field 'admin_task_code'", EditText.class);
    target.name_filter = Utils.findRequiredViewAsType(source, R.id.name_filter, "field 'name_filter'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AdminTaskFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.users_recyclerview = null;
    target.post_task_button = null;
    target.admin_task_description = null;
    target.admin_task_code = null;
    target.name_filter = null;
  }
}
