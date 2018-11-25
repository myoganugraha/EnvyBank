package timhoreitk.envybank.Model;

public class OTPModel {
    private int maxAttempt;
    private String phoneNum;
    private int expireIn;
    private String content;
    private int digit;

    public OTPModel(int maxAttempt, String phoneNum, int expireIn, String content, int digit) {
        this.maxAttempt = maxAttempt;
        this.phoneNum = phoneNum;
        this.expireIn = expireIn;
        this.content = content;
        this.digit = digit;
    }

    public int getMaxAttempt() {
        return maxAttempt;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public int getExpireIn() {
        return expireIn;
    }

    public String getContent() {
        return content;
    }

    public int getDigit() {
        return digit;
    }

    public void setMaxAttempt(int maxAttempt) {
        this.maxAttempt = maxAttempt;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setExpireIn(int expireIn) {
        this.expireIn = expireIn;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDigit(int digit) {
        this.digit = digit;
    }
}
