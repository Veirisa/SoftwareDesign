#ifndef LRU_CACHE_H
#define LRU_CACHE_H


#include <string>
#include <cassert>
#include <optional>
#include <unordered_map>
using namespace std;

template<typename T>
struct lru_cache {

    explicit lru_cache(size_t new_size) : size(new_size), root(), map() {
        assert(new_size > 0);
        root.l_ptr = &root;
        root.r_ptr = &root;
    };

    void set(const string &key, const T &obj) {
        assert(map.size() <= size);
        bool exist = map.find(key) != map.end();
        if (!exist) {
            if (map.size() == size) {
                element &last = *(root.l_ptr);
                remove(last);
                map.erase(last.key);
            }
            map[key] = element(key, obj);
        }
        element &el = map[key];
        if (!exist) {
            add(el);
        } else {
            el.set(obj);
            shift(el);
        }
        assert(map.size() <= size);
        assert(map.find(key) != map.end());
        assert(map[key].obj == obj);
        assert((root.r_ptr)->obj == obj);
    }

    optional<T> get(const string &key) {
        bool exist = map.find(key) != map.end();
        if (!exist) {
            return optional<T>();
        }
        element &el = map[key];
        shift(el);
        assert((root.r_ptr)->obj == el.obj);
        return optional<T>(el.obj);
    }

private:

    struct element {
        T obj;
        string key;
        element *l_ptr;
        element *r_ptr;

        element() = default;

        explicit element(const string &new_key, const T &new_obj) {
            key = new_key;
            obj = new_obj;
        }

        void set(const T &new_obj) {
            obj = new_obj;
        }
    };

    size_t size;
    element root;
    unordered_map<string, element> map;

    void add(element &el) {
        element &after_root = *(root.r_ptr);
        el.l_ptr = &root;
        el.r_ptr = &after_root;
        root.r_ptr = &el;
        after_root.l_ptr = &el;
    }

    void remove(element &el) {
        (el.l_ptr)->r_ptr = el.r_ptr;
        (el.r_ptr)->l_ptr = el.l_ptr;
    }

    void shift(element &el) {
        remove(el);
        add(el);
    }
};


#endif //LRU_CACHE_H


