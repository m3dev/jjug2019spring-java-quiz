package com.m3.jjug2019spring.quiz;

import java.util.List;
import java.util.stream.IntStream;

public class Q3 {

  public static void main(String[] args) {
    for (int countToThrow : new int[]{ 1, 100, 1000, 100000 }) {
      final long startAt = System.currentTimeMillis();
      int instances = countRuntimeNullPointerExceptionsForHotCode(countToThrow);
      final long elapsedMs = System.currentTimeMillis() - startAt;
      System.out.println(String.format("%,9d 回で得られた例外インスタンス数:\t%,9d (%d [ms])", countToThrow, instances, elapsedMs));
    }
    /* 実行結果 (JDK 11.0.3):
        1 回で得られた例外インスタンス数:	        1 (26 [ms])
      100 回で得られた例外インスタンス数:	      100 (1 [ms])
    1,000 回で得られた例外インスタンス数:	    1,000 (23 [ms])
  100,000 回で得られた例外インスタンス数:	   10,754 (275 [ms])
     */
  }

  /**
   * NullPointerException が JVM によって投げられる同じコードを何回も実行した場合に、
   * NullPointerException のインスタンスがいくつ得られるかを返す。
   *
   * @param countToThrow 例外を発生させる回数
   * @return 一意な例外インスタンス数
   */
  public static int countRuntimeNullPointerExceptionsForHotCode(int countToThrow) {
    return ObjectIdentityUtil.countIdentity(IntStream.range(0, countToThrow).mapToObj((i) -> {
      try {
        throwsRuntimeNullPointerException();
      } catch (NullPointerException e) {
        return e;
      }
      throw new AssertionError("Unreachable code");
    }));
  }

  /**
   * ランタイム(JVM)によって NullPointerException を投げるメソッド。
   *
   * <code>throw new NullPointerException();</code> するのではなく、
   * null を dereference することで JVM 処理系自体に NullPointerException を throw させる。
   *
   * @throws NullPointerException このメソッドは必ず NullPointerException を投げる。
   */
  public static void throwsRuntimeNullPointerException() {
    String str = null;
    str.length(); // NullPointerException
    throw new AssertionError("Unreachable code");
  }

  /**
   * {@see throwsRuntimeNullPointerException} と同じだが別メソッド・別ロジック。
   * JVM がメソッドをまたいで NullPointerException のインスタンスを流用していることを調べるために用意した。
   */
  public static void throwsRuntimeNullPointerException2() {
    List<Integer> str = null;
    str.size(); // NullPointerException
    throw new AssertionError("Unreachable code");
  }
}
