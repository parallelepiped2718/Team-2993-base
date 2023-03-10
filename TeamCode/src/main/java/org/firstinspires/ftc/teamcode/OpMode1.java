package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hw;

@TeleOp(name = "MainOpMode")
public class OpMode1 extends OpMode {
  private final ElapsedTime runtime = new ElapsedTime();
  private Hw hw;

  @Override
  public void init() {
    telemetry.addData("Status", "Initializing");

    hw = new Hw(hardwareMap); //initialize hardware
    
    telemetry.addData("Status", "Initialized");
    telemetry.update();
  }

  @Override
  public void init_loop() {
  }

  @Override
  public void start() {
    runtime.reset();
  }

  @Override
  public void loop() {
    
    telemetry.update();
  }

  // function to do turning and movment, supports mekanum wheels
  public void driveOp() {
    double drive = -gamepad1.left_stick_y;
    double strafe = gamepad1.left_stick_x;
    double twist = gamepad1.right_stick_x;
    //code taken from https://github.com/brandon-gong/ftc-mecanum/blob/master/MecanumDrive.java
    double[] speeds = {
      (drive + strafe + twist),
      (drive - strafe - twist),
      (drive - strafe + twist),
      (drive + strafe - twist)
    };

    //may need to invert some of the input values or the motor directions

    // Loop through all values in the speeds[] array and find the greatest
    // *magnitude*.  Not the greatest velocity.
    double max = Math.abs(speeds[0]);
    for(int i = 0; i < speeds.length; i++) {
      if ( max < Math.abs(speeds[i]) ) max = Math.abs(speeds[i]);
    }

    // If and only if the maximum is outside of the range we want it to be,
    // normalize all the other speeds based on the given speed value.
    if (max > 1) {
      for (int i = 0; i < speeds.length; i++) speeds[i] /= max;
    }

    // apply the calculated values to the motors.
    hw.setFrontLeft(speeds[0]);
    hw.setFrontRight(speeds[1]);
    hw.setBackLeft(speeds[2]);
    hw.setBackLeft(speeds[3]);
  }
}
