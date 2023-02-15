package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import java.lang.Math;


public class Hw {
  private DcMotorEx frontRight, backRight, frontLeft, backLeft;
  private DcMotorEx liftLeft, liftRight;
  private Servo clawServo;
  
  public Hw (HardwareMap hardwareMap)
  {
    frontRight = hardwareMap.get(DcMotorEx.class, "MotorC0");
    frontRight.setDirection(DcMotorEx.Direction.REVERSE);

    backRight = hardwareMap.get(DcMotorEx.class, "MotorC1");
    backRight.setDirection(DcMotorEx.Direction.REVERSE);

    backLeft = hardwareMap.get(DcMotorEx.class, "MotorC2");
    backLeft.setDirection(DcMotorEx.Direction.FORWARD);

    frontLeft = hardwareMap.get(DcMotorEx.class, "MotorC3");
    frontLeft.setDirection(DcMotorEx.Direction.FORWARD);

    liftLeft = hardwareMap.get(DcMotorEx.class, "MotorE0");
    liftLeft.setDirection(DcMotorEx.Direction.REVERSE);

    liftRight = hardwareMap.get(DcMotorEx.class, "MotorE1");
    liftRight.setDirection(DcMotorEx.Direction.FORWARD);

    clawServo = hardwareMap.get(Servo.class, "ServoClaw");
    clawServo.setDirection(Servo.Direction.FORWARD);
  }

  public void setFrontLeft (double speed)
  {
    frontLeft.setPower(speed);
  }
  public void setFrontRight (double speed)
  {
    frontRight.setPower(speed);
  }
  public void setBackLeft (double speed)
  {
    backLeft.setPower(speed);
  }
  public void setBackRight (double speed)
  {
    backRight.setPower(speed);
  }

  public void stopAndReset ()
  {
    frontRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
    backRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
    frontLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
    backLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
  }
  
  public void encoderMode()
  {
    frontRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    backRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    frontLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    backLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
  }

  public int frontRightTicks()
  {
    return frontRight.getCurrentPosition();
  }
  public int frontLeftTicks()
  {
    return frontLeft.getCurrentPosition();
  }
  public int backRightTicks()
  {
    return backRight.getCurrentPosition();
  }
  public int backLeftTicks()
  {
    return backLeft.getCurrentPosition();
  }

  public void setLift(double speed)
  {
    liftLeft.setPower(speed);
    liftRight.setPower(speed);
  }
  
  public void setClawPos (double pos)
  {
    clawServo.setPosition(pos);
  }
}
