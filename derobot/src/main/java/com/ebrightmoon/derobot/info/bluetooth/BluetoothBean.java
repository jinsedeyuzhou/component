package com.ebrightmoon.derobot.info.bluetooth;

import android.util.Log;

import com.ebrightmoon.derobot.info.base.BaseBean;
import com.ebrightmoon.derobot.info.base.BaseData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 *
 */
public class BluetoothBean extends BaseBean {
    private static final String TAG = BluetoothBean.class.getSimpleName();

    /**
     * 蓝牙地址
     */
    private String bluetoothAddress;

    /**
     * 蓝牙是否打开
     */
    private String isEnabled;

    /**
     * 连接的手机的信息
     */
    private List<JSONObject> device;

    /**
     * 手机设置的名字
     */
    private String phoneName;

    public String getBluetoothAddress() {
        return bluetoothAddress;
    }

    public void setBluetoothAddress(String bluetoothAddress) {
        this.bluetoothAddress = bluetoothAddress;
    }

    public String getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled;
    }

    public List<JSONObject> getDevice() {
        return device;
    }

    public void setDevice(List<JSONObject> device) {
        this.device = device;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    @Override
    protected JSONObject toJSONObject() {
        try {
            jsonObject.put(BaseData.Bluetooth.BLUETOOTH_ADDRESS, isEmpty(bluetoothAddress));
            jsonObject.put(BaseData.Bluetooth.IS_ENABLED, isEmpty(isEnabled));
            jsonObject.put(BaseData.Bluetooth.DEVICE, new JSONArray(device));
            jsonObject.put(BaseData.Bluetooth.PHONE_NAME, isEmpty(phoneName));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.toJSONObject();
    }

    public static class DeviceBean extends BaseBean {

        /**
         * 连接手机的蓝牙地址
         */
        private String address;

        /**
         * 连接手机的蓝牙名字
         */
        private String name;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        protected JSONObject toJSONObject() {
            try {
                jsonObject.put(BaseData.Bluetooth.Device.NAME, isEmpty(name));
                jsonObject.put(BaseData.Bluetooth.Device.ADDRESS, isEmpty(address));
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            return super.toJSONObject();
        }
    }
}
