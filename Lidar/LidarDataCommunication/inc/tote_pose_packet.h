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

    // Constructor
    ToteEndpoint(double x, double y) : x(x), y(y) {}
  };
  
  ToteEndpoint firstEndpoint; // One of the line endpoints (order doesn't matter)
  ToteEndpoint secondEndpoint; // The other line endpoint (order doesn't matter)
  double angle; // In radians
  
  // Constructor
  TotePosePacket(ToteEndpoint firstEndpoint, ToteEndpoint secondEndpoint, double angle) : firstEndpoint(firstEndpoint), secondEndpoint(secondEndpoint), angle(angle) {}
  
  // Returns a string in the tote format protocol form
  std::string formattedMessage() {
    // Create a buffer to hold the message
    char message[100]; // 100 should be more than enough
    
    // Format the message
    sprintf(message, "%0.3f,%0.3f,%0.3f,%0.3f,%0.3f", firstEndpoint.x, firstEndpoint.y, secondEndpoint.x, secondEndpoint.y, angle);
    
    // Convert the message to a std::string
    std::string messageString(message);
    
    return messageString;
  }
};

#endif // LIDAR_LIDARDATACOMMUNICATION_TOTEPOSEPACKET_H_