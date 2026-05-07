package top.nustar.pixel.minecraft.yaml.component.common.scan;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.function.Supplier;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Logger;

/**
 * @author NuStar
 * @since 2026/5/9 17:38
 */
public class ScanUtil {

    public static <T extends Annotation> void scanAnnotation(String path, Class<T> annotationClazz, Supplier<Logger> loggerSupplier) {
        ClassLoader classLoader = ScanUtil.class.getClassLoader();
        try {
            Enumeration<URL> resources = classLoader.getResources(path);
            List<Class<?>> classes = new ArrayList<>();
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                if (url.getProtocol().equals("jar")) {
                    classes.addAll(processJarFile(url, path));
                }
            }
            classes.stream()
                    .filter(clazz -> {
                        if (!clazz.isAnnotationPresent(annotationClazz)) {
                            loggerSupplier.get().warning(clazz.getName() + " 没有" + annotationClazz.getName() +"注解");
                            return false;
                        }
                        return true;
                    })
                    .forEach(clazz -> {
//                        try {
//                            Annotation annotation = clazz.getAnnotation(annotationClazz);
//
//                        } catch (InstantiationException | IllegalAccessException e) {
//                            throw new RuntimeException("实例化特性类失败: " + clazz.getName(), e);
//                        }
                    });
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static @NotNull List<Class<?>> processJarFile(@NotNull URL jarUrl, @NotNull String packageName) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        String jarPath = jarUrl.getPath().substring(5, jarUrl.getPath().indexOf("!"));
        JarFile jarFile = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));

        Enumeration<JarEntry> entries = jarFile.entries();
        String packagePath = packageName.replace('.', '/');

        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String entryName = entry.getName();
            if (entryName.startsWith(packagePath) && entryName.endsWith(".class")) {
                String className = entryName.replace('/', '.').substring(0, entryName.length() - 6);
                classes.add(Class.forName(className));
            }
        }
        jarFile.close();
        return classes;
    }
}
