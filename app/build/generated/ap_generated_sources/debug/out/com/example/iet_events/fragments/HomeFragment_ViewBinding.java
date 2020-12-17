// Generated code from Butter Knife. Do not modify!
package com.example.iet_events.fragments;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.airbnb.lottie.LottieAnimationView;
import com.example.iet_events.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class HomeFragment_ViewBinding implements Unbinder {
  private HomeFragment target;

  @UiThread
  public HomeFragment_ViewBinding(HomeFragment target, View source) {
    this.target = target;

    target.tasks_status_text = Utils.findRequiredViewAsType(source, R.id.tasks_status_text, "field 'tasks_status_text'", TextView.class);
    target.no_meeting_text = Utils.findRequiredViewAsType(source, R.id.no_meeting_text, "field 'no_meeting_text'", TextView.class);
    target.task_number_text = Utils.findRequiredViewAsType(source, R.id.task_number_text, "field 'task_number_text'", TextView.class);
    target.animationView = Utils.findRequiredViewAsType(source, R.id.animationView, "field 'animationView'", LottieAnimationView.class);
    target.loadingAnimationTask = Utils.findRequiredViewAsType(source, R.id.loadingAnimationTask, "field 'loadingAnimationTask'", LottieAnimationView.class);
    target.loadingAnimationMeeting = Utils.findRequiredViewAsType(source, R.id.loadingAnimationMeeting, "field 'loadingAnimationMeeting'", LottieAnimationView.class);
    target.meeting_recycler_view = Utils.findRequiredViewAsType(source, R.id.meeting_recycler_view, "field 'meeting_recycler_view'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    HomeFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tasks_status_text = null;
    target.no_meeting_text = null;
    target.task_number_text = null;
    target.animationView = null;
    target.loadingAnimationTask = null;
    target.loadingAnimationMeeting = null;
    target.meeting_recycler_view = null;
  }
}
