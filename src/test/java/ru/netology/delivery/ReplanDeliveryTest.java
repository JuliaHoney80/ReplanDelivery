package ru.netology.delivery;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.selector.ByText;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ReplanDeliveryTest {
    @BeforeEach
    void setup() { open("http://localhost:9999"); }

    @Test
    @DisplayName("Should successful plan meeting")
    void shouldSuccessfulPlanMeeting() {
        DataGenerator.UserInfo validUser = DataGenerator.Registration.generateUser("ru");
        int daysToAddForFirstMeeting = 4;
        String firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        int daysToAddForSecondMeeting = 7;
        String secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("data-test-id='city')input").setValue(validUser.getCity());
        $("data-test-id='date')input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("data-test-id='date')input").setValue(firstMeetingDate);
        $("data-test-id='name')input").setValue(validUser.getName());
        $("data-test-id='phone')input").setValue(validUser.getPhone());
        $("[data-test-id ='agreement']").click();
        $("button.button").click();
        $(byText("Запланировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $("data-test-id='success-notification') .notification__content")
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + firstMeetingDate));
        $("data-test-id='date')input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("data-test-id='date')input").setValue(secondMeetingDate);
        $("data-test-id='replan-notification') .notification__content")
                .shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"))
                .shouldBe(visible);
        $("data-test-id='replan-notification') button").click();
        $("success-notification') .notification__content")
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + secondMeetingDate))
                .shouldBe(visible);



    }


}
