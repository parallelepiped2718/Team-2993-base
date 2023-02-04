public class Pid {
  private double kP;
  private double kI;
  private double kD;

  private double target;

  private double previousError = 0;
  private double integral = 0;

  public Pid (double kP, double kI, double kD)
  {
    this.kP = kP;
    this.kI = kI;
    this.kD = kD;
    target = 0;
  }

  public Pid (double kP, double kI, double kD, double target)
  {
    this.kP = kP;
    this.kI = kI;
    this.kD = kD;
    this.target = target;
  }
  
  public void setTarget(double target)
  {
    this.target = target;
  }

  //this will need to be called every frame, even when the robot is doing something else
  //therefore this needs to somehow run asynchronusly in the background
  //also we need to actually set the output of this to the motor power
  public double updatePid (double currentPos, double deltaTime)
  {
    double error = target - currentPos;
    integral += error * deltaTime;
    double deriv = (error - previousError) / deltaTime;
    double output = kP * error + kI * integral + kD * deriv;
    previousError = error;
    return output;
  }

  public void updateTuningValues(double kP, double kI, double kD)
  {
    this.kP = kP;
    this.kI = kI;
    this.kD = kD;
  }
}
