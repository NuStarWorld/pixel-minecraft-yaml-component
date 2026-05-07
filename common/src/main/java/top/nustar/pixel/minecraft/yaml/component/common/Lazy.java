package top.nustar.pixel.minecraft.yaml.component.common;


import java.util.function.Supplier;

/**
 * @author NuStar
 * @since 2025/9/3 17:07
 */
public class Lazy<T> implements Supplier<T> {

    public static <T> Lazy<T> of(Supplier<T> supplier) {
        return new Lazy<>(supplier);
    }

    public Lazy(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    private final Supplier<T> supplier;

    private volatile T value;

    @Override
    public T get() {
        if (value == null) {
            synchronized (this) {
                if (value == null) {
                    value = supplier.get();
                }
            }
        }
        return value;
    }
}
