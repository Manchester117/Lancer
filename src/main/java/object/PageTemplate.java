package object;

import component.ElementParser;
import component.Operator;
import component.OperatorImpl;
import macaca.client.MacacaClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;

public class PageTemplate {
    private static Logger logger = LogManager.getLogger(PageTemplate.class);
    private Map<String, Locator> locatorContainer;
    private String storage;
    private Operator operator;

    /*
    * @description: 页面模板实例化
    * @author:      Griffin
    * @date:        2018/5/4
    * @time:        11:15
    * @param:       driver   操作页面的Handler
    * @return:
    */
    public PageTemplate(MacacaClient driver) {
        this.operator = new OperatorImpl(driver);
    }

    /*
    * @description: 页面模板实例化
    * @author:      Griffin
    * @date:        2018/5/4
    * @time:        11:16
    * @param:       driver   操作页面的Handler
    * @param:       storage  对象仓库的存储路径
    * @return:
    */
    public PageTemplate(MacacaClient driver, String storage) {
        this.storage = storage;
        this.operator = new OperatorImpl(driver);
    }

    /*
    * @description: 设置当前的页面
    * @author:      Griffin
    * @date:        2018/5/4
    * @time:        14:19
    * @param:       moduleName  模块名称
    * @param:       pageName    页面名称
    * @return:
    */
    public void setElementModule(String moduleName, String pageName) {
        String moduleFullName = StringUtils.join(moduleName, ".xml");
        String moduleFullPath = Paths.get(this.storage, moduleFullName).toAbsolutePath().toString();
        locatorContainer = ElementParser.readRepository(moduleFullPath, pageName);
    }

    /*
    * @description: 根据元素名称从对象库获取元素对象
    * @author:      Griffin
    * @date:        2018/5/4
    * @time:        14:21
    * @param:       elementName 元素名称
    * @return:      返回元素对象
    */
    private Locator getLocator(String elementName) {
        Locator locator = null;
        if (Objects.nonNull(locatorContainer))
            locator = locatorContainer.get(elementName);
        else
            logger.warn("页面对象容器为空");
        return locator;
    }

    /*
    * @description: 对元素进行点击
    * @author:      Griffin
    * @date:        2018/7/2
    * @time:        16:37
    * @param:       对象库中元素的名称
    * @return:
    */
    public void click(String elementName) {
        Locator locator = this.getLocator(elementName);
        this.operator.click(locator);
    }

    /*
    * @description: 对元素进行长按
    * @author:      Griffin
    * @date:        2018/7/2
    * @time:        16:37
    * @param:       对象库中元素的名称, 长按持续时间
    * @return:
    */
    public void press(String elementName, double duration) {
        Locator locator = this.getLocator(elementName);
        this.operator.press(locator, duration);
    }

    /*
    * @description: 在元素中输入文本
    * @author:      Griffin
    * @date:        2018/7/2
    * @time:        16:38
    * @param:       对象库中元素的名称, 输入的内容
    * @return:
    */
    public void input(String elementName, String value) {
        Locator locator = this.getLocator(elementName);
        this.operator.input(locator, value);
    }

    /*
    * @description: 清空文本框中的文本
    * @author:      Griffin
    * @date:        2018/7/2
    * @time:        16:39
    * @param:       对象库中元素的名称
    * @return:
    */
    public void clear(String elementName) {
        Locator locator = this.getLocator(elementName);
        this.operator.clear(locator);
    }

    /*
    * @description: 从元素中获取文本
    * @author:      Griffin
    * @date:        2018/7/2
    * @time:        16:39
    * @param:       对象库中元素的名称
    * @return:
    */
    public String getText(String elementName) {
        Locator locator = this.getLocator(elementName);
        return this.operator.getText(locator);
    }

    /*
    * @description: 判断元素是否存在
    * @author:      Griffin
    * @date:        2018/7/2
    * @time:        16:40
    * @param:       对象库中元素的名称
    * @return:
    */
    public boolean isElementExist(String elementName) {
        Locator locator = this.getLocator(elementName);
        return this.operator.isElementExist(locator);
    }

    /*
    * @description: ADB命令执行方法
    * @author:      Griffin
    * @date:        2018/7/2
    * @time:        16:40
    * @param:       ADB命令字符串
    * @return:
    */
    public void executeADBCommand(String command) {
        this.operator.executeADBCommand(command);
    }

    /*
    * @description: 使用物理按键方法
    * @author:      Griffin
    * @date:        2018/7/2
    * @time:        16:41
    * @param:       Android设备唯一标识, 物理按键编码
    * @return:
    */
    public void keyEvent(String udid, String keyCode) {
        this.operator.keyEvent(udid, keyCode);
    }

    public void swipeToUp(String udid) {
        this.operator.swipeToUp(udid);
    }

    public void swipeToDown(String udid) {
        this.operator.swipeToDown(udid);
    }

    public void swipeToLeft(String udid) {
        this.operator.swipeToLeft(udid);
    }

    public void swipeToRight(String udid) {
        this.operator.swipeToRight(udid);
    }

    public void closeAppForAndroid(String udid, String packageName) {
        this.operator.closeAppForAndroid(udid, packageName);
    }
}
