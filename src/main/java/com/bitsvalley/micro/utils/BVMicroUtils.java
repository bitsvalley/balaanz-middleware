package com.bitsvalley.micro.utils;

import com.bitsvalley.micro.domain.User;
import com.bitsvalley.micro.webdomain.RuntimeSetting;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.DecimalFormat;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BVMicroUtils {


    public static final String TRANSFER = "TRANSFER";

    public static final String CURRENT_ACCOUNT = "CURRENT ACCOUNT";
    public static final String SAVING_ACCOUNT = "SAVING ACCOUNT";
    public static final String DAILY_SAVING_ACCOUNT = "DAILY SAVING ACCOUNT";
    public static final String LOAN_ACCOUNT = "LOAN ACCOUNT";
    public static final String SHARE_ACCOUNT = "SHARE ACCOUNT";
    public static final String APARTMENT_ACCOUNT = "APARTMENT ACCOUNT";
    public static final String NOTES = "NOTES";

    public static final String CURRENT_TO_GL_TRANSFER = "CURRENT_TO_GL_TRANSFER";
    public static final String SAVING_TO_GL_TRANSFER = "SAVING_TO_GL_TRANSFER";

    public static final String CURRENT_LOAN_TRANSFER = "CURRENT_LOAN_TRANSFER";
    public static final String SAVING_SHARE_TRANSFER = "SAVING_SHARE_TRANSFER";
    public static final String DEBIT_DEBIT_TRANSFER = "DEBIT_DEBIT_TRANSFER";
    public static final String DEBIT_CURRENT_TRANSFER = "DEBIT_CURRENT_TRANSFER";

    public static final String CURRENT_DEBIT_TRANSFER = "CURRENT_DEBIT_TRANSFER";
    public static final String CURRENT_SHARE_TRANSFER = "CURRENT_SHARE_TRANSFER";
    public static final String CURRENT_CURRENT_TRANSFER = "CURRENT_CURRENT_TRANSFER";

    public static final String SAVINGS_MINIMUM_BALANCE_ADDED_BY = "Savings minimum balance added by: ";

    public static final String CURRENT = "CURRENT";

    public static final String GENERAL_SAVINGS = "GENERAL_SAVINGS";
    public static final String RETIREMENT_SAVINGS = "RETIREMENT_SAVINGS";
    public static final String DAILY_SAVINGS = "DAILY_SAVINGS";
    public static final String MEDICAL_SAVINGS = "MEDICAL_SAVINGS";
    public static final String SOCIAL_SAVINGS = "SOCIAL_SAVINGS";
    public static final String BUSINESS_SAVINGS = "BUSINESS_SAVINGS";
    public static final String CHILDREN_SAVINGS = "CHILDREN_SAVINGS";
    public static final String REAL_ESTATE_SAVINGS = "REAL_ESTATE_SAVINGS";
    public static final String EDUCATION_SAVINGS = "EDUCATION_SAVINGS";

    public static final String CONSUMPTION_LOAN = "CONSUMPTION_LOAN";
    public static final String SHORT_TERM_LOAN = "SHORT_TERM_LOAN";
    public static final String AGRICULTURE_LOAN = "AGRICULTURE_LOAN";
    public static final String BUSINESS_INVESTMENT_LOAN = "BUSINESS_INVESTMENT_LOAN";
    public static final String SCHOOL_FEES_LOAN = "SCHOOL_FEES_LOAN";
    public static final String REAL_ESTATE_LOAN = "REAL_ESTATE_LOAN";
    public static final String OVERDRAFT_LOAN = "OVERDRAFT_LOAN";
    public static final String NJANGI_FINANCING = "NJANGI_FINANCING";
    public static final String NO_NAME = "NO_NAME";

    public static final String ORDINARY_SHARE = "ORDINARY_SHARE";

    public static final String CUSTOMER_IN_USE = "customerInUse";
    public static final String DATE_FORMATTER = "dd-MM-yyyy HH:mm";
    public static final String DATE_ONLY_FORMATTER = "dd-MM-yyyy";
    public static final String DATE_US_ONLY_FORMATTER = "yyyy-MM-dd";
    //    public static final String SYSTEM = "SYSTEM";
    public static final String REGULAR_MONTHLY_PAYMENT_MISSING = "Regular Monthly payment not on schedule might be missing payment for some months. " +
            "Please check the account statement";
    public static final String MINIMUM_BALANCE_NOT_MET_FOR_THIS_ACCOUNT = "Minimum Balance not met for this account ";
    public static final String AMOUNT_ON_HOLD_BALANCE_CHANGED = "Amount On Hold balance changed";

    public static final String PENDING_APPROVAL = "PENDING_APPROVAL";
    public static final String PENDING_PAYOUT = "PENDING_PAYOUT";
    public static final String ACTIVE = "ACTIVE";
    public static final String INACTIVE = "INACTIVE";

    public static final String LOAN_MUST_BE_IN_ACTIVE_STATE = "SELECTED LOAN MUST BE IN 'ACTIVE' STATE";

    public static final String CREDIT = "CREDIT";
    public static final String DEBIT = "DEBIT";
    public static final String EXPENSES = "EXPENSES";
    public static final String LIABILITIES = "LIABILITIES";
    public static final String REVENUE = "REVENUE";
    public static final String ASSETS = "ASSETS";
    public static final String CASH = "CASH";
    public static final String EVENT = "EVENT";
    public static final String SAVINGS = "SAVINGS";
    public static final String LOAN = "LOAN";
    public static final String VAT = "VAT";
    public static final String LOAN_INTEREST = "LOAN_INTEREST";

    //    public static final String CASH_GL_1001 = "CASH GL 1001";
    public static final String CASH_GL_5001 = "CASH_GL_5001";
    public static final String SHARE_GL_XXXX = "SHARE_GL_XXXX";

    public static final String CURRENT_GL_3004 = "CURRENT_GL_3004";
    public static final String SHARE_GL_5004 = "SHARE_GL_5004";
    public static final String PREFERENCE_SHARE_GL_5005 = "PREFERENCE_SHARE_GL_5005";

    public static final String GL_4002 = "GL_4002";


    public static final String UNIT_SHARE_PRICE = "unitSharePrice";
    public static final String UNIT_SHARE_PREFERENCE_PRICE = "unitSharePreferencePrice";
    public static final String SHARE = "SHARE";
    public static final String PREFERENCE_SHARE = "PREFERENCE_SHARE";
    public static final String GL_3001 = "GL_3001";
    public static final String INIT_SYSTEM = "INIT_SYSTEM";

    public static final String GL_3004 = "GL_3004";
    public static final String GL_3005 = "GL_3005";
    public static final String GL_3006 = "GL_3006";
    public static final String GL_3007 = "GL_3007";
    public static final String GL_3008 = "GL_3008";
    public static final String GL_3009 = "GL_3009";

    public static final String GL_3010 = "GL_3010";
    public static final String GL_3011 = "GL_3011";
    public static final String GL_3012 = "GL_3012";
    public static final String GL_3013 = "GL_3013";
    public static final String GL_3014 = "GL_3014";
    public static final String GL_3015 = "GL_3015";

    public static final String GL_3016 = "GL_3016";
    public static final String GL_3017 = "GL_3017";
    public static final String GL_3018 = "GL_3018";
    public static final String GL_3019 = "GL_3019";
    public static final String GL_3020 = "GL_3020";
    public static final String GL_3021 = "GL_3021";
    public static final String GL_3022 = "GL_3022";
    public static final String GL_3023 = "GL_3023";
    public static final String GL_5004 = "GL_5004";
    public static final String GL_5005 = "GL_5005";

    //    public static final String GL_3003 = "GL_3003";
    public static final String GL_5001 = "GL_5001";
    //    public static final String GL_7001 = "GL_7001";
    public static final String GL_TRANSFER = "GL_TRANSFER";
    public static final String NJANGI_FINANCING_LOAN_INTEREST = "NJANGI_FINANCING_LOAN_INTEREST";
    public static final String OVERDRAFT_LOAN_INTEREST = "OVERDRAFT_LOAN_INTEREST";
    public static final String REAL_ESTATE_LOAN_INTEREST = "REAL_ESTATE_LOAN_INTEREST";
    public static final String SCHOOL_FEES_LOAN_INTEREST = "SCHOOL_FEES_LOAN_INTEREST";
    public static final String BUSINESS_INVESTMENT_LOAN_INTEREST = "BUSINESS_INVESTMENT_LOAN_INTEREST";
    public static final String AGRICULTURE_LOAN_INTEREST = "AGRICULTURE_LOAN_INTEREST";
    public static final String CONSUMPTION_LOAN_INTEREST = "CONSUMPTION_LOAN_INTEREST";
    public static final String SHORT_TERM_LOAN_INTEREST = "SHORT_TERM_LOAN_INTEREST";
    public static final String GL_7015 = "GL_7015";
    public static final String GL_7016 = "GL_7016";
    public static final String GL_7017 = "GL_7017";
    public static final String GL_7018 = "GL_7018";
    public static final String GL_7019 = "GL_7019";
    public static final String GL_7020 = "GL_7020";
    public static final String GL_7021 = "GL_7021";
    public static final String GL_7022 = "GL_7022";

    public static final String ROLE_CASHIER = "ROLE_CASHIER";
    public static final String ROLE_ACCOUNT_BALANCES = "ROLE_ACCOUNT_BALANCES";
    public static final String ROLE_CUSTOMER_TRANSACTIONS_PRINT = "ROLE_CUSTOMER_TRANSACTIONS_PRINT";
    public static final String ROLE_MAIN_SEARCH_USERS = "ROLE_MAIN_SEARCH_USERS";
    public static final String ROLE_GL_ACCOUNT_EXPENSE_ENTRY = "ROLE_GL_ACCOUNT_EXPENSE_ENTRY";
    public static final String ROLE_MAIN_GL_ACCOUNTS = "ROLE_MAIN_GL_ACCOUNTS";
    public static final String ROLE_CREATE_GL_ACCOUNT = "ROLE_CREATE_GL_ACCOUNT";
    public static final String ROLE_CUSTOMER = "ROLE_CUSTOMER";
    public static final String ROLE_DAILY_COLLECTION_CUSTOMER = "ROLE_DAILY_COLLECTION_CUSTOMER";
    public static final String ROLE_MANAGER = "ROLE_MANAGER";
    public static final String ROLE_GENERAL_MANAGER = "ROLE_GENERAL_MANAGER";
    public static final String ROLE_EMPLOYEE = "ROLE_EMPLOYEE";
    public static final String ROLE_ALL_BRANCH_GL = "ROLE_ALL_BRANCH_GL";
    public static final String ROLE_AGENT = "ROLE_AGENT";

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String CREDIT_CARD = "CREDIT CARD";

    public static final String THEME_COLOR_2 = "themeColor2";
    public static final String THEME_COLOR = "themeColor";
    public static final String MAKE_A_PAYMENT = "makeAPayment";
    public static final String CURRENCY = "currency";
    public static final String BID = "bid";
    public static final String INVOICE_FOOTER = "invoiceFooter";
    public static final String BILL_SELECTION_ENABLED = "billSelectionEnabled";
    public static final String COUNTRY_CODE = "countryCode";
    public static final String CURRENT_ORG = "current_org";
    public static final String SHOPPING_CART = "shoppingCart";
    public static final String SHOP_PRODUCTS = "shopProducts";
    public static final String SHOP_CATEGORIES = "shopCategories";
    public static final String POS_TO_GL_TRANSFER = "POS_TO_GL_TRANSFER";
    public static final String POS_GL_3333 = "POS_GL_3333";
    public static final String POINT_OF_SALE = "POINT_OF_SALE";
    public static final String SALES = "SALES";
    public static final String CONTEXT_NAME = "CONTEXT_NAME";
    public static final String MOMO_DISBURSEMENT = "MOMO customer disbursement Request";
    public static final String PLATFORM_FEE = "platformFee";
    public static final String WALLET_ENABLED = "walletEnabled";
    public static final String SAVING_MIN_BALANCE = "savingMinBalance";

    public static final String EMAIL_DESCRIPTION_1 = "emailDescription1";
    public static final String EMAIL_DESCRIPTION_2 = "emailDescription2";
    public static final String ORG_PROVIDED_SERVICES = "organizationProvidedServices";

    public static final String PREFERENCE_SHARE_TYPE = "Preference Share";
    public static final String ORDINARY_SHARE_TYPE = "Ordinary Share";
    public static final double DEFAULT_UNSIGNED_AMOUNT_LIMIT = 0.0;

    // SMS
    public static final String CREDIT_SMS = "Dear %s, "
        + "%s frs has been deposited to your %s daily saving account ending with xxx %s. "
        + "Available balance: %s frs. "
        + "Thank you for banking with us. "
        + "%s team";

    public static final String DEBIT_SMS = "Dear %s, "
        + "%s frs has been withdrawn from your %s daily saving account ending with %s. "
        + "Available balance: %s frs. "
        + "Thank you for banking with us. "
        + "%s team";

    public static String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"; //TODO: avoid collision
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 9) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }


    public static String formatDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        String formatDateTime = localDateTime.format(formatter);
        return formatDateTime;
    }

    public static String formatDateOnly(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_ONLY_FORMATTER);
        String formatDateTime = localDate.format(formatter);
        return formatDateTime;
    }

    public static String formatUSDateOnly(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_US_ONLY_FORMATTER);
        String formatDateTime = localDate.format(formatter);
        return formatDateTime;
    }

    public static String formatCurrency(double totalSaved) {
        return formatCurrency(totalSaved, "cm");
    }

    public static String formatCurrency(double totalSaved, String countryCode) {
        //TODO: Move this to property file on server. EUR dev env and XAF prod
        String total = "";
        if ("us".equalsIgnoreCase(countryCode)) {
            Locale locale = new Locale("en", "US");
            NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
            total = fmt.format(totalSaved);
            return total;
        } else { //("CM".equalsIgnoreCase(countryCode))

            Locale locale = new Locale("en", "CM");
            NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
            total = fmt.format(totalSaved);
            total = total.replace("XAF", "");
            total = total.replace("FCFA", "");
//        total = total.replaceFirst("F","-");
//        total = total.replaceFirst("A","");
        }
        return total;
    }

    public static String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy hh:mm");
        String format = formatter.format(date);
        return format;
    }


    public static Date formatDate(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = null;
        try {
            parse = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return parse;
    }

    public static LocalDateTime formatLocaleDate(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = null;
        try {
            parse = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        LocalDateTime localDateTime = convertToLocalDateTimeViaMilisecond(parse);
        return localDateTime;
    }


    public static String getCobacSavingsAccountNumber(String countryCode, String productCode,
                                                      int numberOfProductsInBranch, String userAccountNumber,
                                                      String branch) {

        //country code - 3 digits
        //product code - 2 digits
        //number of products for that branch - 5 digits
        //customer number starting with 101 - 11 digit
        //branch code 001 - 3 digit

//        accountNumber = accountNumber.replaceFirst("1011", "101");

        numberOfProductsInBranch = numberOfProductsInBranch + 100000;
        String noOfProductsInBranch = numberOfProductsInBranch + "";
        noOfProductsInBranch = noOfProductsInBranch.replaceFirst("1", "");

        userAccountNumber = countryCode + productCode + noOfProductsInBranch + userAccountNumber + branch;

        return userAccountNumber;
    }


    public static LocalDateTime convertToLocalDateTimeViaMilisecond(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public static LocalDate convertToLocalDate(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static Date convertToDate(LocalDateTime dateToConvert) {
        LocalDateTime localDateTime = LocalDateTime.of(
                dateToConvert.getYear(),
                dateToConvert.getMonth(),
                dateToConvert.getDayOfMonth(),
                dateToConvert.getHour(),
                dateToConvert.getMinute(),
                dateToConvert.getSecond());
        Date aDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return aDate;

    }


    public static String getFormatAccountNumber(String accountNumber) {
        return accountNumber.substring(0, 5) + " " + accountNumber.substring(5, 10) + " " + accountNumber.substring(10, 21) + " " + accountNumber.substring(21, 23);
    }


    public static String getOppositeCreditOrDebit(String creditOrDebit) {
        if (creditOrDebit.equals(BVMicroUtils.DEBIT)) {
            return BVMicroUtils.CREDIT;
        }
        return BVMicroUtils.DEBIT;
    }

    public static String getFullName(User aUser) {
        aUser.setGender(StringUtils.isEmpty(aUser.getGender()) ? "" : aUser.getGender());
        return aUser.getGender() + ". " + aUser.getFirstName() + " " + aUser.getLastName();
    }

    // Function to validate the password.
    public static boolean isValidPassword(String password) {

        //    ^ represents starting character of the string.
        //        (?=.*[0-9]) represents a digit must occur at least once.
        //        (?=.*[a-z]) represents a lower case alphabet must occur at least once.
        //        (?=.*[A-Z]) represents an upper case alphabet that must occur at least once.
        //        (?=.*[@#$%^&-+=()] represents a special character that must occur at least once.
        //        (?=\\S+$) white spaces don’t allowed in the entire string.
        //    .{8, 20} represents at least 8 characters and at most 20 characters.
        //            $ represents the end of the string.

        // Regex to check valid password.
        String regex = "^(?=.*[0-9])"
//                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#!$%^&+=])"
                + "(?=\\S+$).{8,20}$";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        // If the password is empty
        // return false
        if (password == null) {
            return false;
        }

        // Pattern class contains matcher() method
        // to find matching between given password
        // and regular expression.
        Matcher m = p.matcher(password);

        // Return if the password
        // matched the ReGex
        return m.matches();
    }

    public static String get_SHA_512_SecurePassword(String passwordToHash) {
        String generatedPassword = null;
        String salt = "12345";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public final static String maskAccountNumber(String input) {
        return "xxxxxx xxxxxx xxxxxx " + input.substring((input.length() - 5), input.length());
    }

    public final static String maskPhoneNumber(String input) {
        if (input.length() > 3)
            return "XXX " + input.substring((input.length() - 5), input.length());
        return "XXX ---";
    }

    public static String getTwoDigitInt(long orgId) { // No OrgID > 99
        StringBuilder sb = new StringBuilder();
        String twoDigit = orgId < 10 ? sb.append("0").append(orgId).toString() : sb.append(orgId).toString();
        return twoDigit;
    }

    public static String leftJustify(String pad, int length, String inputString) {
        if (inputString.length() >= length) {
            return inputString;
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length - inputString.length()) {
            sb.append(pad);
        }
        sb.append(inputString);

        return sb.toString();
    }

    public static String parseNotesCurrent(String input, String customText) {
        input = input.replaceAll("CURRENT", customText);
        return input.toLowerCase();
    }


    public static <T> List<T> getCollectionFromIterable(Iterable<T> itr) {
        List<T> cltn = new ArrayList<T>();
        for (T t : itr)
            cltn.add(t);
        return cltn;
    }


//    @ResponseBody
//    public static byte[] getLogoImage(Path path) throws IOException {
////        RuntimeSetting runtimeSetting = (RuntimeSetting)request.getSession().getAttribute("runtimeSettings");
////        Path path = Paths.get(logoPath);
//        byte[] data = Files.readAllBytes(path);
//        return data;
//    }
    public static ObjectMapper objectMapper = new ObjectMapper();

    public static String getLoggedInUserName() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return principal.toString();
    }

    public static String parseDoubleWithTwoDecimal(double value) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(value);
    }
}
