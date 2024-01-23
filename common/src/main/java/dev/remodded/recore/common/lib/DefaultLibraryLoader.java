package dev.remodded.recore.common.lib;

import dev.remodded.recore.api.lib.LibraryLoader;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 * An implementation of {@link LibraryLoader} that provides the functionality for loading libraries at runtime.
 * Libraries are added and stored in this loader, and can be loaded as needed.
 */
public class DefaultLibraryLoader implements LibraryLoader {

    // Field offset of the override field
    private static final long OVERRIDE_OFFSET = 12;
    private final Logger logger;
    private final MavenLibraryResolver resolver = new MavenLibraryResolver();
    private final ClassLoader classLoader;

    /**
     * Constructs a new {@code DefaultLibraryLoader} with the provided logger and classloader.
     *
     * @param logger       the logger to use for logging events.
     * @param classLoader  the classloader to use for loading libraries.
     */
    public DefaultLibraryLoader(@NotNull Logger logger, @NotNull ClassLoader classLoader) {
        this.logger = logger;
        this.classLoader = classLoader;
        addRepository("maven-central", "default", "https://repo1.maven.org/maven2/");
    }

    /**
     * Adds a library represented by a Maven dependency to the loader.
     *
     * @param dependency the Maven dependency representing the library.
     */
    @Override
    public void addLibrary(Dependency dependency) {
        resolver.addDependency(dependency);
    }

    /**
     * Adds a new repository from where libraries can be fetched.
     *
     * @param id The ID of the repository.
     * @param type The type of the repository.
     * @param url The URL of the repository.
     */
    @Override
    public void addRepository(String id, String type, String url) {
        resolver.addRepository(new RemoteRepository.Builder(id, type, url).build());
    }

    /**
     * Loads all libraries added to this loader.
     */
    @Override
    public void load() {
        ReCoreLibraryStore store = new ReCoreLibraryStore();
        resolver.register(store);

        List<Path> paths = store.getPaths();
        URL[] urls = new URL[paths.size()];
        for (int i = 0; i < paths.size(); i++) {
            Path path = store.getPaths().get(i);
            try {
                urls[i] = path.toUri().toURL();
            } catch (MalformedURLException e) {
                throw new AssertionError(e);
            }
        }

        try {
            URLClassLoader urlClassLoader = (URLClassLoader) this.classLoader;
            Class<?> urlClass = URLClassLoader.class;
            Method method = urlClass.getDeclaredMethod("addURL", URL.class);
            setAccessible(method);
            Arrays.stream(urls).forEach(url -> {
                try {
                    System.out.println("Loading dependency: " + url.toString());
                    logger.debug("Loading dependency: " + url.toString());
                    method.invoke(urlClassLoader, url);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e) {
            logger.error("Error while adding libraries to classpath", e);
        }

    }

    /**
     * Sets the specified method as accessible.
     * This is very hacky way around java modules system
     *
     * @param method the method to set as accessible.
     */
    private void setAccessible(Method method) {
        Unsafe unsafe = getUnsafe();
        if (unsafe != null) {
            unsafe.putBoolean(method, OVERRIDE_OFFSET, true);
        }
    }

    /**
     * Retrieves the instance of {@code Unsafe}.
     *
     * @return  the instance of {@code Unsafe}, or {@code null} if it could not be retrieved.
     */
    private Unsafe getUnsafe() {
        try {
            Field unsafe = Unsafe.class.getDeclaredField("theUnsafe");
            unsafe.setAccessible(true);
            return (Unsafe) unsafe.get(null);
        } catch (Throwable ignore) {
            return null;
        }
    }
}
