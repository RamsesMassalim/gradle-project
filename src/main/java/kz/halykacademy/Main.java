package kz.halykacademy;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    public static void main(String[] args) {
        List<String> strings = new CopyOnWriteArrayList<>();
        AtomicBoolean start = new AtomicBoolean(false);
        AtomicBoolean end = new AtomicBoolean(false);

        Runnable streamLoader = () -> {
            int size = 10;

            while (size > 0) {
                size--;

                strings.add("String " + size);

                if (strings.size() > 3) {
                    start.set(true);
                }
            }
        };

        Runnable streamReader = () -> {
            while (!end.get()) {
                if (start.get()) {
                    if (strings.size() <= 0) {
                        end.set(true);
                        break;
                    } else {
                        String removedString = strings.remove(0);
                        System.out.printf("%s removed %s\n",
                                Thread.currentThread().getName(),
                                removedString);
                    }
                }
            }
        };

        for (int i = 0; i < 3; i++) {
            new Thread(streamReader).start();
        }

        new Thread(streamLoader).start();
    }
}
