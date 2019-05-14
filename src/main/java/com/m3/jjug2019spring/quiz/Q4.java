package com.m3.jjug2019spring.quiz;

import java.util.stream.IntStream;

public class Q4 {
  private static class UnauthorizedException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public UnauthorizedException(String message) { super(message); }
  }

  public static void main(String[] args) {
    for (int countToThrow : new int[]{ 1, 100, 1000, 100000, 1000000 }) {
      final long startAt = System.currentTimeMillis();
      int instances = countUnauthorizedExceptionsForHotCode(countToThrow);
      final long elapsedMs = System.currentTimeMillis() - startAt;
      System.out.println(String.format("%,9d 回で得られた例外インスタンス数:\t%,9d (%d [ms])", countToThrow, instances, elapsedMs));
    }
    /* 実行結果 (JDK 11.0.3):
        1 回で得られた例外インスタンス数:	        1 (22 [ms])
      100 回で得られた例外インスタンス数:	      100 (2 [ms])
    1,000 回で得られた例外インスタンス数:	    1,000 (3 [ms])
  100,000 回で得られた例外インスタンス数:	  100,000 (273 [ms])
1,000,000 回で得られた例外インスタンス数:	1,000,000 (2810 [ms])
     */
  }

  /**
   * UnauthorizedException を投げる同じコードを何回も実行した場合に、
   * UnauthorizedException のインスタンスがいくつ得られるかを返す。
   *
   * @param countToThrow 例外を発生させる回数
   * @return 一意な例外インスタンス数
   */
  public static int countUnauthorizedExceptionsForHotCode(int countToThrow) {
    return ObjectIdentityUtil.countIdentity(IntStream.range(0, countToThrow).mapToObj((i) -> {
      try {
        throwsUnauthorizedException();
      } catch (UnauthorizedException e) {
        return e;
      }
      throw new AssertionError("Unreachable code");
    }));
  }

  /**
   * @throws UnauthorizedException このメソッドは必ず UnauthorizedException を投げる。
   */
  public static void throwsUnauthorizedException() {
    throw new UnauthorizedException("ログインが必要です");
  }
}
