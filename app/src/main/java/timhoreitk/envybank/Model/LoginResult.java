package timhoreitk.envybank.Model;

public class LoginResult {

    private Integer status;
    private String message;
    private String results;

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getResults() {
        return results;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setResults(String results) {
        this.results = results;
    }
}
