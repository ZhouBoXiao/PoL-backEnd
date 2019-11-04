cmake_minimum_required(VERSION 2.8)

#c++11支持
add_definitions(-std=c++11)

#设置java路径
set(java_home "/usr/java/jdk1.8.0_181-amd64")

#jni头文件
include_directories(${java_home}/include/
                    ${java_home}/include/linux/)

add_library(NativeLib SHARED
            NativeLib.cpp)

target_link_libraries(NativeLib)
