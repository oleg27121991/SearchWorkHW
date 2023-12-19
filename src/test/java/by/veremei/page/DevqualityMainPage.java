package by.veremei.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import java.util.List;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DevqualityMainPage {
    private final SelenideElement btnChangeLangInHeader = $(".header__langs"),
                                btnOpenFormInBannerSection = $(".banner__content .js-call-popup"),
                                btnSendForm = $(".request_popup .btn_request"),
                                inputPhoneMail = $(".request_popup [name='phone-mail']"),
                                inputDescribeIssue = $(".request_popup .input__text_textarea"),
                                popUpWithForm = $(".request_popup");
    private final ElementsCollection linkWithChangedLanguage = $$(".header__lang"),
                                    h1Title = $$(".banner__content h1"),
                                    inputsInForm = $$(".request_popup .input__label");

    public void openMainPage (String url) {
        Selenide.open(url);
    }

    public void changeLangOnSite(String name) {
        btnChangeLangInHeader.hover();
        linkWithChangedLanguage.findBy(text(name)).click();
    }

    public void checkH1TitleLocalization(List<String> expectedTitle) {
        h1Title.shouldHave(texts(expectedTitle));
    }

    public void openFormFromBannerSection() {
        btnOpenFormInBannerSection.click();
    }

    public void setEmptyForm(String email, String message) {
        btnSendForm.click();
    }

    public void checkInvalidTextInInputPhoneMail() {
        inputPhoneMail.shouldHave(cssClass("error"));
        inputsInForm.first().shouldHave(cssValue("color", "rgba(255, 0, 0, 1)"));
    }

    public void checkInvalidTextInInputDescribeIssue() {
        inputDescribeIssue.shouldHave(cssClass("error"));
        inputsInForm.last().shouldHave(cssValue("color", "rgba(255, 0, 0, 1)"));
    }

    public void enterEmail(String email) {
        inputPhoneMail.setValue(email);
    }

    public void clickOnBtnSubmit() {
        btnSendForm.click();
    }

    public void enterDescription(String text) {
        inputDescribeIssue.setValue(text);
    }

    public void checkPopUpWithForm() {
        popUpWithForm.shouldBe(visible);
    }

    public void checkPopUpWithFormContainsTitle(String title) {
        popUpWithForm.shouldHave(text(title));
    }
}
