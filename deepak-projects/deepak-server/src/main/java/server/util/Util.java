package server.util;

public final class Util {
    private static Long nextNumber = 0L;

    public static synchronized Long getNextNumber(){
        synchronized (Util.class) {
            return ++nextNumber;
        }
    }
}
