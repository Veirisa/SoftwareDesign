cmake_minimum_required(VERSION 3.12)
project(lru_cache)

set(CMAKE_CXX_STANDARD 17)

add_library(googleTests STATIC
        gtest/gtest-all.cc
        gtest/gtest.h
        gtest/gtest_main.cc)

enable_testing()

add_executable(lru_cache lru_cache.h tester.cpp)

target_link_libraries(lru_cache googleTests)