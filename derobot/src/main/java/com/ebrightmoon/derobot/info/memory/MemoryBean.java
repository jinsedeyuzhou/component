package com.ebrightmoon.derobot.info.memory;

import android.util.Log;

import com.ebrightmoon.derobot.info.base.BaseBean;
import com.ebrightmoon.derobot.info.base.BaseData;

import org.json.JSONObject;

/**
 *
 */
public class MemoryBean extends BaseBean {
    private static final String TAG = MemoryBean.class.getSimpleName();

    /**
     * ram total
     */
    private String ramMemory;

    /**
     * ram  Available
     */
    private String ramAvailMemory;

    /**
     * rom Available
     */
    private String romMemoryAvailable;

    /**
     * rom total
     */
    private String romMemoryTotal;

    /**
     * sdcard Available
     */
    private String sdCardMemoryAvailable;

    /**
     * sdcard total
     */
    private String sdCardMemoryTotal;

    public String getRamMemory() {
        return ramMemory;
    }

    public void setRamMemory(String ramMemory) {
        this.ramMemory = ramMemory;
    }

    public String getRamAvailMemory() {
        return ramAvailMemory;
    }

    public void setRamAvailMemory(String ramAvailMemory) {
        this.ramAvailMemory = ramAvailMemory;
    }

    public String getRomMemoryAvailable() {
        return romMemoryAvailable;
    }

    public void setRomMemoryAvailable(String romMemoryAvailable) {
        this.romMemoryAvailable = romMemoryAvailable;
    }

    public String getRomMemoryTotal() {
        return romMemoryTotal;
    }

    public void setRomMemoryTotal(String romMemoryTotal) {
        this.romMemoryTotal = romMemoryTotal;
    }

    public String getSdCardMemoryAvailable() {
        return sdCardMemoryAvailable;
    }

    public void setSdCardMemoryAvailable(String sdCardMemoryAvailable) {
        this.sdCardMemoryAvailable = sdCardMemoryAvailable;
    }

    public String getSdCardMemoryTotal() {
        return sdCardMemoryTotal;
    }

    public void setSdCardMemoryTotal(String sdCardMemoryTotal) {
        this.sdCardMemoryTotal = sdCardMemoryTotal;
    }

    @Override
    protected JSONObject toJSONObject() {
        try {
            jsonObject.put(BaseData.Memory.RAM_MEMORY, isEmpty(ramMemory));
            jsonObject.put(BaseData.Memory.RAM_AVAIL_MEMORY, isEmpty(ramAvailMemory));
            jsonObject.put(BaseData.Memory.ROM_MEMORY_AVAILABLE, isEmpty(romMemoryAvailable));
            jsonObject.put(BaseData.Memory.ROM_MEMORY_TOTAL, isEmpty(romMemoryTotal));
            jsonObject.put(BaseData.Memory.SDCARD_MEMORY_AVAILABLE, isEmpty(sdCardMemoryAvailable));
            jsonObject.put(BaseData.Memory.SDCARD_MEMORY_TOTAL, isEmpty(sdCardMemoryTotal));
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return super.toJSONObject();
    }
}
