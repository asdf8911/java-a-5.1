package ru.netology.delivery.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.BACK_SPACE;

public class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        Configuration.holdBrowserOpen = true;
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);

        // TODO: добавить логику теста в рамках которого будет выполнено планирование и перепланирование встречи.
        // Для заполнения полей формы можно использовать пользователя validUser и строки с датами в переменных
        // firstMeetingDate и secondMeetingDate. Можно также вызывать методы generateCity(locale),
        // generateName(locale), generatePhone(locale) для генерации и получения в тесте соответственно города,
        // имени и номера телефона без создания пользователя в методе generateUser(String locale) в датагенераторе

        // ГОРОД
        $("[placeholder='Город']").setValue(validUser.getCity());
        // ДАТА ПЕРВАЯ
        $("[placeholder='Дата встречи']").click();
        $("[placeholder='Дата встречи']").sendKeys(Keys.CONTROL + "A");
        $("[placeholder='Дата встречи']").sendKeys(BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(firstMeetingDate);
        // ФИ
        $x("//input[@name='name']").setValue(validUser.getName());
        // ТЕЛЕФОН
        $x("//input[@name='phone']").setValue(validUser.getPhone());
        // СОГЛАСИЕ
        $("[data-test-id='agreement']").click();
        // ЗАПЛАНИРОВАТЬ
        $x("//span[@class='button__text']").click();
        // ОКНО УСПЕХА
        $x("//*[contains(text(),'Успешно!')]").shouldBe(visible, Duration.ofSeconds(15));
        $("div .notification__content").shouldHave(exactText("Встреча успешно запланирована на " + firstMeetingDate));

        //$("div[data-test-id='success-notification']").shouldBe(visible, Duration.ofSeconds(15));
        //$("div[data-test-id='success-notification']").shouldHave(exactText("Успешно! Встреча успешно забронирована на " + firstMeetingDate));

        // ДАТА ВТОРАЯ
        $("[placeholder='Дата встречи']").click();
        $("[placeholder='Дата встречи']").sendKeys(Keys.CONTROL + "A");
        $("[placeholder='Дата встречи']").sendKeys(BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(secondMeetingDate);
        // ЗАПЛАНИРОВАТЬ
        $x("//span[@class='button__text']").click();
        // ПЕРЕПЛАНИРОВАТЬ
        $x("//span[contains(text(), 'Перепланировать')]").click();
        // ОКНО УСПЕХА
        $x("//*[contains(text(),'Успешно!')]").shouldBe(visible, Duration.ofSeconds(15));
        $("div .notification__content").shouldHave(exactText("Встреча успешно запланирована на " + secondMeetingDate));
    }

}

