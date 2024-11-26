package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardPlanningTest {

    //Подключение Allure - добавление и удаление листенера
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }


    @Test
    public void shouldReplanMeeting() {
        open("http://localhost:9999/");
        var testUser = DataGenerator.Registration.generateUser("ru");

        var firstAdditionalDays = 5;
        var firstDate = DataGenerator.generateDate(firstAdditionalDays);
        var secondAdditionalDays = 12;
        var secondDate = DataGenerator.generateDate(secondAdditionalDays);

        $("[data-test-id='city'] input").setValue(testUser.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(firstDate);
        $("[data-test-id='name'] input").setValue(testUser.getName());
        $("[data-test-id='phone'] input").setValue(testUser.getPhone());
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("div.notification__content").shouldBe(Condition.visible, Duration.ofSeconds(20)).shouldHave(Condition.text("Встреча успешно запланирована на " + firstDate));

        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(secondDate);
        $("button.button").click();
        $("[data-test-id='replan-notification'] .notification__content").shouldBe(Condition.visible).shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $("[data-test-id='replan-notification'] button").click();
        $("[data-test-id='success-notification'] .notification__content").shouldBe(Condition.visible).shouldHave(Condition.text("Встреча успешно запланирована на " + secondDate));

    }

    @Test
    public void shouldGetErrorMessageWithInvalidPhone() {
        open("http://localhost:9999/");
        var testUser = DataGenerator.Registration.generateUser("ru");
        var firstAdditionalDays = 5;
        var firstDate = DataGenerator.generateDate(firstAdditionalDays);

        $("[data-test-id='city'] input").setValue(testUser.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(firstDate);
        $("[data-test-id='name'] input").setValue(testUser.getName());
        $("[data-test-id='phone'] input").setValue(DataGenerator.generateInvalidPhone("en"));
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.exactText("Введен неверный номер мобильного телефона"));
    }

}


