# Argument Setup
set(serial_DIR /usr/local/share/serial/cmake/)

# Packages
find_package(serial 1.2.1 REQUIRED)

# Include Directories
include_directories(${serial_INCLUDE_DIRS})

# Linking Directories
link_directories(${serial_LIBRARY_DIRS})

# Library
add_library(LidarDataParser parser.cpp)