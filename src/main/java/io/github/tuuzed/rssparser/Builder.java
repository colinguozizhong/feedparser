package io.github.tuuzed.rssparser;

public interface Builder<T> {
    T build() throws IllegalAccessException;
}
