package net.kdt.pojavlaunch.utils;

import java.util.Map;
import top.defaults.checkerboarddrawable.BuildConfig;

public class JSONUtils {
    public static String[] insertJSONValueList(String[] args, Map<String, String> keyValueMap) {
        for (int i = 0; i < args.length; i++) {
            args[i] = insertSingleJSONValue(args[i], keyValueMap);
        }
        return args;
    }

    public static String insertSingleJSONValue(String value, Map<String, String> keyValueMap) {
        String valueInserted = value;
        for (Map.Entry<String, String> keyValue : keyValueMap.entrySet()) {
            valueInserted = valueInserted.replace("${" + keyValue.getKey() + "}", keyValue.getValue() == null ? BuildConfig.FLAVOR : keyValue.getValue());
        }
        return valueInserted;
    }
}
