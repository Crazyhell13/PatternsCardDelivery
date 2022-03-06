package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import ru.netology.data.DataGenerator;
import ru.netology.data.RegistrationByCardInfo;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryCardFormTest {
    RegistrationByCardInfo user = DataGenerator.Registration.generateByCard("ru");
    String inThreeDays = DataGenerator.Registration.generateDate(3);
    String inFiveDays = DataGenerator.Registration.generateDate(5);
    String city = DataGenerator.setCityRandomArray();

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
    }

    @Test
    void shouldTestHappyPath() {
        $("[data-test-id='city'] input").setValue(city);
        $("[data-test-id='date'] input").setValue(inThreeDays);
        $("[data-test-id='name'] input").setValue(user.getName());
        $("[data-test-id='phone'] input").setValue(user.getNumber());
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification']>.notification__content").shouldHave(text("Встреча успешно запланирована на " + inThreeDays));
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(inFiveDays);
        $$("button").find(exactText("Запланировать")).click();
        $$(".button").find(exactText("Перепланировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification']>.notification__content").shouldHave(text("Встреча успешно запланирована на " + inFiveDays));
    }
    @Test
    void shouldTestDoubleClick() {
        $("[data-test-id='city'] input").setValue(city);
        $("[data-test-id='date'] input").setValue(inThreeDays);
        $("[data-test-id='name'] input").setValue(user.getName());
        $("[data-test-id='phone'] input").setValue(user.getNumber());
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification']>.notification__content").shouldHave(text("Встреча успешно запланирована на " + inThreeDays));
        $$("button").find(exactText("Запланировать")).click();
        $$(".button").find(exactText("Перепланировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification']>.notification__content").shouldHave(text("Встреча успешно запланирована на " + inThreeDays));
    }
    @Test
    void shouldTestNoDate() {
        $("[data-test-id='city'] input").setValue(city);
        $("[data-test-id='date'] input").setValue(inThreeDays);
        $("[data-test-id='name'] input").setValue(user.getName());
        $("[data-test-id='phone'] input").setValue(user.getNumber());
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification']>.notification__content").shouldHave(text("Встреча успешно запланирована на " + inThreeDays));
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue("");
        $$("button").find(exactText("Запланировать")).click();
        $(".calendar-input .input_invalid .input__sub").shouldHave(text("Неверно введена дата"));
    }
    @Test
    void shouldTestInvalidDate3() {
        $("[data-test-id='city'] input").setValue(city);
        $("[data-test-id='date'] input").setValue(inThreeDays);
        $("[data-test-id='name'] input").setValue(user.getName());
        $("[data-test-id='phone'] input").setValue(user.getNumber());
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification']>.notification__content").shouldHave(text("Встреча успешно запланирована на " + inThreeDays));
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(DataGenerator.Registration.generateDate(1));
        $$("button").find(exactText("Запланировать")).click();
        $(".calendar-input .input_invalid .input__sub").shouldHave(text("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldTestEmpty(){
        $("[data-test-id='city'] input").setValue("");
        $("[data-test-id='date'] input").setValue("");
        $("[data-test-id='name'] input").setValue("");
        $("[data-test-id='phone'] input").setValue("");
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }
    @Test
    void shouldTestWithoutCity(){
        $("[data-test-id='city'] input").setValue("");
        $("[data-test-id='date'] input").setValue(inThreeDays);
        $("[data-test-id='name'] input").setValue(user.getName());
        $("[data-test-id='phone'] input").setValue(user.getNumber());
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }
    @Test
    void shouldTestInvalidCity1(){
        $("[data-test-id='city'] input").setValue("Елец");
        $("[data-test-id='date'] input").setValue(inThreeDays);
        $("[data-test-id='name'] input").setValue(user.getName());
        $("[data-test-id='phone'] input").setValue(user.getNumber());
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(text("Доставка в выбранный город недоступна"));
    }
    @Test
    void shouldTestInvalidCity2(){
        $("[data-test-id='city'] input").setValue("Moscow");
        $("[data-test-id='date'] input").setValue(inThreeDays);
        $("[data-test-id='name'] input").setValue(user.getName());
        $("[data-test-id='phone'] input").setValue(user.getNumber());
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(text("Доставка в выбранный город недоступна"));
    }
    //баг
    @Test
    void shouldTestWithoutName(){
        $("[data-test-id='city'] input").setValue(city);
        $("[data-test-id='date'] input").setValue(inThreeDays);
        $("[data-test-id='name'] input").setValue("");
        $("[data-test-id='phone'] input").setValue(user.getNumber());
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }

    //баг
    @Test
    void shouldTestNameWithE(){
        $("[data-test-id='city'] input").setValue(city);
        $("[data-test-id='date'] input").setValue(inThreeDays);
        $("[data-test-id='name'] input").setValue("Фёдор Леонов");
        $("[data-test-id='phone'] input").setValue(user.getNumber());
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification']>.notification__content").shouldHave(text("Встреча успешно запланирована на " + inThreeDays));
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(inFiveDays);
        $$("button").find(exactText("Запланировать")).click();
        $$(".button").find(exactText("Перепланировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification']>.notification__content").shouldHave(text("Встреча успешно запланирована на " + inFiveDays));
    }

    @Test
    void shouldTestInvalidName1(){
        $("[data-test-id='city'] input").setValue(city);
        $("[data-test-id='date'] input").setValue(inThreeDays);
        $("[data-test-id='name'] input").setValue("Rabid Racoon");
        $("[data-test-id='phone'] input").setValue(user.getNumber());
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }
    @Test
    void shouldTestInvalidName2(){
        $("[data-test-id='city'] input").setValue(city);
        $("[data-test-id='date'] input").setValue(inThreeDays);
        $("[data-test-id='name'] input").setValue("Енотовый_Енот");
        $("[data-test-id='phone'] input").setValue(user.getNumber());
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }
    @Test
    void shouldTestInvalidName3(){
        $("[data-test-id='city'] input").setValue(city);
        $("[data-test-id='date'] input").setValue(inThreeDays);
        $("[data-test-id='name'] input").setValue("999");
        $("[data-test-id='phone'] input").setValue(user.getNumber());
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }
    @Test
    void shouldTestWithoutPhone(){
        $("[data-test-id='city'] input").setValue(city);
        $("[data-test-id='date'] input").setValue(inThreeDays);
        $("[data-test-id='name'] input").setValue(user.getName());
        $("[data-test-id='phone'] input").setValue("");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }

    //баг
    @Test
    void shouldTestInvalidPhone1(){
        $("[data-test-id='city'] input").setValue(city);
        $("[data-test-id='date'] input").setValue(inThreeDays);
        $("[data-test-id='name'] input").setValue(user.getName());
        $("[data-test-id='phone'] input").setValue("+7900");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }
    //баг
     @Test
    void shouldTestInvalidPhone2(){
        $("[data-test-id='city'] input").setValue(city);
        $("[data-test-id='date'] input").setValue(inThreeDays);
        $("[data-test-id='name'] input").setValue(user.getName());
        $("[data-test-id='phone'] input").setValue("89000000000");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldTestWithoutAgreement (){
        $("[data-test-id='city'] input").setValue(city);
        $("[data-test-id='date'] input").setValue(inThreeDays);
        $("[data-test-id='name'] input").setValue(user.getName());
        $("[data-test-id='phone'] input").setValue(user.getNumber());
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='agreement'].input_invalid .checkbox__text").shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
    @Test
    void shouldTestWithoutDate (){
        $("[data-test-id='city'] input").setValue(city);
        $("[data-test-id='date'] input").setValue("");
        $("[data-test-id='name'] input").setValue(user.getName());
        $("[data-test-id='phone'] input").setValue(user.getNumber());
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $(".calendar-input .input_invalid .input__sub").shouldHave(text("Неверно введена дата"));
    }
    @Test
    void shouldTestInvalidDate (){
        $("[data-test-id='city'] input").setValue(city);
        $("[data-test-id='date'] input").setValue(DataGenerator.Registration.generateDate(1));
        $("[data-test-id='name'] input").setValue(user.getName());
        $("[data-test-id='phone'] input").setValue(user.getNumber());
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $(".calendar-input .input_invalid .input__sub").shouldHave(text("Заказ на выбранную дату невозможен"));
    }
    @Test
    void shouldTestInvalidDate2 (){
        $("[data-test-id='city'] input").setValue(city);
        $("[data-test-id='date'] input").setValue("01.01.2022");
        $("[data-test-id='name'] input").setValue(user.getName());
        $("[data-test-id='phone'] input").setValue(user.getNumber());
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $(".calendar-input .input_invalid .input__sub").shouldHave(text("Заказ на выбранную дату невозможен"));
    }
}


