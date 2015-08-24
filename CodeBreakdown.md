# Introduction #

This is a complete breakdown of the code used in the project, useful for understanding what's happening


# The Framework #

The Psilawn code is built around a framework of utility methods, meant to limit the amount of spaghetti code needed to do simple things.

## moveRelay.c ##

This is the core of all functionality with the relays. The Vex Spike Relays work by taking 2 binary values over 2 separate wires. 1 0 means turn right, 0 1 turn left, and 1 1 or 0 0 mean stop. As you can see, interfacing with the relays directly would be very ugly. So this was created.

Definition:
void moveRelay ( int motor1, int motor2, int direction )

_Param: motor1_ - The position of the first control line on the sensor board. This should be a constant.
_Param: motor2_ - The position of the second control line on the sensor board. This should be a constant
_Param: Direction_ -
_Returns_ - Immediately. No value is returned, this is simply a utility

## wheelController.c ##

This is a utility function for controlling the wheels. It uses moveRelay to do the dirty work, but simplifies calling by just passing a direction to the function.

Using this, the code automatically makes each motor go forwards or backwards, depending on the specified direction.

**WARNING:** It is up to the calling function to judge distance and sensor checking. This simply turns on or off the motors

Definition:
void wheelController ( int direction )

_Param: direction_ - The direction the robot should go. Legal values are
  * -2 - Go backwards
  * -1 - Turn left
  * 0 - Stop
  * 1 - Turn Right
  * 2 - Go Forwards
_Returns_ - Immediately. No value is returned, this is simply a utility

## transController.c ##

This is a utility function for making the arm traverse into the correct position. Given a position (based on the numbering guide), no matter where it is it will go to that position. Note: There are only 3 positions - far left, far right, and center. The gray and black are only helpers to tell where the arm is so it doesn't have to slam into the sides in order to move.

Definition:
void transController ( int position )

_Param: position_ - The position the arm should go. Legal values are:
  * -1 - Far left
  * 0 - Center
  * 1 - Far right
_Returns_ - Only when arm is in correct position. No value is returned, this is simply a utility

## transController\_check.c ##

This is a utility function for transController. It returns the current color that the arm is at via the light sensor, nothing else. The reason this is a separate function is to clean up the code needed to calculate this inside of while and if statements.

Definition:
int transController\_check ( void )

_Returns_ - Immediately. The returned value is the numerical representation of the color

## SonicSensorCheck.c ##

This is a utility function for getting real good values from the sonic sensor. The reason is that the sonic sensor tends to throw out random values as it hits a blade of grass. This simply gets 5 values over 100 ms and averages them together. If the value is below 5 or above 256, the value is thrown out. If the average is below 5 or above 256, then the entire average is recalculated. This allows for retrieval of good, clean sonic sensor values

Definition:
int SonicSensorCheck ( int sensorNum, int sensorOut, int sensorInt )

_Param: sensorNum_ - The sensor number that it is checking. This is solely used for debug statements to separate the values that they are returning
_Param: sensorOut_ - The position of the sonic sensor in the sensor board. This should be passed as a constant.
_Param: sensorInt_ - The position of the sonic sensor in the _interrupt_ board. This should be passed as a constant.
_Returns_ - When the correct values have been obtained. The returned value is a correct sonic sensor value.