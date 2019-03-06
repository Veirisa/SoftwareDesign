#include <iostream>
#include <map>
#include "gtest/gtest.h"
#include "lru_cache.h"


TEST(lru_cache, test_add_and_rewrite)
{
    lru_cache<string> lc(1);
    lc.set("a", "a");
    EXPECT_TRUE(lc.get("a") == optional<string>("a"));
    lc.set("a", "aa");
    EXPECT_TRUE(lc.get("a") == optional<string>("aa"));
    lc.set("b", "b");
    EXPECT_FALSE(lc.get("a").has_value());
    lc.set("a", "aaa");
    EXPECT_TRUE(lc.get("a") == optional<string>("aaa"));
}

TEST(lru_cache, test_remove)
{
    lru_cache<size_t> lc(10);
    for (size_t i = 10; i >= 1; --i) {
        lc.set(to_string(i), i);
    }
    for (size_t i = 1; i <= 10; ++i) {
        EXPECT_TRUE(lc.get(to_string(i)) == optional<size_t>(i));
    }
    for (size_t i = 11; i <= 15; ++i) {
        lc.set(to_string(i), i);
    }
    for (size_t i = 1; i <= 5; ++i) {
        EXPECT_FALSE(lc.get(to_string(i)).has_value());
    }
    for (size_t i = 6; i <= 15; ++i) {
        EXPECT_TRUE(lc.get(to_string(i)) == optional<size_t>(i));
    }
}

TEST(lru_cache, test_random)
{
    srand(time(0));
    const int AMOUNT = 20;
    const int CACHE_SIZE = 10;
    int value[AMOUNT + 1];
    vector<int> key_nums;
    lru_cache<int> lc(CACHE_SIZE);
    for (int i = 0; i < 100000; ++i) {
        int key_num = rand() % AMOUNT + 1;
        string key = to_string(key_num);
        bool is_set = rand() % 3 > 0;
        if (is_set) {
            value[key_num] = rand();
            lc.set(key, value[key_num]);
            key_nums.push_back(key_num);
        } else {
            int len = (int)key_nums.size();
            for (int j = len - 1; j >= max(0, len - CACHE_SIZE); --j) {
                if (key_nums[j] == key_num) {
                    EXPECT_TRUE(lc.get(key) == optional<int>(value[key_num]));
                    key_nums.push_back(key_num);
                    break;
                }
            }
        }
    }
}