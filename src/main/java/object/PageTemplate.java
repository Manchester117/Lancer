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

    /**
    * @description: 页面模板实例化
    * @author:      Alex
    * @date:        2018/5/4
    * @time:        11:15
    * @param:       driver   操作页面的Handler
    * @return:
    */
    public PageTemplate(MacacaClient driver) {
        this.operator = new OperatorImpl(driver);
    }

    /**
    * @description: 页面模板实例化
    * @author:      Alex
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

    /**
    * @description: 设置当前的页面
    * @author:      Alex
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

    /**
    * @description: 根据元素名称从对象库获取元素对象
    * @author:      Alex
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

    public void click(String elementName) {
        Locator locator = this.getLocator(elementName);
        this.operator.click(locator);
    }

    public void input(String elementName, String value) {
        Locator locator = this.getLocator(elementName);
        this.operator.input(locator, value);
    }

    public void clear(String elementName) {
        Locator locator = this.getLocator(elementName);
        this.operator.clear(locator);
    }

    public String getText(String elementName) {
        Locator locator = this.getLocator(elementName);
        return this.operator.getText(locator);
    }

    public boolean isElementExist(String elementName) {
        Locator locator = this.getLocator(elementName);
        return this.operator.isElementExist(locator);
    }

    public void swipeToUp() {
        this.operator.swipeToUp();
    }

    public void swipeToDown() {
        this.operator.swipeToDown();
    }

    public void swipeToLeft() {
        this.operator.swipeToLeft();
    }

    public void swipeToRight() {
        this.operator.swipeToRight();
    }

    public void closeAppForAndroid(String packageName) {
        this.operator.closeAppForAndroid(packageName);
    }
}
