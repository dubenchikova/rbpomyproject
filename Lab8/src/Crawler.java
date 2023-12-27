import java.util.LinkedList;

public class Crawler {
    // Метод для отображения результатов сканирования
    public static void showResult(LinkedList<URLDepthPair> resultLink) {
        for (URLDepthPair c : resultLink)
            System.out.println("Depth :" + c.getDepth()+"\tLink :"+c.getURL());
    }
    // Метод для проверки строки на наличие только цифр
    public static boolean checkDigit(String line) {
        boolean isDigit = true;
        for (int i = 0; i < line.length() && isDigit; i++)
            isDigit = Character.isDigit(line.charAt(i));
        return isDigit;
    }

    public static void main(String[] args) {
        // Инициализация аргументов командной строки
        args = new String[]{"http://info.cern.ch/", "10", "10"};
        // Проверка аргументов на наличие требуемых параметров
        if (args.length == 3&&checkDigit(args[1])&&checkDigit(args[2])) {
            String lineUrl = args[0];
            int numThreads = Integer.parseInt(args[2]);
            // Инициализация пула URL-адресов
            URLPool pool = new URLPool(Integer.parseInt(args[1]));
            pool.addPair(new URLDepthPair(lineUrl, 0));
            // Создание и запуск потоков
            for (int i = 0; i < numThreads; i++) {
                CrawlerTask c = new CrawlerTask(pool);
                Thread t = new Thread(c);
                t.start();
            }
            // Ожидание завершения работы всех потоков
            while (pool.getWait() != numThreads) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("Ignoring  InterruptedException");
                }
            }
            // Отображение результатов сканирования
            try {
                showResult(pool.getResult());;
            } catch (NullPointerException e) {
                System.out.println("Not Link");
            }
            // Выход из программы
            System.exit(0);
        } else {
            // Если аргументы не были введены или введены некорректно
            System.out.println("usage: java Crawler <URL> <maximum_depth> <num_threads> or second/third not digit");
        }
    }

}