#include <roborio_connection.h>

#include <lidar_data_processor.h>
#include <lidar_serial_data_collector.h>

int main(int argc, char **argv) {
    RoboRIOConnection connection;
    connection.start("127.0.0.1", "9999");
    connection.send(TotePose(TotePose::ToteEndpoint(4, 3), TotePose::ToteEndpoint(10, 3), 50));
    connection.stop();

    LidarSerialDataCollector lidarSerialDataCollector("/dev/cu.usbserial-A903IUON", 115200);
    lidarSerialDataCollector.start();

    LidarDataProcessor dataProcessor;

    while (lidarSerialDataCollector.isRunning()) {
        dataProcessor.processLidarData(lidarSerialDataCollector.getOutput());
    }

    return 0;
}