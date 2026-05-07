package top.nustar.pixel.minecraft.yaml.component.common.exception;

/**
 * @author NuStar
 * @since 2026/5/8 00:19
 */
public class ValidationException extends RuntimeException {
    private static final long serialVersionUID = -8312236949680773523L;

    public ValidationException() {
        super();
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }

    protected ValidationException(
            String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
