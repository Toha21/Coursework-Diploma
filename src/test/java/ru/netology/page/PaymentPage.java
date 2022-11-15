package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.date.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byCssSelector;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class PaymentPage {
    private SelenideElement cardNumber = $("[placeholder=\"0000 0000 0000 0000\"]");
    private SelenideElement month = $( "[placeholder=\"08\"]");
    private SelenideElement year = $("[placeholder=\"22\"]");
    private SelenideElement owner = $(byText("Владелец")).parent().$(".input__control");
    private SelenideElement cvc = $(" [placeholder=\"999\"]");
    private SelenideElement continueButton =$x(" //*[text()='Продолжить']");
    private SelenideElement cardNumberError = $x("//*[@id=\"root\"]/div/form/fieldset/div[1]/span/span/span[3]");
    private SelenideElement monthError = $(byText("Месяц")).parent().$(".input__sub");
    private SelenideElement yearError = $(byText("Год")).parent().$(".input__sub");
    private SelenideElement ownerError = $(byText("Владелец")).parent().$(".input__sub");
    private SelenideElement cvcError = $(byText("CVC/CVV")).parent().$(".input__sub");


    public void fillCard(DataHelper.CardInfo cardInfo){
        cardNumber.setValue(cardInfo.getCardNumber());
        month.setValue(cardInfo.getMonth());
        year.setValue(cardInfo.getYear());
        owner.setValue(cardInfo.getOwner());
        cvc.setValue(cardInfo.getCvcCVV());
        continueButton.click();
    }

    public void cardNumberErrorVisible(){
        cardNumberError.shouldBe(visible);
    }

    public void monthErrorVisible(){
        monthError.shouldBe(visible);
    }

    public void yearErrorVisible(){
        yearError.shouldBe(visible);
    }

    public void ownerErrorVisible(){
        ownerError.shouldBe(visible);
    }

    public void cvcErrorVisible(){
        cvcError.shouldBe(visible);
    }
    public void successfullPayment() {
        $(".notification_status_ok").shouldBe(Condition.visible, Duration.ofSeconds(30));
    }

    public void declinedPayment() {
        $(byCssSelector("div.notification.notification_status_error.notification_has-closer.notification_stick-to_right.notification_theme_alfa-on-white")).shouldBe(Condition.visible, Duration.ofSeconds(20));
    }



}
