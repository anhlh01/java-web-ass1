package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HomePage {

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id="note-description")
    private WebElement noteDescription;

    @FindBy(id="logout-button")
    private WebElement logoutButton;

    @FindBy(id = "add-new-note-button")
    private WebElement addNewNoteButton;

    @FindBy(id = "save-note-button")
    private WebElement saveNoteButton;

    @FindBy(id = "save-credential-button")
    private WebElement saveCredentialButton;

    @FindBy(css = "#userTable>tbody>tr>th")
    private List<WebElement> noteTitles;

    @FindBy(css = "#credentialTable>tbody>tr>th")
    private List<WebElement> credentialUrls;

    @FindBy(id = "noteModalLabel")
    private WebElement notePopupTitle;

    @FindBy(id = "credentialModalLabel")
    private WebElement credentialPopupTitle;

    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;

    @FindBy(id = "add-new-credential")
    private WebElement addNewCredentialButton;

    @FindBy(id = "credential-url")
    private WebElement credentialUrl;

    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    @FindBy(id = "credential-username")
    private WebElement credentialUsername;

    @FindBy(xpath = "//*[@id=\"btnEditNote\"]")
    private WebElement editNoteButton;

    @FindBy(id = "btnEditCredential")
    private WebElement editCredentialButton;

    @FindBy(xpath = "//*[@id=\"userTable\"]/tbody/tr/td[1]/a")
    private WebElement deleteNoteButton;

    @FindBy(xpath = "//*[@id=\"credentialTable\"]/tbody/tr[1]/td[1]/a")
    private WebElement deleteCredentialButton;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void logout() {
        logoutButton.click();
    }

    public void clickNotesTab() {
        notesTab.click();
    }

    public void clickCredentialsTab() {
        credentialsTab.click();
    }

    public void clickAddNewNoteButton() {
        addNewNoteButton.click();
    }

    public void clickAddNewCredentialButton() {
        addNewCredentialButton.click();
    }

    public WebElement getNotePopupTitle() {
        return notePopupTitle;
    }

    public WebElement getCredentialsPopupTitle() {
        return credentialPopupTitle;
    }

    public WebElement getAddNewNoteButton() {
        return addNewNoteButton;
    }

    public WebElement getCredentialAddButton() {
        return addNewCredentialButton;
    }

    public void addNewNote(String noteTitle, String noteDescription) {
        this.noteTitle.sendKeys(noteTitle);
        this.noteDescription.sendKeys(noteDescription);
        this.saveNoteButton.click();
    }

    public void addNewCredential(String url, String username, String password) {
        this.credentialUrl.sendKeys(url);
        this.credentialUsername.sendKeys(username);
        this.credentialPassword.sendKeys(password);
        this.saveCredentialButton.click();
    }

    public void clickEditNoteButton() {
        this.editNoteButton.click();
    }

    public void clickEditCredentialButton() {
        this.editCredentialButton.click();
    }

    public String deleteNote() {
        String noteFirstTitle = noteTitles.get(0).getText();
        this.deleteNoteButton.click();
        return noteFirstTitle;
    }

    public String deleteCredential() {
        String credentialFirstUrl = credentialUrls.get(0).getText();
        this.deleteCredentialButton.click();
        return credentialFirstUrl;
    }

    public void editNote(String noteTitle, String noteDescription) {
        this.noteTitle.clear();
        this.noteDescription.clear();
        this.noteTitle.sendKeys(noteTitle);
        this.noteDescription.sendKeys(noteDescription);
        this.saveNoteButton.click();
    }

    public void editCredential(String url, String username, String password) {
        this.credentialUrl.clear();
        this.credentialPassword.clear();
        this.credentialUsername.clear();
        this.credentialUrl.sendKeys(url);
        this.credentialUsername.sendKeys(username);
        this.credentialPassword.sendKeys(password);
        this.saveCredentialButton.click();
    }

    public boolean checkContainNote(String noteTitle) {
        for (WebElement element : noteTitles) {
            if (element.getText().equals(noteTitle)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkContainCredential(String url) {
        for (WebElement element : credentialUrls) {
            if (element.getText().equals(url)) {
                return true;
            }
        }
        return false;
    }
}
