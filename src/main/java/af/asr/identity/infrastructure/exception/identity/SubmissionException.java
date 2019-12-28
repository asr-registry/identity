package af.asr.identity.infrastructure.exception.identity;

public class SubmissionException extends Exception {

    public SubmissionException(String msg) {
        super(msg);
    }

    public SubmissionException(Throwable e) {
        super(e);
    }
}
