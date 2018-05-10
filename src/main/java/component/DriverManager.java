package component;

import com.alibaba.fastjson.JSONObject;
import macaca.client.MacacaClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Objects;

public class DriverManager {
    private static Logger logger = LogManager.getLogger(DriverManager.class);
    private volatile static DriverManager driverManager;
    private MacacaClient driver;

    /**
    * @description: 对MacacaDriver进行设置
    * @author:      Alex
    * @date:        2018/5/4
    * @time:        10:56
    * @param:       runParams   Driver的配置参数
    * @return:
    */
    private DriverManager(Map<String, Object> runParams){
        driver = new MacacaClient();
        JSONObject properties = new JSONObject();
        properties.put("platformName", runParams.get("platformName").toString());
        properties.put("app", runParams.get("app").toString());
        properties.put("udid", runParams.get("udid").toString());
        properties.put("reuse", Integer.parseInt(runParams.get("reuse").toString()));               // 是否要重新安装APP

        JSONObject desiredCapabilities = new JSONObject();
        desiredCapabilities.put("desiredCapabilities", properties);
        try {
            driver.initDriver(desiredCapabilities);
        } catch (Exception e) {
            logger.warn("设置运行参数异常");
            e.printStackTrace();
        }
    }

    /**
    * @description: Driver的单例模式
    * @author:      Alex
    * @date:        2018/5/4
    * @time:        10:57
    * @param:       runParams   Driver的配置参数
    * @return:      返回DriverManager
    */
    public static DriverManager getDriverManager(Map<String, Object> runParams) {
        if (Objects.isNull(driverManager)) {
            synchronized (DriverManager.class) {
                if (Objects.isNull(driverManager))
                    driverManager = new DriverManager(runParams);
            }
        }
        return driverManager;
    }

    public MacacaClient getDriver() {
        return this.driver;
    }
}
