package testcase;

import component.DriverManager;
import component.report.TestListener;
import macaca.client.MacacaClient;

import java.util.HashMap;
import java.util.Map;

public class CaseConfigAction {
    public static MacacaClient getDriver(String platformName, String app, String udid, String reuse, String waitTimeInterval, String waitTimeout) {
        Map<String, Object> runParams = new HashMap<>();
        runParams.put("platformName", platformName);
        runParams.put("app", app);
        runParams.put("udid", udid);
        runParams.put("reuse", reuse);

        MacacaClient driver = DriverManager.getDriverManager(runParams).getDriver();
        driver.setWaitElementTimeInterval(Integer.parseInt(waitTimeInterval));
        driver.setWaitElementTimeout(Integer.parseInt(waitTimeout));
        // 将Driver直接传递给TestListener,用于截图操作
        TestListener.setDriver(driver);
        return driver;
    }
}
