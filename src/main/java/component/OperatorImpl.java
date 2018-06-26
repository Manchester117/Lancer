package component;

import macaca.client.MacacaClient;
import macaca.client.commands.Element;
import macaca.client.common.GetElementWay;
import object.Locator;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class OperatorImpl implements Operator {
    private static Logger logger = LogManager.getLogger(OperatorImpl.class);
    private MacacaClient driver = null;

    public OperatorImpl(MacacaClient driver) {
        this.driver = driver;
    }

    public MacacaClient getDriver() {
        return driver;
    }

    public void setDriver(MacacaClient driver) {
        this.driver = driver;
    }

    /*
    * @description: 获取屏幕大小-用于滑动屏幕
    * @author:      Griffin
    * @date:        2018/5/4
    * @time:        11:01
    * @param:
    * @return:      返回屏幕大小的Map
    */
    private Map<String, Integer> getScreenSize() {
        Map<String, Integer> screenSizeInfo = new HashMap<>();
        try {
            String width = this.driver.getWindowSize().get("width").toString();
            String height = this.driver.getWindowSize().get("height").toString();
            screenSizeInfo.put("width", Integer.parseInt(width));
            screenSizeInfo.put("height", Integer.parseInt(height));
            logger.info(StringUtils.join("获取屏幕尺寸 ", "height: ", height, " width: ", width));
        } catch (Exception e) {
            logger.warn("获取屏幕尺寸异常");
            e.printStackTrace();
        }
        return screenSizeInfo;
    }

    /*
    * @description: 获取元素的基础方法
    * @author:      Griffin
    * @date:        2018/5/4
    * @time:        11:01
    * @param:       locator     元素实体
    * @return:      返回元素
    */
    @Override
    public Element getElement(Locator locator) {
        Element element = null;
        try {
            if (Objects.equals(locator.getLocatorType(), GetElementWay.ID)) {
                element = driver.waitForElementById(locator.getLocatorIdentify());
                logger.info(StringUtils.join("找到元素: ", locator.getLocatorIdentify()));
            } else if (Objects.equals(locator.getLocatorType(), GetElementWay.XPATH)) {
                element = driver.waitForElementByXPath(locator.getLocatorIdentify());
                logger.info(StringUtils.join("找到元素: ", locator.getLocatorIdentify()));
            } else if (Objects.equals(locator.getLocatorType(), GetElementWay.NAME)) {
                element = driver.waitForElementByName(locator.getLocatorIdentify());
                logger.info(StringUtils.join("找到元素: ", locator.getLocatorIdentify()));
            } else if (Objects.equals(locator.getLocatorType(), GetElementWay.CLASS_NAME)) {
                element = driver.waitForElement(GetElementWay.CLASS_NAME, locator.getLocatorIdentify());
                logger.info(StringUtils.join("找到元素: ", locator.getLocatorIdentify()));
            } else {
                logger.info(StringUtils.join("不是规定的元素标识: ", locator.getLocatorIdentify(), " 请使用正确id/xpath/name/class name进行元素定位"));
            }
        } catch (Exception e) {
            logger.warn(StringUtils.join("找不到元素", locator));
            e.printStackTrace();
        }
        return element;
    }

    /*
     * @description: 点击页面元素
     * @author:      Griffin
     * @date:        2018/6/21
     * @time:        15:49
     * @param:       locator     元素实体
     */
    @Override
    public void click(Locator locator) {
        if (Objects.isNull(locator)) {
            logger.warn(StringUtils.join("当前元素为空,无法点击操作"));
            return;
        }
        Element element = this.getElement(locator);
        try {
            element.click();
            logger.info(StringUtils.join("执行点击: ", locator.getLocatorIdentify()));
        } catch (Exception e) {
            logger.warn("点击元素异常");
            e.printStackTrace();
        }
    }

    /*
     * @description: 在文本框输入内容
     * @author:      Griffin
     * @date:        2018/6/21
     * @time:        15:49
     * @param:       locator     元素实体
     */
    @Override
    public void input(Locator locator, String value) {
        if (Objects.isNull(locator)) {
            logger.warn(StringUtils.join("当前元素为空,无法输入操作"));
            return;
        }
        Element element = this.getElement(locator);
        try {
            element.sendKeys(value);
            logger.info(StringUtils.join("执行输入: ", locator.getLocatorIdentify()));
        } catch (Exception e) {
            logger.warn("输入文字异常");
            e.printStackTrace();
        }
    }

    /*
     * @description: 清空文本框中的内容
     * @author:      Griffin
     * @date:        2018/6/21
     * @time:        15:49
     * @param:       locator     元素实体
     */
    @Override
    public void clear(Locator locator) {
        if (Objects.isNull(locator)) {
            logger.warn(StringUtils.join("当前元素为空,无法清空操作"));
            return;
        }
        Element element = this.getElement(locator);
        try {
            element.clearText();
            logger.info(StringUtils.join("清空内容: ", locator.getLocatorIdentify()));
        } catch (Exception e) {
            logger.warn("清空内容异常");
            e.printStackTrace();
        }
    }

    /*
    * @description: 获取元素中的文本
    * @author:      Griffin
    * @date:        2018/6/21
    * @time:        15:55
    * @param:       locator    元素实体
    * @return:      返回元素中的文本信息
    */
    @Override
    public String getText(Locator locator) {
        if (Objects.isNull(locator)) {
            logger.warn(StringUtils.join("当前元素为空,无法获取文本操作"));
            return null;
        }
        Element element = this.getElement(locator);
        try {
            logger.info(StringUtils.join("获取元素内容: ", locator.getLocatorIdentify()));
            return element.getText();
        } catch (Exception e) {
            logger.warn("获取元素文字异常");
            e.printStackTrace();
        }
        return null;
    }

    /*
    * @description: 判断元素是否存在
    * @author:      Griffin
    * @date:        2018/6/21
    * @time:        15:56
    * @param:       元素实体
    * @return:      元素是否存在的boolean值
    */
    @Override
    public boolean isElementExist(Locator locator) {
        if (Objects.isNull(locator)) {
            logger.warn(StringUtils.join("当前元素为空,无法判断元素是否存在"));
            return false;
        }
        Boolean isExist = Boolean.FALSE;
        try {
            isExist = this.driver.isElementExist(locator.getLocatorType(), locator.getLocatorIdentify());
            logger.info(StringUtils.join("判断元素是否存在: ", locator.getLocatorIdentify()));
        } catch (Exception e) {
            logger.warn("执行判断元素操作异常");
            e.printStackTrace();
        }
        return isExist;
    }

    /*
    * @description: 发送ADB命令
    * @author:      Griffin
    * @date:        2018/6/26
    * @time:        15:55
    * @param:       ADB命令字符串
    * @return:
    */
    @Override
    public void executeADBCommand(String command) {
        try {
            Runtime.getRuntime().exec(command);
            logger.info(StringUtils.join("执行ADB命令: ", command));
        } catch (IOException e) {
            logger.warn("执行ADB命令异常");
            e.printStackTrace();
        }
    }

    /*
     * @description: 发送物理按键
     * @author:      Griffin
     * @date:        2018/6/25
     * @time:        18:42
     * @param:       物理按键EnumCode
     * @return:
     */
    @Override
    public void keyEvent(String keyCode) {
        String keyCommand = MessageFormat.format("adb shell input keyevent {0}", keyCode);
        this.executeADBCommand(keyCommand);
    }

    /*
    * @description: 使用adb命令向上滑动屏幕
    * @author:      Griffin
    * @date:        2018/6/21
    * @time:        15:57
    * @param:
    * @return:
    */
    @Override
    public void swipeToUp() {
        Map<String, Integer> screenSizeInfo = this.getScreenSize();
        Integer width = screenSizeInfo.get("width");
        Integer height = screenSizeInfo.get("height");

        // 直接使用adb命令来滑动页面
        String swipeToUpCommand = MessageFormat.format("adb shell input touchscreen swipe {0} {1} {2} {3}", width / 2, height * 0.2, width / 2, height * 0.8, 200);
        try {
            this.driver.sleep(1000);
            Runtime.getRuntime().exec(swipeToUpCommand);
            logger.info("向上滑动-命令: ", swipeToUpCommand);
        } catch (Exception e) {
            logger.warn("向上滑动屏幕异常");
            e.printStackTrace();
        }
    }

    /*
    * @description: 使用adb命令向下滑动屏幕
    * @author:      Griffin
    * @date:        2018/6/21
    * @time:        15:58
    * @param:
    * @return:
    */
    @Override
    public void swipeToDown() {
        Map<String, Integer> screenSizeInfo = this.getScreenSize();
        Integer width = screenSizeInfo.get("width");
        Integer height = screenSizeInfo.get("height");

        // 直接使用adb命令来滑动页面
        String swipeToDownCommand = MessageFormat.format("adb shell input touchscreen swipe {0} {1} {2} {3}", width / 2, height * 0.8, width / 2, height * 0.2, 200);
        try {
            this.driver.sleep(1000);
            Runtime.getRuntime().exec(swipeToDownCommand);
            logger.info(StringUtils.join("向下滑动-命令: ", swipeToDownCommand));
        } catch (Exception e) {
            logger.warn("向下滑动屏幕异常");
            e.printStackTrace();
        }
    }

    /*
    * @description: 使用adb命令向左滑动屏幕
    * @author:      Griffin
    * @date:        2018/6/21
    * @time:        15:58
    * @param:
    * @return:
    */
    @Override
    public void swipeToLeft() {
        Map<String, Integer> screenSizeInfo = this.getScreenSize();
        Integer width = screenSizeInfo.get("width");
        Integer height = screenSizeInfo.get("height");
        // 直接使用adb命令来滑动页面
        String swipeToLeftCommand = MessageFormat.format("adb shell input touchscreen swipe {0} {1} {2} {3}", width * 0.8, height / 2, width * 0.2, height / 2, 200);
        try {
            this.driver.sleep(1000);
            Runtime.getRuntime().exec(swipeToLeftCommand);
            logger.info(StringUtils.join("向左滑动-命令: ", swipeToLeftCommand));
        } catch (Exception e) {
            logger.warn("向左滑动屏幕异常");
            e.printStackTrace();
        }
    }

    /*
    * @description: 使用adb命令向右滑动屏幕
    * @author:      Griffin
    * @date:        2018/6/21
    * @time:        15:59
    * @param:
    * @return:
    */
    @Override
    public void swipeToRight() {
        Map<String, Integer> screenSizeInfo = this.getScreenSize();
        Integer width = screenSizeInfo.get("width");
        Integer height = screenSizeInfo.get("height");
        // 直接使用adb命令来滑动页面
        String swipeToRightCommand = MessageFormat.format("adb shell input touchscreen swipe {0} {1} {2} {3}", width * 0.2, height / 2, width * 0.8, height / 2, 200);
        try {
            this.driver.sleep(1000);
            Runtime.getRuntime().exec(swipeToRightCommand);
            logger.info(StringUtils.join("向右滑动-命令: ", swipeToRightCommand));
        } catch (Exception e) {
            logger.warn("向右滑动屏幕异常: ");
            e.printStackTrace();
        }
    }

    /*
    * @description: 使用adb命令退出App
    * @author:      Griffin
    * @date:        2018/6/21
    * @time:        16:00
    * @param:
    * @return:
    */
    @Override
    public void closeAppForAndroid(String packageName) {
        if (!StringUtils.isNoneBlank(packageName)) {
            logger.warn(StringUtils.join("获取的App包名为空,无法退出App"));
            return;
        }
        String closeAppCommand = StringUtils.join("adb shell am force-stop ", packageName);
        try {
            Runtime.getRuntime().exec(closeAppCommand);
            logger.info(StringUtils.join("退出APP-命令: ", closeAppCommand));
        } catch (IOException e) {
            logger.warn("退出App异常: ");
            e.printStackTrace();
        }
    }
}
