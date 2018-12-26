package uk.co.ameth.aws.example.model;

public class Response {

    public enum Status {
        OK, ERROR;
    }

    private String message;

    private Status status;

    public String getMessage() {
        return message;
    }

    public Response setMessage(String message) {
        this.message = message;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public Response setStatus(Status status) {
        this.status = status;
        return this;
    }
}
