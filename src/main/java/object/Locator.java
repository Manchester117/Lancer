package object;

import macaca.client.common.GetElementWay;

public class Locator {
    private GetElementWay locatorType;
    private String locatorIdentify;
    /*
    * @description: APP页面元素实例化
    * @author:      Alex
    * @date:        2018/5/4
    * @time:        11:13
    * @param:       locatorIdentify 元素的定位方式(默认以XPath定位)
    * @return:
    */
    public Locator(String locatorIdentify) {
        this.locatorType = GetElementWay.XPATH;
        this.locatorIdentify = locatorIdentify;
    }

    /*
    * @description: APP页面元素实例化
    * @author:      Alex
    * @date:        2018/5/4
    * @time:        11:14
    * @param:       locatorType     元素定位类型
    * @param:       locatorIdentify 元素的定位方式
    * @return:
    */
    public Locator(GetElementWay locatorType, String locatorIdentify) {
        this.locatorType = locatorType;
        this.locatorIdentify = locatorIdentify;
    }

    public GetElementWay getLocatorType() {
        return locatorType;
    }

    public void setLocatorType(GetElementWay locatorType) {
        this.locatorType = locatorType;
    }

    public String getLocatorIdentify() {
        return locatorIdentify;
    }

    public void setLocatorIdentify(String locatorIdentify) {
        this.locatorIdentify = locatorIdentify;
    }

    @Override
    public String toString() {
        return "Locator{" +
                "locatorType=" + locatorType +
                ", locatorIdentify='" + locatorIdentify + '\'' +
                '}';
    }
}
