package action;

import macaca.client.MacacaClient;
import object.PageTemplate;

import java.text.MessageFormat;

public class SearchJobAction extends BaseAction {
    private PageTemplate page;

    public SearchJobAction(MacacaClient driver, String udid, String repoPath) {
        super(driver, udid, repoPath);
        page = new PageTemplate(driver, repoPath);
    }

    public void searchJob(String keyWord) {
        page.setElementModule("PageElementModule", "机会页");
        page.click("输入关键字");
        page.executeADBCommand(MessageFormat.format("adb -s {0} shell ime set com.sohu.inputmethod.sogou/.SogouIME", udid));
        page.input("关键字文本框", keyWord);
        page.keyEvent(udid, "66");
    }

    public void quitJobList() {
        page.setElementModule("PageElementModule", "职位列表");
        page.click("返回机会页");
    }
}
