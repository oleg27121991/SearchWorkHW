package by.veremei.test;

import by.veremei.data.FormData;
import by.veremei.data.Language;
import by.veremei.page.DevqualityMainPage;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Configuration.baseUrl;
import static io.qameta.allure.Allure.step;

public class DevqualityTest extends BaseTest {
    DevqualityMainPage mainPage = new DevqualityMainPage();
    FormData formData = new FormData();
    private final static String TITLE_IN_FORM = "Leave a request";
    static Stream<Arguments> mainPageShouldDisplayCorrectTitle() {
        return Stream.of(
                Arguments.of(
                        Language.EN,
                        List.of("Don't worry about your next release.")
                ),
                Arguments.of(
                        Language.FR,
                        List.of("Rassurez-vous de votre prochaine version.")
                ),
                Arguments.of(
                        Language.RU,
                        List.of("Не беспокойтесь за свой следующий релиз.")
                )
        );
    }

    @ParameterizedTest
    @MethodSource
    @Feature("Локализация")
    @Story("Главная страница")
    @Owner("tg - @Veremeioleg")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Проверка локализации заголовка h1 на главной странице")
    @Tag("WEB_POSITIVE")
    void mainPageShouldDisplayCorrectTitle(Language language, List<String> expectedTitle) {
        step("Открываем главную страницу", () -> {
            mainPage.openMainPage(baseUrl);
        });
        step("Меняем текст на сайте на другой язык", () -> {
            mainPage.changeLangOnSite(language.description);
        });
        step("Проверяем, что заголовок h1 отображается на выбранном языке", () ->{
            mainPage.checkH1TitleLocalization(expectedTitle);
        });
    }

    @Test
    @Feature("Элементы на странице")
    @Story("Главная страница")
    @Owner("tg - @Veremeioleg")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Появление pop-up окна с формой 'Оставьте заявку' после клика на кнопку в баннерной секции сайта")
    @Tag("WEB_POSITIVE")
    void checkPopUpWithFormAfterClickOnBtnInBannerSection() {
        step("Открываем главную страницу", () -> {
            mainPage.openMainPage(baseUrl);
        });
        step("Кликаем на кнопку 'Познакомиться и ...'", () -> {
            mainPage.openFormFromBannerSection();
        });
        step("Проверяем, что отображается pop-up с формой обратной связи", () -> {
            mainPage.checkPopUpWithForm();
        });
        step("Проверяем, что pop-up с формой обратной связи содержит заголовок", () -> {
            mainPage.checkPopUpWithFormContainsTitle(TITLE_IN_FORM);
        });
    }

    @Test
    @Feature("Валидация")
    @Story("Валидация формы")
    @Owner("tg - @Veremeioleg")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Отправка пустой формы 'Оставьте заявку'")
    @Tag("WEB_NEGATIVE")
    void sendEmptyForm() {
        step("Открываем главную страницу", () -> {
            mainPage.openMainPage(baseUrl);
        });
        step("Кликаем на кнопку 'Познакомиться и ...'", () -> {
            mainPage.openFormFromBannerSection();
        });
        step("Отправляем пустую форму", () -> {
            mainPage.setEmptyForm("", "");
        });
        step("Проверяем что текст 'Ваш телефон или e-mail' стал красным", () -> {
            mainPage.checkInvalidTextInInputPhoneMail();
        });
        step("Проверяем что текст 'Текст обращения' стал красным", () -> {
            mainPage.checkInvalidTextInInputDescribeIssue();
        });
    }

    @Test
    @Feature("Валидация")
    @Story("Валидация формы")
    @Owner("tg - @Veremeioleg")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Отправка формы с незаполненным полем 'Текст обращения'")
    @Tag("WEB_NEGATIVE")
    void sendFormWithoutDescribeIssue() {
        step("Открываем главную страницу", () -> {
            mainPage.openMainPage(baseUrl);
        });
        step("Кликаем на кнопку 'Познакомиться и ...'", () -> {
            mainPage.openFormFromBannerSection();
        });
        step("Вводим в поле 'Ваш телефон или e-mail' валидный email", () -> {
            mainPage.enterEmail(formData.validEmail);
        });
        step("Кликаем на кнопку 'Отправить заявку'", () -> {
            mainPage.clickOnBtnSubmit();
        });
        step("Проверяем что текст 'Текст обращения' стал красным", () -> {
            mainPage.checkInvalidTextInInputDescribeIssue();
        });
    }

    @Test
    @Feature("Валидация")
    @Story("Валидация формы")
    @Owner("tg - @Veremeioleg")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Отправка формы с незаполненным полем 'Ваш телефон или e-mail'")
    @Tag("WEB_NEGATIVE")
    void sendFormWithEmptyFieldPhoneEmail() {
        step("Открываем главную страницу", () -> {
            mainPage.openMainPage(baseUrl);
        });
        step("Кликаем на кнопку 'Познакомиться и ...'", () -> {
            mainPage.openFormFromBannerSection();
        });
        step("Вводим в поле 'Текст обращения' случайный текст", () -> {
            mainPage.enterDescription(formData.shortDescr);
        });
        step("Кликаем на кнопку 'Отправить заявку'", () -> {
            mainPage.clickOnBtnSubmit();
        });
        step("Проверяем что текст 'Ваш телефон или e-mail' стал красным", () -> {
            mainPage.checkInvalidTextInInputPhoneMail();
        });
    }

    @Test
    @Feature("Валидация")
    @Story("Валидация формы")
    @Owner("tg - @Veremeioleg")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Отправка формы 'Оставьте заявку' с некорректным email")
    @Tag("WEB_NEGATIVE")
    void sendFormWithInvalidEmail() {
        step("Открываем главную страницу", () -> {
            mainPage.openMainPage(baseUrl);
        });
        step("Кликаем на кнопку 'Познакомиться и ...'", () -> {
            mainPage.openFormFromBannerSection();
        });
        step("Вводим в поле 'Ваш телефон или e-mail' невалидный email", () -> {
            mainPage.enterEmail(formData.invalidEmail);
        });
        step("Вводим в поле 'Текст обращения' случайный текст", () -> {
            mainPage.enterDescription(formData.shortDescr);
        });
        step("Кликаем на кнопку 'Отправить заявку'", () -> {
            mainPage.clickOnBtnSubmit();
        });
        step("Проверяем что текст 'Ваш телефон или e-mail' стал красным", () -> {
            mainPage.checkInvalidTextInInputPhoneMail();
        });
    }
}
