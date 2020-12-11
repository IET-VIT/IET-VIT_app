// Generated code from Butter Knife. Do not modify!
package com.example.iet_events.ui;

import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.iet_events.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AdminActivity_ViewBinding implements Unbinder {
  private AdminActivity target;

  @UiThread
  public AdminActivity_ViewBinding(AdminActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AdminActivity_ViewBinding(AdminActivity target, View source) {
    this.target = target;

    target.add_tasks_card = Utils.findRequiredViewAsType(source, R.id.add_tasks_card, "field 'add_tasks_card'", CardView.class);
    target.check_tasks_card = Utils.findRequiredViewAsType(source, R.id.check_tasks_card, "field 'check_tasks_card'", CardView.class);
    target.meeting_card = Utils.findRequiredViewAsType(source, R.id.meeting_card, "field 'meeting_card'", CardView.class);
    target.admin_frame = Utils.findRequiredViewAsType(source, R.id.admin_frame, "field 'admin_frame'", FrameLayout.class);
    target.admin_main_layout = Utils.findRequiredViewAsType(source, R.id.admin_main_layout, "field 'admin_main_layout'", ConstraintLayout.class);
    target.admin_toolbar = Utils.findRequiredViewAsType(source, R.id.admin_toolbar, "field 'admin_toolbar'", Toolbar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AdminActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.add_tasks_card = null;
    target.check_tasks_card = null;
    target.meeting_card = null;
    target.admin_frame = null;
    target.admin_main_layout = null;
    target.admin_toolbar = null;
  }
}
