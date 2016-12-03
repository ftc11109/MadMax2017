package org.firstinspires.ftc.tekerz;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This is NOT an opmode, just hardware and const definitions
 *
 */
public class Hardware
{
    /***********************************************8
     *     ALL HARDWARE CLASSES
     */

    public DcMotor leftDrive = null;
    public DcMotor rightDrive = null;
    public DcMotor collecter = null;
    public DcMotor shooterClipRight = null;
    public DcMotor shooterClipLeft = null;
    public DcMotor shooterRight = null;
    public DcMotor shooterLeft = null;
    public Servo    leftRoller = null;
    public Servo    rightRoller = null;
    public Servo    directParticle = null;
    public ColorSensor colorSensor0 = null;
    public ColorSensor colorSensor1 = null;


    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public Hardware(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        //NAMES OF THINGS!!
        leftDrive = hwMap.dcMotor.get("left_drive");
        rightDrive = hwMap.dcMotor.get("right_drive");
        collecter = hwMap.dcMotor.get("collecter");
        shooterClipRight = hwMap.dcMotor.get("shooter_clip_right");
        shooterRight = hwMap.dcMotor.get("shooter_fire_right");
        shooterClipLeft = hwMap.dcMotor.get("shooter_clip_left");
        shooterLeft = hwMap.dcMotor.get("shooter_fire_left");

        leftRoller = hwMap.servo.get("left_roller");
        rightRoller = hwMap.servo.get("right_roller");
        directParticle = hwMap.servo.get("direct_particle");

        colorSensor0 = hwMap.colorSensor.get("rgb0");
        colorSensor1 = hwMap.colorSensor.get("rgb1");


        // Define and Initialize Motors
        leftDrive.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        rightDrive.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors
        collecter.setDirection(DcMotor.Direction.REVERSE);
        shooterClipLeft.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        shooterClipRight.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors
        shooterLeft.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        shooterRight.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors

        // Set all motors to zero power
        leftDrive.setPower(0);
        rightDrive.setPower(0);
        collecter.setPower(0);
        shooterClipRight.setPower(0);
        shooterRight.setPower(0);
        shooterClipLeft.setPower(0);
        shooterLeft.setPower(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        collecter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        shooterRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooterClipRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooterLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooterClipLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Define and initialize ALL installed servos.
        directParticle.setDirection(Servo.Direction.REVERSE);

    }

    /***
     *
     * waitForTick implements a periodic delay. However, this acts like a metronome with a regular
     * periodic tick.  This is used to compensate for varying processing times for each cycle.
     * The function looks at the elapsed cycle time, and sleeps for the remaining time interval.
     *
     * @param periodMs  Length of wait cycle in mSec.
     */
    public void waitForTick(long periodMs) {

        long  remaining = periodMs - (long)period.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0) {
            try {
                Thread.sleep(remaining);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Reset the cycle clock for the next pass.
        period.reset();
    }
}

