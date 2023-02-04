package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import java.lang.Math;


public class Hw {

  public Hw (HardwareMap hwMap)
  {
    //init stuff here
  }

  public void setFrontLeft () {} //todo
  public void setFrontRight () {} //todo
  public void setBackLeft () {} //todo
  public void setBackRight () {} //todo

  public void stopResetFrontLeft () {} //todo
  public void stopResetFrontRight() {} //todo
  public void stopResetBackLeft() {} //todo
  public void stopResetBackRight() {} //todo
  
  public void encoderMode()
  {
    //set all of the motors to RUN_USING_ENCODER
  }

  public int frontRightTicks() {return 0;} //todo
  public int frontLeftTicks() {return 0;} //todo
  public int backRightTicks() {return 0;} //todo
  public int backLeftTicks() {return 0;} //todo
}
