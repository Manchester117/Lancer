package testcase;

import action.BaseAction;
import action.LoginAction;
import action.LogoutAction;
import action.SearchJobAction;
import component.driver.DriverConfig;
import macaca.client.MacacaClient;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.*;

public class TestSearchJobAction { ;
    private BaseAction baseAction;
    private LoginAction loginAction;
    private SearchJobAction searchJobAction;
    private LogoutAction logoutAction;
    private Boolean welcomePageFlag = Boolean.TRUE;

    @Parameters({"platformName", "app", "port", "udid", "reuse", "repositoryPath", "waitTimeInterval", "waitTimeout", "delay"})
    @BeforeClass
    public void beforeTest(String platformName, String app, String port, String udid, String reuse, String repositoryPath, String waitTimeInterval, String waitTimeout, String delay) {
        MacacaClient driver = DriverConfig.getDriver(platformName, app, port, udid, reuse, waitTimeInterval, waitTimeout, delay);
        baseAction = new BaseAction(driver, udid, repositoryPath);
        loginAction = new LoginAction(driver, udid, repositoryPath);
        searchJobAction = new SearchJobAction(driver, udid, repositoryPath);
        logoutAction = new LogoutAction(driver, udid, repositoryPath);
    }

    @DataProvider(name = "signInData")
    public Object[][] signInData() {
        return new Object[][] {
            {"xxxx@xx.com", "xxxxxxx"},
        };
    }

    @Test(description = "通过'立即订阅'登录", dataProvider = "signInData")
    public void loginBySubscribeNowPage(String userName, String password) {
        if (welcomePageFlag) {
            if (!loginAction.welcomePageAppear())
                // 如果欢迎首页没有出现,则跳过测试
                throw new SkipException("跳过剩下的测试");
            welcomePageFlag = Boolean.FALSE;
        }
        baseAction.swipePageToLeft(2);
        baseAction.clickAnyPage("PageElementModule", "欢迎页", "欢迎页");

        loginAction.loginBySubscribeNow(userName, password);
        String verifyText = loginAction.getText("PageElementModule", "机会页", "输入关键字");
        Assert.assertEquals("输入关键字", verifyText);
    }

    @Test(dependsOnMethods = {"loginBySubscribeNowPage"}, description = "搜索职位")
    public void searchJob() {
        searchJobAction.searchJob("Java");
        searchJobAction.quitJobList();
    }

    @Test(dependsOnMethods = {"searchJob"}, description = "退出登录")
    public void logoutApp() {
        logoutAction.Logout();
    }

    @AfterClass
    public void afterClass() {
        baseAction.closeAppForAndroid("com.zzzz.hhhh");
    }
}
