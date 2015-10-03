# Packages
find_package(OpenCV REQUIRED)

# Include Directories
include_directories(${OpenCV_INCLUDE_DIRS})

# Linking Directories
link_directories(${OpenCV_LIBRARY_DIRS})

# Library
add_library(LidarDataInterperter interperter.cpp)