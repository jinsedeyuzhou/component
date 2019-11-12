package com.ebrightmoon.derobot.reboot.ui.realtime.datasource;

import com.ebrightmoon.derobot.reboot.kit.common.PerformanceDataManager;
import com.ebrightmoon.derobot.reboot.ui.realtime.widget.LineChart;

public class CpuDataSource implements IDataSource {
    @Override
    public LineChart.LineData createData() {
        float rate = PerformanceDataManager.getInstance().getLastCpuRate();
        return LineChart.LineData.obtain(rate, Math.round(rate) + "%");
    }
}
