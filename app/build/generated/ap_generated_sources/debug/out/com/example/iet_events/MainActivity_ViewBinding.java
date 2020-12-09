// Generated code from Butter Knife. Do not modify!
package com.example.iet_events;

import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(MainActivity target, View source) {
    this.target = target;

    target.drawer_layout = Utils.findRequiredViewAsType(source, R.id.drawer_layout, "field 'drawer_layout'", DrawerLayout.class);
    target.nav_view = Utils.findRequiredViewAsType(source, R.id.nav_view, "field 'nav_view'", NavigationView.class);
    target.main_toolbar = Utils.findRequiredViewAsType(source, R.id.main_toolbar, "field 'main_toolbar'", Toolbar.class);
    target.qr_code_button = Utils.findRequiredViewAsType(source, R.id.qr_code_button, "field 'qr_code_button'", FloatingActionButton.class);
    target.nav_bottom_lyt_link = Utils.findRequiredViewAsType(source, R.id.nav_bottom_lyt_link, "field 'nav_bottom_lyt_link'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.drawer_layout = null;
    target.nav_view = null;
    target.main_toolbar = null;
    target.qr_code_button = null;
    target.nav_bottom_lyt_link = null;
  }
}
