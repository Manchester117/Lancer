package action;

import macaca.client.MacacaClient;
import object.PageTemplate;
import util.Tools;

public class BaseAction {
    private PageTemplate page;
    protected MacacaClient driver;

    public BaseAction(MacacaClient driver, String repoPath) {
        page = new PageTemplate(driver, repoPath);
        this.driver = driver;
    }

    public String getText(String moduleName, String pageName, String elementName) {
        page.setElementModule(moduleName, pageName);
        return page.getText(elementName);
    }

    public boolean isElementExist(String moduleName, String pageName, String elementName) {
        page.setElementModule(moduleName, pageName);
        return page.isElementExist(elementName);
    }

    /**
    * @description:         对页面进行点击(不涉及具体元素)
    * @author:              Alex
    * @date:                2018/5/8
    * @time:                14:04
    * @param:
    * @return:
    */
    public void clickAnyPage(String moduleName, String pageName, String elementName) {
        page.setElementModule(moduleName, pageName);
        page.click(elementName);
//        Tools.sleep(1000);
    }

    /**
     * @description         页面向左滑动
     * @author:             Alex
     * @date:               2018/5/4
     * @time:               10:46
     * @param times         滑动次数
     */
    public void swipePageToLeft(int times) {
        PageTemplate page = new PageTemplate(driver);
        for (int i = 0; i < times; ++i)
            page.swipeToLeft();
    }

    /**
     * @description         页面向右滑动
     * @author:             Alex
     * @date:               2018/5/4
     * @time:               10:46
     * @param times         滑动次数
     */
    public void swipePageToRight(int times) {
        PageTemplate page = new PageTemplate(driver);
        for (int i = 0; i < times; ++i)
            page.swipeToRight();
    }

    /**
     * @description         页面向上滑动
     * @author:             Alex
     * @date:               2018/5/4
     * @time:               10:46
     * @param times         滑动次数
     */
    public void swipePageToUp(int times) {
        PageTemplate page = new PageTemplate(driver);
        for (int i = 0; i < times; ++i)
            page.swipeToUp();
    }

    /**
     * @description         页面向下滑动
     * @author:             Alex
     * @date:               2018/5/4
     * @time:               10:46
     * @param times         滑动次数
     */
    public void swipePageToDown(int times) {
        PageTemplate page = new PageTemplate(driver);
        for (int i = 0; i < times; ++i)
            page.swipeToDown();
    }

    /**
    * @description:         关闭APP的操作
    * @author:              Alex
    * @date:                2018/5/7
    * @time:                14:46
    * @param:               被测App包名
    * @return:
    */
    public void closeAppForAndroid(String packageName) {
        page.closeAppForAndroid(packageName);
    }
}
