import java.util.LinkedList;

public class URLPool {
    // Список ненайденных ссылок
    LinkedList<URLDepthPair> findLink;
    // Список найденных ссылок
    LinkedList<URLDepthPair> viewedLink;
    // Максимальная глубина поиска ссылок
    int maxDepth;
    // Количество потоков, ожидающих добавления ссылки
    int cWait;

    public URLPool(int maxDepth) {
        this.maxDepth = maxDepth;
        // Инициализация списков найденных и ненайденных ссылок
        findLink = new LinkedList<URLDepthPair>();
        viewedLink = new LinkedList<URLDepthPair>();
        cWait = 0;
    }

    // Получение следующей пары URL и глубины
    public synchronized URLDepthPair getPair() {
        // Пока список ненайденных ссылок пуст, поток ожидает
        while (findLink.size() == 0) {
            cWait++;
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Ignoring InterruptedException");
            }
            cWait--;
        }
        // Получение следующей пары URL и глубины из списка ненайденных ссылок
        URLDepthPair nextPair = findLink.removeFirst();
        // Добавление пары в список найденных ссылок
        viewedLink.add(nextPair);
        return nextPair;
    }

    // Добавление новой пары URL и глубины в список ненайденных ссылок
    public synchronized void addPair(URLDepthPair pair) {
        // Проверка, что пара ссылок еще не обрабатывалась
        if(URLDepthPair.check(viewedLink,pair)) {
            viewedLink.add(pair);
            // Проверка, что глубина меньше максимальной глубины поиска
            if (pair.getDepth() < maxDepth) {
                // Добавление пары в список ненайденных ссылок и оповещение ожидающих потоков
                findLink.add(pair);
                notify();
            }
        }
    }

    // Получение количества потоков, ожидающих добавления ссылки
    public synchronized int getWait() {
        return cWait;
    }

    // Получение списка найденных ссылок
    public LinkedList<URLDepthPair> getResult() {
        return viewedLink;
    }
}
