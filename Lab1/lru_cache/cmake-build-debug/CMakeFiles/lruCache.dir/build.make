# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.12

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


# Remove some rules from gmake that .SUFFIXES does not remove.
SUFFIXES =

.SUFFIXES: .hpux_make_needs_suffix_list


# Suppress display of executed commands.
$(VERBOSE).SILENT:


# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /Applications/CLion.app/Contents/bin/cmake/mac/bin/cmake

# The command to remove a file.
RM = /Applications/CLion.app/Contents/bin/cmake/mac/bin/cmake -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /Users/veirisa/CLionProject/lru_cache

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /Users/veirisa/CLionProject/lru_cache/cmake-build-debug

# Include any dependencies generated for this target.
include CMakeFiles/lruCache.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/lruCache.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/lruCache.dir/flags.make

CMakeFiles/lruCache.dir/tester.cpp.o: CMakeFiles/lruCache.dir/flags.make
CMakeFiles/lruCache.dir/tester.cpp.o: ../tester.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/Users/veirisa/CLionProject/lru_cache/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object CMakeFiles/lruCache.dir/tester.cpp.o"
	/Library/Developer/CommandLineTools/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/lruCache.dir/tester.cpp.o -c /Users/veirisa/CLionProject/lru_cache/tester.cpp

CMakeFiles/lruCache.dir/tester.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/lruCache.dir/tester.cpp.i"
	/Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /Users/veirisa/CLionProject/lru_cache/tester.cpp > CMakeFiles/lruCache.dir/tester.cpp.i

CMakeFiles/lruCache.dir/tester.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/lruCache.dir/tester.cpp.s"
	/Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /Users/veirisa/CLionProject/lru_cache/tester.cpp -o CMakeFiles/lruCache.dir/tester.cpp.s

# Object files for target lruCache
lruCache_OBJECTS = \
"CMakeFiles/lruCache.dir/tester.cpp.o"

# External object files for target lruCache
lruCache_EXTERNAL_OBJECTS =

liblruCache.a: CMakeFiles/lruCache.dir/tester.cpp.o
liblruCache.a: CMakeFiles/lruCache.dir/build.make
liblruCache.a: CMakeFiles/lruCache.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/Users/veirisa/CLionProject/lru_cache/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Linking CXX static library liblruCache.a"
	$(CMAKE_COMMAND) -P CMakeFiles/lruCache.dir/cmake_clean_target.cmake
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/lruCache.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/lruCache.dir/build: liblruCache.a

.PHONY : CMakeFiles/lruCache.dir/build

CMakeFiles/lruCache.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/lruCache.dir/cmake_clean.cmake
.PHONY : CMakeFiles/lruCache.dir/clean

CMakeFiles/lruCache.dir/depend:
	cd /Users/veirisa/CLionProject/lru_cache/cmake-build-debug && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /Users/veirisa/CLionProject/lru_cache /Users/veirisa/CLionProject/lru_cache /Users/veirisa/CLionProject/lru_cache/cmake-build-debug /Users/veirisa/CLionProject/lru_cache/cmake-build-debug /Users/veirisa/CLionProject/lru_cache/cmake-build-debug/CMakeFiles/lruCache.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/lruCache.dir/depend
