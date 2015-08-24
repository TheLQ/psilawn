# Introduction #

This right here is the log of what happens with the programmers. Everything NEEDS to be documented. Create a new header under log for each date

# Log #

## Friday, March 26, 2010 ##

Today was when programming officially started because the programming cable came today. The first logic was written just to test the sensors. A simple infinite loops with if...else statements was used to try and make the sensors turn off and on motors.

The line tracking sensor is hard to use. The purpose of them is to detect the IR lights around the border (places where its not supposed to go) and turn around when it gets too close. The returned numbers to the controller to tell how strong the IR light is ranged from 0 - ~1200 with it normally being around 1000. However various remotes, flashlights, nor laser pointers could not affect the values. The only thing that we could do is completely block out the light by covering it with our hands, which is pretty useless to us. More investigating is needed to find out the various specs of it.

The next thing we tested was the Ultrasonic sensor. It operates by sending out sound waves, listening for the response, and timing all of it. The returned vales are between 0 - 254. At first the values where bouncing all over the place, randomly jumping from 20 - 120. After getting frustrated we controlled the conditions more by pointing it away from all of the moving people, putting a tape measure on the ground, holding up a large piece of cardbord, and trying to match inches to returned values. After testing however, it seemed that the returned values are pretty close to the distance in inches, which is pretty cool because it takes out a lot of calculations and guessing from the code. Some logic though will need to implemented that will throw out values above 250 which sprinkle the incoming data.

-Leon Blakey

## Monday, March 29, 2010 ##

Today the major ultrasonic code was written. The goal was to take 5 frames in 100 ms of the ultrasonic sensor, throw out the highest and the lowest, get the average, and recalculate if the value is still to high.

At first the code was a monstrosity. EasyC does not have support for arrays, so it had to be manually typed in (and learned, since I didn't know how to use arrays in C). The logic was to populate an array with the values, iterate over calculating the highest and lowest values, copying the values that weren't the highest or lowest into another array, and calculating the average. This all looked pretty ugly. So I then looked into condensing this into 1 for loop.

And then came the complete rewrite of almost an hour and a half of work. 3 for loops were replaced by one pretty small one. Inside the value was fetched, checked if it was between 5 and 250 (any other value was ignored), then added to a number used for that iteration, and the count was incremented. Using the number and the count, an average could be simply calculated. Next was simply a check to make sure that the -1 and 255 values (from when all the values were thrown out) weren't returned.

Took some time, but the ultrasonic sensor code is mostly written now.

-Leon Blakey

## Wednesday, March 31, 2010 ##

Today was a small programming day. The ultrasonic code was finalized: the infinite loop stripped from the actual production code as well as the entire thing copied to a test function. The main logic was worked on a little just to get the main idea down. The code that would be used while the weedeater is on still needs to be written.

The delay here though is from several design challenges: lack of self-awareness. First thing is knowledge of where the head is. Vex motor sensors cannot be adjusted by distance, its only by speed. However the robot needs to know if the arm is up or down, or if the entire arm is tilted to the left or to the right.

With the arm, a basic bumper switch at the top part of the arm should work, assuming that the limits are when the bars touch, not at some strange angles. When the arm is fully extended, the bumper switch will be hit and the robot will know (knowing that it just told it to go down) that the head is down, and also vise versa.

The transverse part is a little harder. Since the entire thing moves on a circle on the floor, its hard to tell where it is at. There are 2 idea's I have:
-Bumper sensor with grooves: As the arm transverses back and fourth, there would be 3 groves - one for the center, 2 for the sides. The bumper switch would simply drag across the ground until it falls into one of the groves. Pro's: Simple to implement, simple to program. Cons: Must always return to center, no knowledge of which groove its in, and wear and tear on the sensor.
-IR sensor: At the parts where the robot can't go, a piece of wood would be up. As the sensor gets close, more IR would get reflected, and it knows that that is the end of its transverse. Same on the other side. Pro's: Absolute boundaries. Con's: Harder to program due to random incorrect values, never really know where the center is.

-Leon Blakey

## Wednesday, April 7, 2010 ##

Today was a proof of concept day. An issue highlighted above was how to tell where the arm transverse position. A simple design that we came up with was painting an arc where it would be traversing and using the IR sensor to detect the light. Half would be black, half would be gray, and the center would be white. Knowing this, simple programming could be written to center it.

To test this concept, me and Michael put together a simple program to tell what color it was detecting. It took alot of testing to find the right intensity values, but eventually they were found. We simply said that anything above 950 is gray, anything less that 900 is white, and the in between is black. The reason that black is more intense then gray is that the gray is very shiny.

One minor programming issue that we ran into was that the original code got corrupted, a weird "linking error" was found. After lots of debugging we simply made a new project and copied the code to it.

There was some talk that the robot would be programmed a path instead of fully autonomous. The process to do it is very hard and the end result would most likely be flaky.

Since earlier testing has found that the motor output nor the sensor output cannot trip the relay we have, new relays have been ordered that are supposed to work with the vex controller. Hopefully they do.

-Leon Blakey