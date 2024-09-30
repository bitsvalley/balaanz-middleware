package com.bitsvalley.micro.services;


import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class InterestService {


    public double calculateInterestAccruedMonthCompounded(double interestRate,
                                                          LocalDateTime createdDate,
                                                          double principalAmount) {
        double interestPlusOne = (
                interestRate * .01 * .0833333) + 1;
        double temp = Math.pow(interestPlusOne, getNumberOfMonths(createdDate));
        temp = temp - 1;
        return principalAmount * temp;
    }

    /*  Amortised Loan repayment
        Here’s a formula to calculate your monthly payments manually: M= P[r(1+r)^n/((1+r)^n)-1)]
        M = the total monthly mortgage payment.
        P = the principal loan amount.
        r = your monthly interest rate. Lenders provide you an annual rate so you’ll need to divide that figure by 12 (the number of months in a year) to get the monthly rate. If your interest rate is 5 percent, your monthly rate would be 0.004167 (0.05/12=0.004167).
        n = number of payments over the loan’s lifetime. Multiply the number of years in your loan term by 12 (the number of months in a year) to get the number of payments for your loan. For example, a 30-year fixed mortgage would have 360 payments (30x12=360).
     */
    public double monthlyPaymentAmortisedPrincipal(double interestRate,
                                                   int noOfMonths,
                                                   double principalAmount) {

        if(interestRate == 0){
           return principalAmount/noOfMonths;
        }

        double rate = (interestRate * .01) / 12;
        double ratePlusOne = rate + 1;
        double temp = Math.pow(ratePlusOne, noOfMonths);
        double nominator = rate * temp;

        double denominator = temp - 1;
        return principalAmount * (nominator / denominator);
    }

    /*
    a*(r/n)
    a: 100,000, the amount of the loan
    r: 0.06 (6% expressed as 0.06)
    n: 12 (based on monthly payments)
    Calculation 1: 100,000*(0.06/12)=500, or 100,000*0.005=500
    Calculation 2: (100,000*0.06)/12=500, or 6,000/12=500
     */
    public double monthlyPaymentInterestOnly(int interestRate,
                                                   int noOfMonths,
                                                   int principalAmount) {
        double rate = (interestRate * .01);
        double denominator = rate / noOfMonths;
        return principalAmount / denominator;
    }


    private double getNumberOfMonths(LocalDateTime createdDateInput) {
        double noOfMonths = 0.0;
        Duration diff = Duration.between(createdDateInput, LocalDateTime.now());
        noOfMonths = diff.toDays() / 30;
        return Math.floor(noOfMonths);
    }

}
