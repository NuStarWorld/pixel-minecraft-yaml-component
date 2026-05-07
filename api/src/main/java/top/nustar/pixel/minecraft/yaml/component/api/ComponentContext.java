package top.nustar.pixel.minecraft.yaml.component.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

/**
 * @author NuStar
 * @since 2026/5/7 23:52
 */
@Data
@RequiredArgsConstructor
public class ComponentContext<T> {
    private final T holder;

    private final UUID holderUid;

    private final Map<String, String> placeholderMap;

    public static <T> ComponentContext<T> of(T holder, UUID holderUid) {
        return of(holder, holderUid, Collections.emptyMap());
    }

    public static <T> ComponentContext<T> of(T holder, UUID holderUid, Map<String, String> placeholderMap) {
        return new ComponentContext<>(holder, holderUid, placeholderMap);
    }
}
