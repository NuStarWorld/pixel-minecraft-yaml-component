package top.nustar.pixel.minecraft.yaml.component.api.component;

import top.nustar.pixel.minecraft.yaml.component.api.ComponentContext;
import top.nustar.pixel.minecraft.yaml.component.api.expression.LineExpressionCache;

import java.util.ArrayList;
import java.util.List;

/**
 * @author NuStar
 * @since 2026/5/9 22:56
 */
public interface PlayerAttributeComponent extends YamlComponent {

    /**
     * 获取属性名称
     * @return 属性名称
     */
    String getAttributeName();

    /**
     * 获得原属性列表
     * @return 原属性列表
     */
    List<String> getAttributeList(ComponentContext<?> componentContext);

    /**
     * 应用属性
     * @param componentContext 组件上下文
     */
    void applyAttribute(ComponentContext<?> componentContext);

    default <T> List<LineExpressionCache<T>> mergeAttributes(List<LineExpressionCache<T>> lineExpressionCaches1, List<LineExpressionCache<T>> lineExpressionCaches2) {
        List<LineExpressionCache<T>> lineExpressionCaches = new ArrayList<>(lineExpressionCaches1);
        lineExpressionCaches.addAll(lineExpressionCaches2);
        return lineExpressionCaches;
    }
}
