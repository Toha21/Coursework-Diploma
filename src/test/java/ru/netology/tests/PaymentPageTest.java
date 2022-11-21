package ru.netology.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.date.DataHelper;
import ru.netology.page.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.date.DataHelper.*;

public class PaymentPageTest {
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    public void setUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:8080");
    }


    @Test
    void shouldGetPaymentPage() {
        val mainPage = new MainPage();
        mainPage.payByCard();
    }

    @Test
    void shouldGetCreditPage() {
        val mainPage = new MainPage();
        mainPage.payByCredit();
    }

    @Test
    void successfulCardPayment() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillCard(cardInfo);
        paymentPage.successfullPayment();
    }

    @Test
    void shouldNotPayWithDeclinedCard() {
        val cardInfo = new DataHelper.CardInfo(getDeclinedCardNumber(), getValidMonth(), getValidYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillCard(cardInfo);
        paymentPage.declinedPayment();
    }

    @Test
    void theCardNumberIsShort() {
        val cardInfo = new DataHelper.CardInfo(getShortCardNumber(), getValidMonth(), getValidYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillCard(cardInfo);
        paymentPage.cardNumberErrorVisible();
    }

    @Test
    void successfulCardPaymentNoSpace() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumberNoSpace(), getValidMonth(), getValidYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillCard(cardInfo);
        paymentPage.successfullPayment();
    }

    @Test
    void cardNumberNotEntered() {
        val cardInfo = new DataHelper.CardInfo(null, getValidMonth(), getValidYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillCard(cardInfo);
        paymentPage.cardNumberErrorVisible();
    }

    @Test
    void theCardNumberIsEnteredWithTheLetter() {
        val cardInfo = new DataHelper.CardInfo(getCardNumberWithLetters(), getValidMonth(), getValidYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillCard(cardInfo);
        paymentPage.cardNumberErrorVisible();
    }

    @Test
    void theCardNumberIsEnteredWithSpecialCharacters() {
        val cardInfo = new DataHelper.CardInfo(getCardNumberWithSigns(), getValidMonth(), getValidYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillCard(cardInfo);
        paymentPage.cardNumberErrorVisible();
    }

    @Test
    void theCardNumberEnteredIsLong() {
        val cardInfo = new DataHelper.CardInfo(getTheCardNumberEnteredIsLong(), getValidMonth(), getValidYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillCard(cardInfo);
        paymentPage.successfullPayment();
    }

    @Test
    void monthNotEntered() {
        val cardInfo = new DataHelper.CardInfo(getTheCardNumberEnteredIsLong(), null, getValidYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillCard(cardInfo);
        paymentPage.monthErrorVisible();
    }

    @Test
    void enteringSingleDigitInTheMonthField() {
        val cardInfo = new DataHelper.CardInfo(getTheCardNumberEnteredIsLong(), getMonthWithOneDigit(), getValidYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillCard(cardInfo);
        paymentPage.successfullPayment();
    }

    @Test
    void addingLetterToTheMonthField() {
        val cardInfo = new DataHelper.CardInfo(getTheCardNumberEnteredIsLong(), getMonthWithLetters(), getValidYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillCard(cardInfo);
        paymentPage.monthErrorVisible();
    }

    @Test
    void enteringThreeDigitsInTheMonthField() {
        val cardInfo = new DataHelper.CardInfo(getTheCardNumberEnteredIsLong(), getMonthWithThreeDigits(), getValidYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillCard(cardInfo);
        paymentPage.successfullPayment();
    }

    @Test
    void enteringTwoZerosInTheMonthField() {
        val cardInfo = new DataHelper.CardInfo(getTheCardNumberEnteredIsLong(), getMonthWithNulls(), getValidNextYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillCard(cardInfo);
        paymentPage.monthErrorVisible();
    }

    @Test
    void entering01InTheMonthField() {
        val cardInfo = new DataHelper.CardInfo(getTheCardNumberEnteredIsLong(), getMonth01(), getValidNextYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillCard(cardInfo);
        paymentPage.successfullPayment();
    }

    @Test
    void entering12InTheMonthField() {
        val cardInfo = new DataHelper.CardInfo(getTheCardNumberEnteredIsLong(), getTheFirstMonth(), getValidNextYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillCard(cardInfo);
        paymentPage.successfullPayment();
    }

    @Test
    void numberOfTheMonthIsEnteredMoreThan12() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getMonthOver12(), getValidYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillCard(cardInfo);
        paymentPage.monthErrorVisible();
    }

    @Test
    void enteredInTheMonthFieldOfSpecialCharacters() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getMonthWithSigns(), getValidYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillCard(cardInfo);
        paymentPage.monthErrorVisible();
    }

    @Test
    void enteredInTheMonthFieldOfTheLetter() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getMonthWithLetters(), getValidYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillCard(cardInfo);
        paymentPage.monthErrorVisible();
    }

    @Test
    void theYearFieldIsNotFilled() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), null, getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillCard(cardInfo);
        paymentPage.yearErrorVisible();
    }

    @Test
    void thePreviousYearIsEnteredInTheYearField() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getPastYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillCard(cardInfo);
        paymentPage.yearErrorVisible();
    }

    @Test
    void theCurrentYearIsMesetsPreveduschy() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getPastMonth(), getValidYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillCard(cardInfo);
        paymentPage.monthErrorVisible();
    }

    @Test
    void currentYearAndCurrentMonth() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillCard(cardInfo);
        paymentPage.successfullPayment();
    }

    @Test
    void addingSpecialCharactersToTheYearField() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getYearWithSigns(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillCard(cardInfo);
        paymentPage.yearErrorVisible();
    }

    @Test
    void enteringЕheYearFieldInLetters() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getYearWithLetters(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillCard(cardInfo);
        paymentPage.yearErrorVisible();
    }

    @Test
    void enteringSingleDigitInTheYearField() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getYearWithOneDigit(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillCard(cardInfo);
        paymentPage.yearErrorVisible();
    }

    @Test
    void theOwnerFieldIsNotFilled() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), null, getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillCard(cardInfo);
        paymentPage.ownerErrorVisible();
    }

    @Test
    void theOwnerFieldIsFilledWithCapitalLetter() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerNameShort(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillCard(cardInfo);
        paymentPage.ownerErrorVisible();
    }

    @Test
    void theOwnerFieldIsFilledInWithHyphen() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerNameWithDoubleName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillCard(cardInfo);
        paymentPage.successfullPayment();
    }

    @Test
    void theOwnerFieldIsFilledInCyrillic() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerNameInRussia(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillCard(cardInfo);
        paymentPage.ownerErrorVisible();
    }

    @Test
    void theOwnerFieldIsFilledInWithSpecialCharacter() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerNameWithSigns(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillCard(cardInfo);
        paymentPage.ownerErrorVisible();
    }

    @Test
    void theOwnerFieldIsFilledInWithDigit() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerNameWithDigits(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillCard(cardInfo);
        paymentPage.ownerErrorVisible();
    }

    @Test
    void theCVCfieldIsNotFilledIn() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerName(), null);
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillCard(cardInfo);
        paymentPage.cvcErrorVisible();
    }

    @Test
    void theCVCfieldIsFilledWithSpecialCharacters() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerName(), getCVCwithSigns());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillCard(cardInfo);
        paymentPage.cvcErrorVisible();
    }

    @Test
    void theCVCfieldIsFilledWith2Digits() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerName(), getCVCshort());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillCard(cardInfo);
        paymentPage.cvcErrorVisible();
    }

    @Test
    void theCVCfieldIsFilledWith4Digits() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerName(), getCVClong());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillCard(cardInfo);
        paymentPage.successfullPayment();
    }

    @Test
    void theCVCfieldIsFilledWithLetters() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerName(), getCVCwithLetters());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillCard(cardInfo);
        paymentPage.cvcErrorVisible();
    }

}
