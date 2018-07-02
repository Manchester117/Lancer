package util;

import com.aventstack.extentreports.ExtentTest;
import macaca.client.MacacaClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tools {
    private static Logger logger = LogManager.getLogger(Tools.class);

    public static String getTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        return sdf.format(date);
    }

    public static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            logger.warn("暂停异常: ");
            e.printStackTrace();
        }
    }

    public static void takeScreenShot(String reportFolderPath, String methodName, MacacaClient driver, ThreadLocal<ExtentTest> test) {
        String pictureName = StringUtils.join(methodName, "_", getTime(), ".png");
        String pictureFullPath = Paths.get(reportFolderPath, pictureName).toString();
        try {
            driver.saveScreenshot(pictureFullPath);
            test.get().addScreenCaptureFromPath(pictureName);
        } catch (Exception e) {
            logger.warn(StringUtils.join("截图异常: ", pictureName));
            e.printStackTrace();
        }
    }

    public static void createFolder(String reportFolderPath) {
        File file = new File(reportFolderPath);
        if (!file.exists()) {
            if (file.mkdirs())
                logger.info(StringUtils.join("创建报告目录成功: ", reportFolderPath));
            else
                logger.warn(StringUtils.join("创建报告目录失败,报告将无法保存: ", reportFolderPath));
        }
    }
}
