package com.example.youth55test;

public class LoanData {

    /*2019.11.14
    * json array 를 받는 과정에서 자료형 타입이 잘못 명시되어있어서 계속
    * Expecting character, got :
    * 에러가 발생함. */

    private int loanId;
    private String loanSubject;
    private int loanMoney;
    private String repayTerm;
    private String repayWay;
    private String businCorpName;
    private String socialName;
    private String grade;
    private String age;
    private String isLoanExec;
    private String gender;
    private String isBookMark;
    private String investProgress;

//    public LoanData() {
//    }

    public LoanData(int loanId, String loanSubject, int loanMoney, String repayTerm, String repayWay, String businCorpName, String socialName, String grade, String age, String isLoanExec, String gender, String isBookMark, String investProgress) {
        this.loanId = loanId;
        this.loanSubject = loanSubject;
        this.loanMoney = loanMoney;
        this.repayTerm = repayTerm;
        this.repayWay = repayWay;
        this.businCorpName = businCorpName;
        this.socialName = socialName;
        this.grade = grade;
        this.age = age;
        this.isLoanExec = isLoanExec;
        this.gender = gender;
        this.isBookMark = isBookMark;
        this.investProgress = investProgress;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public String getLoanSubject() {
        return loanSubject;
    }

    public void setLoanSubject(String loanSubject) {
        this.loanSubject = loanSubject;
    }

    public int getLoanMoney() {
        return loanMoney;
    }

    public void setLoanMoney(int loanMoney) {
        this.loanMoney = loanMoney;
    }

    public String getRepayTerm() {
        return repayTerm;
    }

    public void setRepayTerm(String repayTerm) {
        this.repayTerm = repayTerm;
    }

    public String getRepayWay() {
        return repayWay;
    }

    public void setRepayWay(String repayWay) {
        this.repayWay = repayWay;
    }

    public String getBusinCorpName() {
        return businCorpName;
    }

    public void setBusinCorpName(String businCorpName) {
        this.businCorpName = businCorpName;
    }

    public String getSocialName() {
        return socialName;
    }

    public void setSocialName(String socialName) {
        this.socialName = socialName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getIsLoanExec() {
        return isLoanExec;
    }

    public void setIsLoanExec(String isLoanExec) {
        this.isLoanExec = isLoanExec;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIsBookMark() {
        return isBookMark;
    }

    public void setIsBookMark(String isBookMark) {
        this.isBookMark = isBookMark;
    }

    public String getInvestProgress() {
        return investProgress;
    }

    public void setInvestProgress(String investProgress) {
        this.investProgress = investProgress;
    }
}
