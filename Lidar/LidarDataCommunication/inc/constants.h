// Defines constants for use in the LidarDataCommuncation package
#ifndef LIDAR_LIDARDATACOMMUNICATION_CONSTANTS_H_
#define LIDAR_LIDARDATACOMMUNICATION_CONSTANTS_H_

#include <stdlib.h>

namespace Constants {
  const static struct timeval defaultTimeoutValue = {
    .tv_sec = 0,
    .tv_usec = 100 * 1000,
  };
};

#endif // LIDAR_LIDARDATACOMMUNICATION_CONSTANTS_H_