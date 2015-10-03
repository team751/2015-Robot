// The data structures used for representing what needs to be send to the RoboRIO
#ifndef LIDAR_LIDARDATACOMMUNICATION_TOTEPOSEPACKET_H_
#define LIDAR_LIDARDATACOMMUNICATION_TOTEPOSEPACKET_H_

#include <iostream>

// A data structure to store what the communication system should send
struct TotePosePacket {
  // A data structure to store an endpoint that defines the line of the front
  // face of a tote
  struct ToteEndpoint {
    double x;
    double y;

    ToteEndpoint(double x, double y) : x(x), y(y) {}
  };
  
  ToteEndpoint firstEndpoint;
  ToteEndpoint secondEndpoint;
  double angle; // In radians
  
  TotePosePacket(ToteEndpoint firstEndpoint, ToteEndpoint secondEndpoint, double angle) : firstEndpoint(firstEndpoint), secondEndpoint(secondEndpoint), angle(angle) {}
  
  std::string formattedMessage() {
    char message[100]; // 100 should be more than enough
    sprintf(message, "%0.3f,%0.3f,%0.3f,%0.3f,%0.3f", firstEndpoint.x, firstEndpoint.y, secondEndpoint.x, secondEndpoint.y, angle);
    
    std::string messageString(message);
    return messageString;
  }
};

#endif // LIDAR_LIDARDATACOMMUNICATION_TOTEPOSEPACKET_H_