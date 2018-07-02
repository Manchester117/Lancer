package action;

import macaca.client.MacacaClient;
import object.PageTemplate;

public class BaseAction {
    private PageTemplate page;
    protected String udid;
    protected MacacaClient driver;

    public BaseAction(MacacaClient driver, String udid, String repoPath) {
        page = new PageTemplate(driver, repoPath);
        this.driver = driver;
        this.udid = udid;
    }

    /*
    * @description: 获取元素文本
    * @author:      Griffin
    * @date:        2018/6/29
    * @time:        15:34
    * @param:       模块名称, 页面名称, 元素名称
    * @return:      元素文本
    */
    public String getText(String moduleName, String pageName, String elementName) {
        page.setElementModule(moduleName, pageName);
        return page.getText(elementName);
    }

    /*
    * @description: 判断元素是否存在
    * @author:      Griffin
    * @date:        2018/6/29
    * @time:        15:20
    * @param:       模块名称, 页面名称, 元素名称
    * @return:      元素是否存在的Boolean值
    */
    public boolean isElementExist(String moduleName, String pageName, String elementName) {
        page.setElementModule(moduleName, pageName);
        return page.isElementExist(elementName);
    }

    /*
    * @description:         对页面进行点击(不涉及具体元素)
    * @author:              Griffin
    * @date:                2018/5/8
    * @time:                14:04
    * @param:
    * @return:
    */
    public void clickAnyPage(String moduleName, String pageName, String elementName) {
        page.setElementModule(moduleName, pageName);
        page.click(elementName);
    }

    /*
    * @description: 执行ADB命令
    * @author:      Griffin
    * @date:        2018/6/26
    * @time:        16:00
    * @param:       ADB命令字符串
    * @return:
    */
    public void executeADBCommand(String command) {
        page.executeADBCommand(command);
    }

    /*
     * @description: 发送物理按键
     * @author:      Griffin
     * @date:        2018/6/26
     * @time:        10:28
     * @param:       物理按键的Unicode
     * @return:
     */
    public void keyEvent(String keyCode) {
        page.keyEvent(udid, keyCode);
    }

    /*
     * @description         页面向左滑动
     * @author:             Griffin
     * @date:               2018/5/4
     * @time:               10:46
     * @param times         滑动次数
     */
    public void swipePageToLeft(int times) {
        PageTemplate page = new PageTemplate(driver);
        for (int i = 0; i < times; ++i)
            page.swipeToLeft(udid);
    }

    /*
     * @description         页面向右滑动
     * @author:             Griffin
     * @date:               2018/5/4
     * @time:               10:46
     * @param times         滑动次数
     */
    public void swipePageToRight(int times) {
        PageTemplate page = new PageTemplate(driver);
        for (int i = 0; i < times; ++i)
            page.swipeToRight(udid);
    }

    /*
     * @description         页面向上滑动
     * @author:             Griffin
     * @date:               2018/5/4
     * @time:               10:46
     * @param times         滑动次数
     */
    public void swipePageToUp(int times) {
        PageTemplate page = new PageTemplate(driver);
        for (int i = 0; i < times; ++i)
            page.swipeToUp(udid);
    }

    /*
     * @description         页面向下滑动
     * @author:             Griffin
     * @date:               2018/5/4
     * @time:               10:46
     * @param times         滑动次数
     */
    public void swipePageToDown(int times) {
        PageTemplate page = new PageTemplate(driver);
        for (int i = 0; i < times; ++i)
            page.swipeToDown(udid);
    }

    /*
    * @description:         关闭APP的操作
    * @author:              Griffin
    * @date:                2018/5/7
    * @time:                14:46
    * @param:               被测App包名
    * @return:
    */
    public void closeAppForAndroid(String packageName) {
        page.closeAppForAndroid(udid, packageName);
    }
}
