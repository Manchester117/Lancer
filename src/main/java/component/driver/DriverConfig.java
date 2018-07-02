package component.driver;

import com.alibaba.fastjson.JSONObject;
import component.report.TestListener;
import macaca.client.MacacaClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.Tools;

import java.text.MessageFormat;
import java.util.Objects;

public class DriverConfig {
    private static Logger logger = LogManager.getLogger(DriverConfig.class);

    public static MacacaClient getDriver(String platformName, String app, String port, String udid, String reuse, String waitTimeInterval, String waitTimeout, String delay) {
        JSONObject properties = new JSONObject();
        properties.put("platformName", platformName);
        properties.put("app", app);
        properties.put("udid", udid);
        properties.put("reuse", Integer.parseInt(reuse));
        properties.put("host", "127.0.0.1");
        properties.put("port", Integer.parseInt(port));

        JSONObject desiredCapabilities = new JSONObject();
        desiredCapabilities.put("desiredCapabilities", properties);

        if (Objects.nonNull(delay)) {
            Tools.sleep(Integer.parseInt(delay));
            logger.info(MessageFormat.format("UDID: {0} -- 延迟{1}毫秒启动", udid, delay));
        } else {
            logger.info(MessageFormat.format("UDID: {0} -- 没有设置延迟启动", udid));
        }

        MacacaClient driver = new DriverManager(desiredCapabilities).getDriver();
        driver.setWaitElementTimeInterval(Integer.parseInt(waitTimeInterval));
        driver.setWaitElementTimeout(Integer.parseInt(waitTimeout));
        // 将Driver直接传递给TestListener,用于截图操作
        TestListener.setDriver(driver);
        return driver;
    }
}
