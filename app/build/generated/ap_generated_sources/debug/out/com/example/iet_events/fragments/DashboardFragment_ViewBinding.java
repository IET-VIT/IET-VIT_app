// Generated code from Butter Knife. Do not modify!
package com.example.iet_events.fragments;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.iet_events.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DashboardFragment_ViewBinding implements Unbinder {
  private DashboardFragment target;

  @UiThread
  public DashboardFragment_ViewBinding(DashboardFragment target, View source) {
    this.target = target;

    target.tasks_recycler_view = Utils.findRequiredViewAsType(source, R.id.tasks_recycler_view, "field 'tasks_recycler_view'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DashboardFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tasks_recycler_view = null;
  }
}
