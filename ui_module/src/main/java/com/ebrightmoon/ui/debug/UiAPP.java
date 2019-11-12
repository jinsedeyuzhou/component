package com.ebrightmoon.ui.debug;

import com.ebrightmoon.commonlogic.base.ConApp;
import com.ebrightmoon.derobot.reboot.Derobot;

public class UiAPP extends ConApp {
    @Override
    public void onCreate() {
        super.onCreate();
        Derobot.install(this);
    }
}
