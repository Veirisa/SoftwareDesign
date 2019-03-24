public class Searcher{

    enum SearcherType {
        YANDEX, BING, GOOGLE
    }

    private SearcherType type;
    private String name;

    Searcher(SearcherType t) {
        type = t;
        switch (t) {
            case YANDEX:
                name = "Yandex";
                break;
            case BING:
                name = "Bing";
                break;
            case GOOGLE:
                name = "Google";
                break;
            default:
                name = "Unknown";
        }
    }

    SearcherType getType() {
        return type;
    }

    String getName() {
        return name;
    }
}