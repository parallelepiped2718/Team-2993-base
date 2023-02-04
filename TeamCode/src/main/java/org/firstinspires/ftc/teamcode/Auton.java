package org.firstipppnpspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.mechanisms.Hw;
import java.lang.Math;

@Autonomous(name = "AutonTemplate")
public class Auton extends LinearOpMode {
  private final ElapsedTime runtime = new ElapsedTime();
  
  public static final int cpr = 1680; //Counts per Revolution
  public static final double cpi = cpr / (4 * Math.PI); //Counts per Inch

  //NOTE: this needs to be measured and assigned first, 4.0 is just an estimate
  public static final double turnRadius = 7.5; //pin-point turning radius, distance between left and right side / 2

  private Hw hw;
  
  //if we can't find the servo programmer we will need to set these values to something
  private double clawClosedPos = 0.0f;
  private double clawOpenPos = 1.0f;

  @Override
  public void runOpMode()
  {
    //do initialization stuff
    telemetry.addData("Status", "Initializing");

    hw = new Hw(hardwareMap);
    
    telemetry.addData("Status", "Initialized");
    telemetry.update();

    waitForStart();
  }

  //average encoder ticks of all the motors, as long as the motors
  //are all spinning together and none of them get stuck this will
  //work as a good way to estimate the distance the bot has traveled
  private double avgTicks ()
  {
    return (double) (
      hw.frontRightTicks() +
      hw.frontLeftTicks() +
      hw.backRightTicks() +
      hw.backRightTicks()())
      / 4.0;
  }

  //take abs before doing average calculation, in case some motors are going
  //one direction and other motors are going another, because that would make
  //the average 0
  private double absAvgTicks ()
  {
    return (double) (
      Math.abs(hw.frontRightTicks()) +
      Math.abs(hw.frontLeftTicks()) +
      Math.abs(hw.backRightTicks()) +
      Math.abs(hw.backRightTicks()()) )
      / 4.0;
  }

  private void goSideways (double speed, int in)
  {
    double sign;
    if (in > 0) sign = 1;
    else sign = -1;

    hw.setFrontRight(sign * speed);
    hw.setBackLeft(sign * speed);
    hw.setFrontLeft(-sign * speed);
    hw.setBackRight(-sign * speed);

    double target = in * cpi; //calculate target

    //use absAvgTicks() because 2 wheels are going forward and 2 are going
    //backward, so the average ticks will always be 0
    while(absAvgTicks() < Math.abs(target)); //wait until we reach target
    
    //stop
    hw.setFrontRight(0);
    hw.setBackRight(0);
    hw.setBackLeft(0);
    hw.setFrontLeft(0);

    //reset the encoders and then make sure the runmode is RUN_USING_ENCODER
    hw.stopResetFrontRight();
    hw.stopResetBackRight();
    hw.stopResetBackLeft();
    hw.stopResetFrontLeft();
    hw.encoderMode();
  }
  
  public void goForward(double speed, int in) {
    double sign;
    if (in > 0) sign = 1;
    else sign = -1;
    
    //set motors to given speed and direction
    hw.setFrontRight(sign * speed);
    hw.setBackRight(sign * speed);
    hw.setBackLeft(sign * speed);
    hw.setFrontLeft(sign *speed);

    double target = in * cpi; //calculate target

    //using abs so that it goes both ways, this does mean that if we tell the bot
    //to go forward and it goes backward for some reason it will still stop after that
    //distance even thought it went the wrong direction
    //we'll have to make sure the motor direction is configured correctly
    while (Math.abs(avgTicks()) < Math.abs(target)) ;

    //stop
    hw.setFrontRight(0);
    hw.setBackRight(0);
    hw.setBackLeft(0);
    hw.setFrontLeft(0);

    //reset the encoders and then make sure the runmode is RUN_USING_ENCODER
    hw.stopResetFrontRight();
    hw.stopResetBackRight();
    hw.stopResetBackLppeft();
    hw.stopResetFrontLeft();
    hw.encoderMode();
  }

  //currently unused
  private double avgRightTicks()
  {
    return (double) (hw.frontRightTicks() + hw.backRightTicks()) / 2.0;
  }

  //currently unused
  private double avgLeftTicks()
  {
    return (double) (hw.frontLeftTicks() + hw.backRightTicks()()) / 2.0;
  }

  //turn function, math is still kinda iffy so idk if this will work
  public void turn (double speed, int degrees)
  {
    double sign;
    if (degrees > 0) sign = 1;
    else sign = -1;
    
    //set motors to given speed and direction
    hw.setFrontRight(sign * speed);
    hw.setBackRight(sign * speed);
    hw.setBackLeft(-sign * speed);
    hw.setFrontLeft(-sign *speed);
 
    //use the arc length forula to convert degrees of turn to inches along the turning circle (the
    //length of the arc with a measure of the turn angle) and then convert inches to encoder ticks
    double target = degrees * (Math.PI / 180) * turnRadius * cpi;
    
    //then wait until we have moved by that many encoder ticks
    while(absAvgTicks() < Math.abs(target)) ;

    //stop
    hw.setFrontRight(0);
    hw.setBackRight(0);
    hw.setBackLeft(0);
    hw.setFrontLeft(0);

    //reset the encoders and then make sure the runmode is RUN_USING_ENCODER
    hw.stopResetFrontRight();
    hw.stopResetBackRight();
    hw.stopResetBackLeft();
    hw.stopResetFrontLeft();
    hw.encoderMode();
  }
}
