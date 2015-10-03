# Include Directories
include_directories(${Lidar_SOURCE_DIR}/LidarMain/inc)
include_directories(${Lidar_SOURCE_DIR}/LidarDataCommunication/inc)
include_directories(${Lidar_SOURCE_DIR}/LidarDataInterperter/inc)
include_directories(${Lidar_SOURCE_DIR}/LidarDataParser/inc)

# Link Directories
link_directories(${Lidar_SOURCE_DIR}/LidarDataCommunication/src)
link_directories(${Lidar_SOURCE_DIR}/LidarDataInterperter/src)
link_directories(${Lidar_SOURCE_DIR}/LidarDataParser/src)

# Set Output Directories
set(EXECUTABLE_OUTPUT_PATH ../../)

# Define Executables
add_executable(lidar lidar.cpp)

# Link Libraries
target_link_libraries(lidar LidarDataCommunication LidarDataInterperter LidarDataParser)