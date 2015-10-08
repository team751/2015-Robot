//
// Created by Sam Baumgarten on 10/7/15.
//

#ifndef LIDAR_LIDARDATAPROCESSOR_CONSTANTS_H
#define LIDAR_LIDARDATAPROCESSOR_CONSTANTS_H

#include <opencv2/core/types_c.h>

namespace Constants {
    enum FocalLengthCalculationMethod {Multiplier, Constant};

    const double kRho = 1;
    const double kTheta = CV_PI / 180;
    const int kThreshold = 30;
    const double kMinLineLength = 15;
    const double kMaxLineGap = 15;

    const FocalLengthCalculationMethod kFocalLengthCalculationMethod = Multiplier;
    const double kFocalLength = 1.08;
    const double kActualLength = 600;

    const double kLengthDeltaThreshold = 300;

    double getFocalLength(double distance) {
        switch (kFocalLengthCalculationMethod) {
            case Multiplier:
                return distance * kFocalLength;
            case Constant:
                return kFocalLength;
            default:
                return kFocalLength;
        }
    }

}

#endif //LIDAR_CONSTANTS_H
