package component.driver;

import com.alibaba.fastjson.JSONObject;
import macaca.client.MacacaClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DriverManager {
    private static Logger logger = LogManager.getLogger(DriverManager.class);
    private MacacaClient driver;

    /*
    * @description: 初始化MacacaDriver
    * @author:      Griffin
    * @date:        2018/5/4
    * @time:        10:56
    * @param:       Driver的配置参数
    * @return:
    */
    public DriverManager(JSONObject desiredCapabilities){
        driver = new MacacaClient();
        try {
            driver.initDriver(desiredCapabilities);
            logger.info("成功初始化Android Driver");
        } catch (Exception e) {
            logger.warn("设置运行参数异常");
            e.printStackTrace();
        }
    }

    public MacacaClient getDriver() {
        return this.driver;
    }
}
