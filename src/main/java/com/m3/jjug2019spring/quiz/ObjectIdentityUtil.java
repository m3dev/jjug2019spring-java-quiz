package com.m3.jjug2019spring.quiz;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ObjectIdentityUtil {
  private ObjectIdentityUtil() {}

  /**
   * Stream の中に一意なインスタンスがいくつ含まれるかを返す。
   * Object#equals 相当ではなく == 相当でインスタンス数をカウントする。
   * Stream を最後まで消費するが close はしていない。
   */
  public static <T> int countIdentity(Stream<T> stream) {
    // Stream<T> の全要素を、== の参照透過性で一意性を判定する Set にまとめる Collector
    Collector<T, ?, Set<T>> collector = Collectors.toCollection(
        () -> Collections.newSetFromMap(new IdentityHashMap<>())
    );
    return stream.collect(collector).size();
  }
}
