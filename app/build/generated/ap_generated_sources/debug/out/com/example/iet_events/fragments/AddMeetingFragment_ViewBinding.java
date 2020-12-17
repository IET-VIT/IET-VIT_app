// Generated code from Butter Knife. Do not modify!
package com.example.iet_events.fragments;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.iet_events.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddMeetingFragment_ViewBinding implements Unbinder {
  private AddMeetingFragment target;

  @UiThread
  public AddMeetingFragment_ViewBinding(AddMeetingFragment target, View source) {
    this.target = target;

    target.date_select_btn = Utils.findRequiredViewAsType(source, R.id.date_select_btn, "field 'date_select_btn'", TextView.class);
    target.time_select_btn = Utils.findRequiredViewAsType(source, R.id.time_select_btn, "field 'time_select_btn'", TextView.class);
    target.meeting_desc_text = Utils.findRequiredViewAsType(source, R.id.meeting_desc_text, "field 'meeting_desc_text'", EditText.class);
    target.loc_link_text = Utils.findRequiredViewAsType(source, R.id.loc_link_text, "field 'loc_link_text'", EditText.class);
    target.add_meeting_btn = Utils.findRequiredViewAsType(source, R.id.add_meeting_btn, "field 'add_meeting_btn'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AddMeetingFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.date_select_btn = null;
    target.time_select_btn = null;
    target.meeting_desc_text = null;
    target.loc_link_text = null;
    target.add_meeting_btn = null;
  }
}
