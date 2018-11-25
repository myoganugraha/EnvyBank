package timhoreitk.envybank.Model;

public class OTPVerifModel {
    private int maxAttempt;
    private String otpstr;
    private int expireIn;
    private int digit;

    public OTPVerifModel(int maxAttempt, String otpstr, int expireIn, int digit) {
        this.maxAttempt = maxAttempt;
        this.otpstr = otpstr;
        this.expireIn = expireIn;
        this.digit = digit;
    }

    public void setMaxAttempt(int maxAttempt) {
        this.maxAttempt = maxAttempt;
    }

    public void setOtpstr(String otpstr) {
        this.otpstr = otpstr;
    }

    public void setExpireIn(int expireIn) {
        this.expireIn = expireIn;
    }

    public void setDigit(int digit) {
        this.digit = digit;
    }

    public int getMaxAttempt() {
        return maxAttempt;
    }

    public String getOtpstr() {
        return otpstr;
    }

    public int getExpireIn() {
        return expireIn;
    }

    public int getDigit() {
        return digit;
    }
}
