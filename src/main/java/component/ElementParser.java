package component;

import macaca.client.common.GetElementWay;
import object.Locator;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.*;

public class ElementParser {
    private static Logger logger = LogManager.getLogger(ElementParser.class);

    /*
    * @description: 读取对象仓库的方法,按照仓库-模块-页面-元素的方式进行获取
    * @author:      Alex
    * @date:        2018/5/4
    * @time:        10:58
    * @param:       repositoryPath  仓库路径
    * @param:       pageName        页面名称
    * @return:      返回一个页面的Map结构
    */
    public static Map<String, Locator> readRepository(String repositoryPath, String pageName) {
        Map<String, Locator> locatorContainer = new HashMap<>();
        File file = new File(repositoryPath);

        if (!file.exists()) {
            logger.warn(StringUtils.join("没有找到页面仓库: ", repositoryPath));
            return null;
        }

        SAXReader reader = new SAXReader();
        Document doc = null;
        try {
            doc = reader.read(repositoryPath);
            String pageNodeXpath = StringUtils.join("/repository/page[@name='", pageName, "']");
            Element pageElement = (Element)doc.selectSingleNode(pageNodeXpath);

            List elementList = pageElement.elements();
            for (Object element: elementList) {
                String elementName = ((Element)element).getText();
                String type = ((Element)element).attributeValue("type");
                String locator = ((Element)element).attributeValue("locator");

                Locator locatorObject = new Locator(getElementWay(type), locator);
                locatorContainer.put(elementName, locatorObject);
            }
        } catch (DocumentException e) {
            logger.warn("读取页面仓库异常(XML解析问题)");
            e.printStackTrace();
        } catch (NullPointerException e) {
            logger.warn("找不到页面对象");
            e.printStackTrace();
        }
        return locatorContainer;
    }

    /*
    * @description: 获取元素的定位类型
    * @author:      Alex
    * @date:        2018/5/4
    * @time:        10:59
    * @param:       type    元素定位类型(id/xpath/name/class)
    * @return:      返回定位的实体类型
    */
    private static GetElementWay getElementWay(String type) {
        if (Objects.equals(type, GetElementWay.XPATH.getUsing())) {
            return GetElementWay.XPATH;
        } else if (Objects.equals(type, GetElementWay.ID.getUsing())) {
            return GetElementWay.ID;
        } else if (Objects.equals(type, GetElementWay.NAME.getUsing())) {
            return GetElementWay.NAME;
        } else if (Objects.equals(type, GetElementWay.CLASS_NAME.getUsing())) {
            return GetElementWay.CLASS_NAME;
        } else {
            logger.warn(StringUtils.join("未知的定位类型: ", type));
            return null;
        }
    }
}
