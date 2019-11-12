package com.ebrightmoon.derobot.reboot.ui.widget.tableview.intface;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.ebrightmoon.derobot.reboot.ui.widget.tableview.TableConfig;
import com.ebrightmoon.derobot.reboot.ui.widget.tableview.bean.Column;


public interface ITitleDrawFormat {

    /**
     *测量宽
     */
    int measureWidth(Column column, TableConfig config);

    /**
     *测量高
     */
    int measureHeight(TableConfig config);

    /**
     * 绘制
     * @param c 画笔
     * @param column 列信息
     */
    void draw(Canvas c, Column column, Rect rect, TableConfig config);


}
