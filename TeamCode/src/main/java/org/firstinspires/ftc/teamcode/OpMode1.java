package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import java.lang.Math;

@TeleOp(name = "OpMode1")
public class OpMode1 extends OpMode {
  private final ElapsedTime runtime = new ElapsedTime();
  private DcMotorEx frontRight = null, backRight = null, backLeft = null, frontLeft = null, lift = null, lift2 = null;
  private Servo clawServo = null;
  private boolean clawClosed = false;
  private boolean lastButtonState = false;

  // config variables, adjust these to change various things about robot
  private double clawClosedPos = 1.0;
  private double clawOpenPos = 0.0;
  private double driveSpeed = 1;
  private double turnSpeed = .75;
  private double liftSpeed = 0.5; // from 0.5

  /*
    Controls:
    left stick -- moves bot in direction of stick using mechanums
    right stick -- turns bot, only x value does anything
    triggers -- moves lift up and down
    bumpers -- moves linear slide motor up and down
    x -- toggles claw open / closed
  */

  @Override
  public void init() {
    telemetry.addData("Status", "Initializing");
  
    frontRight = hardwareMap.get(DcMotorEx.class, "MotorC0");
    frontRight.setDirection(DcMotorEx.Direction.REVERSE);

    backRight = hardwareMap.get(DcMotorEx.class, "MotorC1");
    backRight.setDirection(DcMotorEx.Direction.REVERSE);

    backLeft = hardwareMap.get(DcMotorEx.class, "MotorC2");
    backLeft.setDirection(DcMotorEx.Direction.FORWARD);

    frontLeft = hardwareMap.get(DcMotorEx.class, "MotorC3");
    frontLeft.setDirection(DcMotorEx.Direction.FORWARD);

    lift = hardwareMap.get(DcMotorEx.class, "MotorE0");
    lift.setDirection(DcMotorEx.Direction.REVERSE);

    lift2 = hardwareMap.get(DcMotorEx.class, "MotorE1");
    lift2.setDirection(DcMotorEx.Direction.FORWARD);

    clawServo = hardwareMap.get(Servo.class, "ServoClaw");
    clawServo.setDirection(Servo.Direction.FORWARD);

    
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
    liftOp();
    driveOp();
    //turnOp();
    handleClaw();
    
    telemetry.addData("x:", gamepad1.x);
    telemetry.addData("clawClosed:", clawClosed);
    telemetry.addData("lastState:", lastButtonState);
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
    frontLeft.setPower(speeds[0]);
    frontRight.setPower(speeds[1]);
    backLeft.setPower(speeds[2]);
    backRight.setPower(speeds[3]);
  }
  /*
  public void turnOp() {
    if (Math.abs(gamepad1.right_stick_x) > 0.05) ;
    {
      double turn = -gamepad1.right_stick_x;
      frontRight.setPower(-turn * turnSpeed );
      backRight.setPower(-turn * turnSpeed);
      frontLeft.setPower(turn * turnSpeed);
      backLeft.setPower(turn * turnSpeed);

    }
  }
  */
  // function to toggle claw between closed and open based on when driver presses the a button
  public void handleClaw ()
  {
    //make sure that we only invert clawClosed when the button goes from upressed to pressed
    //do this by keeping track of the last button state
    if (gamepad1.x && !lastButtonState) clawClosed = !clawClosed;

    if (clawClosed) clawServo.setPosition(clawClosedPos);
    else clawServo.setPosition(clawOpenPos);
    lastButtonState = gamepad1.x;
  }
/*
  public void handleClaw ()
  {
    double leftBumper = gamepad1.left_bumper ? 1.0 : 0.0;
    double rightBumper = gamepad1.right_bumper ? 1.0 : 0.0;

    clawServo.setPower(rightBumper - leftBumper);
  }*/

  // function to handle 4 bar
  public void liftOp() {
    double deadZoneTriggers = gamepad1.right_trigger - gamepad1.left_trigger;
  
    deadZoneTriggers = Math.abs(deadZoneTriggers) < 0.05 ? 0 : deadZoneTriggers;

    lift.setPower(deadZoneTriggers * liftSpeed);
    lift2.setPower(deadZoneTriggers * liftSpeed);
  }
}
