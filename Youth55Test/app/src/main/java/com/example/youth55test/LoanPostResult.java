package com.example.youth55test;

public class LoanPostResult {
    private int state;
    //    private String state;
    private String message;
    private LoanDataList result;
    //private String result;

//    public LoanPostResult(int state, String message, LoanDataList result) {
//        this.state = state;
//        this.message = message;
//        this.result = result;
//    }

    public LoanPostResult(int state, String message) {
        this.state = state;
        this.message = message;
    }


//    public LoanPostResult(String state, String message, String result) {
//        this.state = state;
//        this.message = message;
//        this.result = result;
//    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LoanDataList getResult() {
        return result;
    }

    public void setResult(LoanDataList result) {
        this.result = result;
    }
}
