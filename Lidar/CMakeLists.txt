# Project Definition
cmake_minimum_required(VERSION 2.6 FATAL_ERROR)
project(Lidar)

# Subdirs
subdirs(LidarDataCommunication LidarDataInterperter LidarDataParser LidarMain)

# Packages
# find_package(serial 1.2.1 REQUIRED)
# find_package(OpenCV REQUIRED)

# Include Directories
# include_directories(${serial_INCLUDE_DIRS})
# 
# Linking Directories
# link_directories(${serial_LIBRARY_DIRS})
# 
# Define Executables
# add_executable(lidar lidar.cpp)
# 
# Link
# target_link_libraries(lidar ${serial_LIBRARIES})
# target_link_libraries(lidar ${OpenCV_LIBS})
