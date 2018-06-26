package action;

import macaca.client.MacacaClient;
import object.PageTemplate;

public class LogoutAction extends BaseAction {
    private PageTemplate page;

    /*
     * @description:        注销用户
     * @author:             Griffin
     * @date:               2018/5/4
     * @time:               10:46
     * @param driver        Macaca Driver
     * @param repoPath      对象仓库路径
     */
    public LogoutAction(MacacaClient driver, String repoPath) {
        super(driver, repoPath);
        page = new PageTemplate(driver, repoPath);
    }
    
    /*
    * @description: 注销用户
    * @author:      Griffin
    * @date:        2018/5/4
    * @time:        10:46
    * @param:
    * @return:
    */
    public void Logout() {
        page.setElementModule("PageElementModule", "基础页");
        page.click("我的");
        page.setElementModule("PageElementModule", "我的页");
        page.click("更多");
        page.setElementModule("PageElementModule", "更多");
        page.click("退出登录");
        page.setElementModule("PageElementModule", "退出对话框");
        page.click("确定");
    }
}
