package c04_utery_11_35.renderer;

@FunctionalInterface
public interface Shader<V, C> {
    C shade(V v);
}
