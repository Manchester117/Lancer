package component;

import macaca.client.commands.Element;
import object.Locator;

public interface Operator {
    // 获取元素
    Element getElement(Locator locator);
    // 点击元素
    void click(Locator locator);
    // 输入内容
    void input(Locator locator, String value);
    // 清空内容
    void clear(Locator locator);
    // 获取内容
    String getText(Locator locator);
    // 元素是否显示
    boolean isElementExist(Locator locator);
    // 向上滑动
    void swipeToUp();
    // 向下滑动
    void swipeToDown();
    // 向左滑动
    void swipeToLeft();
    // 向右滑动
    void swipeToRight();
    // 退出App
    void closeAppForAndroid(String packageName);
}
